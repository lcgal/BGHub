package com.example.bghub.Repositories.Http;

import androidx.annotation.Nullable;

import com.example.bghub.Models.ApiResponse.BooleanResponse;
import com.example.bghub.Models.ApiResponse.GameListResponse;
import com.example.bghub.Models.ApiResponse.ProfileResponse;
import com.example.bghub.Models.Credentials;
import com.example.bghub.Models.GameRooms.GameOffer;
import com.example.bghub.Models.Profile;
import com.example.bghub.Models.User;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

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
                .baseUrl("http://192.168.0.187/PhoneApi/")
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
    public Observable<Credentials> test(){
        return mRetrofit.test();
    }

    @Override
    public Observable<GameListResponse>  getGamesList(String version){
        return mRetrofit.getGamesList(version);
    }

    @Override
    public Observable<BooleanResponse> postGameOffer(GameOffer gameOffer) {
        return mRetrofit.postGameOffer(gameOffer);
    }
}


