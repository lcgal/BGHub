package com.example.bghub.Repositories.Http;

import android.net.wifi.hotspot2.pps.Credential;

import com.example.bghub.Models.Credentials;
import com.example.bghub.Models.Profile;
import com.example.bghub.Models.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HttpContract {

    @GET("Credentials/test")
    Observable<Credentials> test();

    @POST("Credentials/FbLogin")
    Observable<User> FbLogin(@Body Profile profile);
}
