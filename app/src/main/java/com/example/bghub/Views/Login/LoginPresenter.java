package com.example.bghub.Views.Login;

import android.os.Bundle;

import com.example.bghub.Models.Credentials;
import com.example.bghub.Models.Profile;
import com.example.bghub.Models.User;
import com.example.bghub.Repositories.Data.DataContract;
import com.example.bghub.Repositories.Http.HttpContract;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private DataContract.Repository mDataRepository;
    private HttpContract mHttpRepository;

    private CompositeDisposable mSubscriptions;
    private User loginUser;

    @Inject
    public LoginPresenter (
            LoginContract.View view,
            DataContract.Repository dataRepository,
            HttpContract httpRepository){

        mView = view;
        mDataRepository = dataRepository;
        mHttpRepository = httpRepository;

    }



    @Override
    public void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                User user = new User();
                Credentials credentials = new Credentials();
                Profile profile = new Profile();
                try {
                    user.setFirstName(object.getString("first_name"));
                    user.setLastName(object.getString("last_name"));
                    user.setEmail(object.getString("email"));

                    String id = object.getString("id");
                    credentials.setFbId(id);
                    profile.setUserId(id);
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    user.setImage_Url(image_url);

                }catch (JSONException e){
                    e.printStackTrace();
                }

                profile.setUser(user);
                profile.setCredentials(credentials);

                processLogin(profile);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","email,first_name,last_name,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void innitializeSubscription(){

        mSubscriptions = new CompositeDisposable();

    }

    private void processLogin(Profile profile){
        mHttpRepository.FbLogin(profile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User result) {
                        loginUser = result;

                        mView.goToMainActivity();

                    }

                    @Override
                    public void onError(Throwable e) {
                        String test = e.getMessage();

                    }

                    @Override
                    public void onComplete() {
                        String test = "";

                    }
                });
    }

}
