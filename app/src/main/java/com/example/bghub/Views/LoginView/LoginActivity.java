package com.example.bghub.Views.LoginView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bghub.R;
import com.example.bghub.Views.Main.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import android.content.Intent;
import android.os.Bundle;


import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (Profile.getCurrentProfile() != null){

            goToMainActivity();

        }

        //Facebook Login
        List< String > facebookPermissions = Arrays.asList("user_photos", "email","public_profile", "AccessToken");
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions(facebookPermissions);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        mPresenter.processUserLogin(Profile.getCurrentProfile(),loginResult.getAccessToken());

                        goToMainActivity();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {

                        goToMainActivity();
                        // App code
                    }
                });
    }

    private void goToMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //finish();
    }



}
