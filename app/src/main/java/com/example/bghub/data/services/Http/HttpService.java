package com.example.bghub.data.services.Http;

import androidx.annotation.Nullable;

import com.example.bghub.data.models.apiResponse.ApiResponse;
import com.example.bghub.data.models.apiResponse.GameListResponse;
import com.example.bghub.data.models.apiResponse.RoomListResponse;
import com.example.bghub.data.models.GameRooms.GameOffer;
import com.example.bghub.data.models.GameRooms.JoinGameRoomPayload;
import com.example.bghub.data.models.users.UserLocation;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService implements HttpContract {
    final private HttpContract mRetrofit;

    public HttpService(@Nullable HttpContract retrofit){
        mRetrofit = retrofit;
    }

//    @Inject
    HttpService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://bghub-api.azurewebsites.net/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HttpContract.class);
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


