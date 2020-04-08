package com.example.bghub.Repositories.Http;

import android.net.wifi.hotspot2.pps.Credential;

import androidx.annotation.Nullable;

import com.example.bghub.Models.User;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
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
                .baseUrl("API")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HttpContract.class);

    }

    @Override
    public Call<User> FbLogin(Credential credential){
        return mRetrofit.FbLogin(credential);
    }
}


