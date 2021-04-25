package com.example.bghub.Views.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bghub.R;
import com.example.bghub.Views.Main.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    CallbackManager mCallBackManager;
    LoginButton mLoginButton;
    ProgressDialog mProgressDialog = null;

    private static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

// Custom layout for login method picker
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_login)
                .setFacebookButtonId(R.id.login_fb)
                .setGoogleButtonId(R.id.login_ggl)
                .build();

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
//                        .setLogo(R.drawable.logo_transparent)
                        .setAvailableProviders(providers)
                        .setAuthMethodPickerLayout(customLayout)
                        .build(),
                RC_SIGN_IN);


//        setContentView(R.layout.activity_login);
//
//        AndroidInjection.inject(this);
//
//        mPresenter.start();
//
//        //Facebook Login
//        setupFacebookLoginCallBack();
//
//        AndroidInjection.inject(this);
//
//        //TODO move this somewhere appropiate
//        //Check Location Permission and Phone State
//        ActivityCompat.requestPermissions(this,
//                new String[]{
//                        android.Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dispose();
        mProgressDialog = null;
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mCallBackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    public void goToMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Triggers when there's a change to the facebook token.
     */
//    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            if(currentAccessToken == null){
//
//            } else{
//                mPresenter.loadUserProfile(currentAccessToken);
//            }
//
//        }
//    };


//    private void setupFacebookLoginCallBack()
//    {
//        List< String > facebookPermissions = Arrays.asList("user_photos", "email","public_profile");
//        mLoginButton = findViewById(R.id.login_button);
//        mLoginButton.setPermissions(facebookPermissions);
//        mLoginButton.setLoginBehavior( LoginBehavior.WEB_ONLY );
//
//
//        mCallBackManager = CallbackManager.Factory.create();
//        mLoginButton.registerCallback(mCallBackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                    }
//                    @Override
//                    public void onCancel() {
//                    }
//                    @Override
//                    public void onError(FacebookException exception) {
//                    }
//                });
//    }

}
