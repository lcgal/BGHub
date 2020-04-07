package com.example.bghub.Views.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bghub.R;
import com.example.bghub.Views.Login.LoginActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    Button mLogoutButton;

    @Inject
    MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpButtons();

    }

    private void setUpButtons(){
        mLogoutButton = findViewById(R.id.logout_button);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){

        LoginManager.getInstance().logOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
