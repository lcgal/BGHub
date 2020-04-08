package com.example.bghub.Repositories.Http;

import android.net.wifi.hotspot2.pps.Credential;

import com.example.bghub.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpContract {

    @POST("FbLogin")
    Call<User> FbLogin(@Body Credential credential);
}
