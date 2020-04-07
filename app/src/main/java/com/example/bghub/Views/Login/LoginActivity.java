package com.example.bghub.Views.Login;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bghub.R;
import com.example.bghub.Views.Main.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import android.content.Intent;
import android.os.Bundle;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    CallbackManager mCallBackManager;
    LoginButton mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (Profile.getCurrentProfile() != null){

            goToMainActivity();

        }

        //Facebook Login
        List< String > facebookPermissions = Arrays.asList("user_photos", "email","public_profile");
        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setPermissions(facebookPermissions);
        mLoginButton.setLoginBehavior( LoginBehavior.WEB_ONLY );

        //FacebookSdk.sdkInitialize(getApplicationContext());
        //LoginManager.getInstance().logOut();
        mCallBackManager = CallbackManager.Factory.create();
        mLoginButton.registerCallback(mCallBackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                    }
                    @Override
                    public void onCancel() {
                    }
                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void goToMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken == null){

            } else{
                loadUserProfile(currentAccessToken);

                //mPresenter.processUserLogin(Profile.getCurrentProfile(),loginResult.getAccessToken());
            }

        }
    };

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                }catch (JSONException e){
                    e.printStackTrace();
                }
                goToMainActivity();
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","email,first_name,last_name,id");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
