package com.example.bghub.Repositories.Data;

import com.example.bghub.Models.Session;
import com.facebook.AccessToken;
import com.facebook.Profile;

import javax.inject.Inject;

public class DataRepository implements DataContract.Repository {

    public AccessToken loginToken;

    public static Session session;

    @Inject
    public DataRepository() {
    }
    
    @Override
    public AccessToken getLoginToken() {
        return loginToken;
    }

    @Override
    public void saveLoginToken(AccessToken loginToken) {
        this.loginToken = loginToken;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void saveSession(Session session) {
        this.session = session;
    }

    @Override
    public void changeSessionStatus (int status){
        session.setStatus(status);
    }

}
