package com.example.bghub.Views.Login;

import android.os.Bundle;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.bghub.BGHubApplication;
import com.example.bghub.Background.DownloadGameListWorker;
import com.example.bghub.Models.ApiResponse.GameListResponse;
import com.example.bghub.Models.ApiResponse.ProfileResponse;
import com.example.bghub.Models.Session.Credentials;
import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Session.Profile;
import com.example.bghub.Models.Session.Session;
import com.example.bghub.Models.Session.User;
import com.example.bghub.Repositories.Data.DataContract;
import com.example.bghub.Repositories.Http.HttpContract;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.bghub.Commons.AppConstants.Logged_in;
import static com.example.bghub.Commons.AppConstants.Logged_out;
import static com.example.bghub.Commons.AppConstants.Processing_Login;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private DataContract.Repository mDataRepository;
    private HttpContract mHttpRepository;

    private Profile mProfile;

    @Inject
    public LoginPresenter (
            LoginContract.View view,
            DataContract.Repository dataRepository,
            HttpContract httpRepository){
        mView = view;
        mDataRepository = dataRepository;
        mHttpRepository = httpRepository;
    };

    @Override
    public void start(){
//        if (mDataRepository.getSession() != null) {
//            mView.goToMainActivity();
//        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mView.goToMainActivity();
        }

//        if (com.facebook.Profile.getCurrentProfile() != null) {
//            mDataRepository.changeSessionStatus(Logged_in);
//        }
    }

    @Override
    public void dispose() {

    }



    @Override
    public void loadUserProfile(AccessToken newAccessToken, FirebaseUser firebaseUser){
        if (mDataRepository.getSession() == null) {
            mDataRepository.startSession();

            GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    User user = new User();
                    Credentials credentials = new Credentials();
                    mProfile = new Profile();
                    try {
                        user.setFirstName(object.getString("first_name"));
                        user.setLastName(object.getString("last_name"));
                        user.setEmail(object.getString("email"));

                        String id = object.getString("id");
                        credentials.setFbId(id);
                        mProfile.setUserId(id);
                        String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                        user.setPhotoUrl(image_url);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mProfile.setUser(user);
                    mProfile.setCredentials(credentials);

                    WorkRequest myWorkRequest = OneTimeWorkRequest.from(DownloadGameListWorker.class);
                    WorkManager.getInstance(BGHubApplication.getAppContext()).enqueue(myWorkRequest);

                    processLogin(mProfile);
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "email,first_name,last_name,id");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private void processLogin(Profile profile){
        //TODO send this to workmanager
        mHttpRepository.FbLogin(profile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(new DisposableObserver<ProfileResponse>() {
                    @Override
                    public void onNext(ProfileResponse result) {
                        mDataRepository.saveProfile(result.getReturnData());
                        mDataRepository.getSession().setProfile(result.getReturnData());

                        mView.goToMainActivity();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
