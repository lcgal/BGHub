package com.example.bghub.Views.Login;

        import com.facebook.AccessToken;
        import com.facebook.Profile;

public interface LoginContract {
    interface View<Presenter> {

        public void goToMainActivity();


    }

    interface Presenter<View>  {

        //request facebook informations about the token
        public void loadUserProfile(AccessToken newAccessToken);


    }
}
