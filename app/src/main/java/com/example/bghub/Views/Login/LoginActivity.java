package com.example.bghub.Views.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.bghub.BGHubApplication;
import com.example.bghub.Background.DownloadGameListWorker;
import com.example.bghub.R;
import com.example.bghub.Repositories.Data.DataContract;
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

import dagger.android.AndroidInjection;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog = null;

    private static final int RC_SIGN_IN = 123;

    @Inject DataContract.Repository mDataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        WorkRequest myWorkRequest = OneTimeWorkRequest.from(DownloadGameListWorker.class);
        WorkManager.getInstance(BGHubApplication.getAppContext()).enqueue(myWorkRequest);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            mDataRepository.processLogin();
            goToMainActivity();
            return;
        }

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

// Custom layout for login method picker
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_login)
                .setEmailButtonId(R.id.login_email)
                .setFacebookButtonId(R.id.login_fb)
                .setGoogleButtonId(R.id.login_ggl)
                .setPhoneButtonId(R.id.login_phone)
                .build();

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setAuthMethodPickerLayout(customLayout)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onDestroy() {
        mProgressDialog = null;
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // TODO user settings etc
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {

            }
        }
    }


    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
