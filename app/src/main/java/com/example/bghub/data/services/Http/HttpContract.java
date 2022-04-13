package com.example.bghub.data.services.Http;

import com.example.bghub.data.models.apiResponse.ApiResponse;
import com.example.bghub.data.models.apiResponse.GameListResponse;
import com.example.bghub.data.models.apiResponse.RoomListResponse;
import com.example.bghub.data.models.GameRooms.GameOffer;
import com.example.bghub.data.models.GameRooms.JoinGameRoomPayload;
import com.example.bghub.data.models.users.UserLocation;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HttpContract {

    @GET("download/gamelist/{version}")
    Observable<GameListResponse> getGamesList(@Path("version") String version);

    @GET("download/gamedescription/{gameid}")
    Observable<ApiResponse<String>> getGameDescription(@Path("gameid") String gameId);

    @POST("gamerooms/offergame")
    Observable<ApiResponse<String>> postGameOffer(@Body GameOffer gameOffer);

    @POST("gamerooms/gamerooms")
    Observable<RoomListResponse> getGameRooms(@Body UserLocation userLocation);

    @POST("gamerooms/joinroom")
    Observable<ApiResponse<String>> postJoinGameRoom(@Body JoinGameRoomPayload joinroompayload);
}
