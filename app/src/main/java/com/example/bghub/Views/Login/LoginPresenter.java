package com.example.bghub.Views.Login;

import android.os.Bundle;

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

        if (mDataRepository.getSession() == null){
            mDataRepository.saveSession(new Session());
            mDataRepository.changeSessionStatus(Logged_out);
        }

        if (com.facebook.Profile.getCurrentProfile() != null){
            mDataRepository.changeSessionStatus(Logged_in);
            mView.goToMainActivity();
        }

    }

    @Override
    public void dispose() {

    }



    @Override
    public void loadUserProfile(AccessToken newAccessToken){
        if (1 == 1) {
        //if (mDataRepository.getSession().getStatus() != Processing_Login) {
            mDataRepository.changeSessionStatus(Processing_Login);

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

                    downloadGameList();
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "email,first_name,last_name,id");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private void downloadGameList(){
        String version = mDataRepository.getGamesListVersion();
        mHttpRepository.getGamesList(version)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(new DisposableObserver<GameListResponse>() {
                    @Override
                    public void onNext(GameListResponse result) {
                        if (result.isUpdate()) {
                            //TODO send this to the background and go on with the login.
                            List<Game> games = result.getData();
                            mDataRepository.saveGamesList(games, result.getVersion());
                        }
                        //TOOD process login first, then have the gamelist be downloaded and processed on the background.
                        processLogin(mProfile);
                    }


                    @Override
                    public void onError(Throwable e) {
                        String test = e.getMessage();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void processLogin(Profile profile){
        mHttpRepository.FbLogin(profile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(new DisposableObserver<ProfileResponse>() {
                    @Override
                    public void onNext(ProfileResponse result) {
                        mDataRepository.saveProfile(result.getReturnData());
                        mDataRepository.changeSessionStatus(Logged_in);
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
