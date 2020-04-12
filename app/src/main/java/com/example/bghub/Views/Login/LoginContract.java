package com.example.bghub.Views.Login;

        import com.facebook.AccessToken;
        import com.facebook.Profile;

public interface LoginContract {
    interface View {

        public void goToMainActivity();


    }

    interface Presenter {

        //request facebook informations about the token
        public void loadUserProfile(AccessToken newAccessToken);


    }
}
