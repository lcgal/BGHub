package com.example.bghub.Repositories.Http;

import android.net.wifi.hotspot2.pps.Credential;

import com.example.bghub.Models.ApiResponse.BooleanResponse;
import com.example.bghub.Models.ApiResponse.GameListResponse;
import com.example.bghub.Models.ApiResponse.ProfileResponse;
import com.example.bghub.Models.Credentials;
import com.example.bghub.Models.GameRooms.GameOffer;
import com.example.bghub.Models.Profile;
import com.example.bghub.Models.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HttpContract {

    @GET("Credentials/test")
    Observable<Credentials> test();

    @POST("Credentials/FbLogin")
    Observable<ProfileResponse> FbLogin(@Body Profile profile);

    @GET("download/gamelist/{version}")
    Observable<GameListResponse> getGamesList(@Path("version") String version);

    @POST("gamerooms/offergame")
    Observable<BooleanResponse> postGameOffer(@Body GameOffer gameOffer);
}
