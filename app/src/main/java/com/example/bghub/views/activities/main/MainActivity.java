package com.example.bghub.views.activities.main;


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

import com.example.bghub.R;
import com.example.bghub.databinding.ActivityMainBinding;
import com.example.bghub.views.fragments.MainMenuFragment;
import com.example.bghub.views.fragments.OfferGameFragment;
import com.example.bghub.views.fragments.ProfileFragment;
import com.example.bghub.views.fragments.SearchGameFragment;
import com.example.bghub.views.activities.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    Button mLogoutButton;

    @Inject
    MainContract.Presenter mPresenter;

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        AndroidInjection.inject(this);

        mPresenter.start();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView bottomMenu = mainBinding.bottomNavigationView;
        bottomMenu.setOnNavigationItemSelectedListener(navListener);
        bottomMenu.setItemIconTintList(null);

        openMainMenu();
    }

    //Using ifs instead of switch because of idle warning about Resource IDs no longer being final
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        } else {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);
        }
    }

    //Using ifs instead of switch because of idle warning about Resource IDs no longer being final
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        if (item.getItemId() == R.id.home) {
            openMainMenu();
        }
        if (item.getItemId() == R.id.profile) {
            openProfile();
        }
        return true;
    };

    private void logout(){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    public void openOfferGameFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OfferGameFragment fragment = mPresenter.provideOfferGameFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openSearchGameFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SearchGameFragment fragment = mPresenter.provideSearchGameFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openMainMenu(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MainMenuFragment fragment = new MainMenuFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openProfile(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ProfileFragment fragment = new ProfileFragment();
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
