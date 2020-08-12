package com.example.bghub.Views.Main;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.bghub.BGHubApplication;
import com.example.bghub.Background.DownloadGameListWorker;
import com.example.bghub.Background.UpdateGameRoomsWorker;
import com.example.bghub.R;
import com.example.bghub.Utils.OnSingleClickListener;
import com.example.bghub.Views.Fragments.OfferGameFragment;
import com.example.bghub.Views.Fragments.SearchGameFragment;
import com.example.bghub.Views.Login.LoginActivity;
import com.facebook.login.LoginManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    Button mLogoutButton;

    Button mOfferGameButton;

    Button mTriggerUpdateButton;

    Button mSearchGameButton;

    @Inject
    MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        mPresenter.start();

        setUpButtons();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpButtons(){

        mOfferGameButton = findViewById(R.id.offer_game_button);
        mOfferGameButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openOfferGameFragment();
            }
        });

        mTriggerUpdateButton = findViewById(R.id.search_game_button);
        mTriggerUpdateButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                WorkRequest myWorkRequest = OneTimeWorkRequest.from(UpdateGameRoomsWorker.class);
                WorkManager.getInstance(BGHubApplication.getAppContext()).enqueue(myWorkRequest);
                //openSearchGameFragment();
            }
        });

        mSearchGameButton = findViewById(R.id.search_game_button2);
        mSearchGameButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openSearchGameFragment();
            }
        });

    }

    private void logout(){

        mPresenter.logout();

        LoginManager.getInstance().logOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void openOfferGameFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OfferGameFragment fragment = mPresenter.provideOfferGameFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openSearchGameFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SearchGameFragment fragment = mPresenter.provideSearchGameFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
