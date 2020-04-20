package com.example.bghub.Repositories.Data;

import com.example.bghub.Models.Session;
import com.facebook.AccessToken;
import com.facebook.Profile;

public interface DataContract {
    interface Repository {
        public AccessToken getLoginToken();

        void saveLoginToken(AccessToken loginToken);

        Session getSession();

        void saveSession(Session session);

        void changeSessionStatus (int status);

    }

}
