package com.example.bghub.Repositories.Http;

import androidx.annotation.Nullable;

import com.example.bghub.Models.ApiResponse.ApiResponse;
import com.example.bghub.Models.ApiResponse.GameListResponse;
import com.example.bghub.Models.ApiResponse.ProfileResponse;
import com.example.bghub.Models.ApiResponse.RoomListResponse;
import com.example.bghub.Models.GameRooms.GameOffer;
import com.example.bghub.Models.GameRooms.JoinGameRoomPayload;
import com.example.bghub.Models.Session.Credentials;
import com.example.bghub.Models.Session.Profile;
import com.example.bghub.Models.UserLocation;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRepository implements HttpContract {
    private HttpContract mRetrofit;

    public HttpRepository(@Nullable HttpContract retrofit){
        mRetrofit = retrofit;
    }

    @Inject
    HttpRepository(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.5/phoneapi/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HttpContract.class);
    }

    @Override
    public Observable<ProfileResponse> FbLogin(Profile profile){
        return mRetrofit.FbLogin(profile);
    }

    @Override
    public Observable<GameListResponse>  getGamesList(String version){
        return mRetrofit.getGamesList(version);
    }

    @Override
    public Observable<ApiResponse<String>> getGameDescription(String gameId) {
        return mRetrofit.getGameDescription(gameId);
    }

    @Override
    public Observable<ApiResponse<String>> postGameOffer(GameOffer gameOffer) {
        return mRetrofit.postGameOffer(gameOffer);
    }

    @Override
    public Observable<RoomListResponse> getGameRooms(UserLocation userLocation) {
        return mRetrofit.getGameRooms(userLocation);
    }

    @Override
    public Observable<ApiResponse<String>> postJoinGameRoom(JoinGameRoomPayload joinroompayload) {
        return mRetrofit.postJoinGameRoom(joinroompayload);
    }
}


