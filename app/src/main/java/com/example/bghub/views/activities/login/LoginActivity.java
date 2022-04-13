package com.example.bghub.views.activities.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.bghub.BGHubApplication;
import com.example.bghub.R;
import com.example.bghub.background.DownloadGameListWorker;
import com.example.bghub.data.services.data.DbContract;
import com.example.bghub.views.activities.main.MainActivity;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

//@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog = null;

    //TODO
//    @Inject
//    DbContract mDataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityResultLauncher<Intent> facebookLoginResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        onFacebookLoginResult(data);
                    }
                });

        WorkRequest myWorkRequest = OneTimeWorkRequest.from(DownloadGameListWorker.class);
        WorkManager.getInstance(BGHubApplication.getAppContext()).enqueue(myWorkRequest);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
//            mDataRepository.processLogin();
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
        Intent facebookLoginIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setAuthMethodPickerLayout(customLayout)
                        .setIsSmartLockEnabled(false)
                        .build();
        facebookLoginResultLauncher.launch(facebookLoginIntent);
    }

    @Override
    protected void onDestroy() {
        mProgressDialog = null;
        super.onDestroy();
    }

    protected void onFacebookLoginResult(Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        // TODO user settings etc
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }


    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
