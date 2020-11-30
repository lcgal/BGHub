package com.example.bghub.Views.Login;

        import com.facebook.AccessToken;
        import com.facebook.Profile;
        import com.google.firebase.auth.FirebaseUser;

public interface LoginContract {
    interface View<Presenter> {

        void goToMainActivity();


    }

    interface Presenter<View>  {

        //request facebook informations about the token
        void loadUserProfile(AccessToken newAccessToken, FirebaseUser firebaseUser);

        void start();

        void dispose();


    }
}
