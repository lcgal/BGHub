package com.example.bghub.data.services.http

import javax.inject.Inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.example.bghub.data.models.apiResponse.GameListResponse
import com.example.bghub.data.models.apiResponse.ApiResponse
import com.example.bghub.data.models.GameRooms.GameOffer
import com.example.bghub.data.models.users.UserLocation
import com.example.bghub.data.models.apiResponse.RoomListResponse
import com.example.bghub.data.models.GameRooms.JoinGameRoomPayload
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HttpService : HttpContract {
    private val mRetrofit: HttpContract?

    @Inject
    constructor(interceptor : Interceptor) {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://bghub-api.azurewebsites.net/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HttpContract::class.java)
    }

    override fun getGamesList(version: String): Observable<GameListResponse> {
        return mRetrofit!!.getGamesList(version)
    }

    override fun getGameDescription(gameId: String): Observable<ApiResponse<String>> {
        return mRetrofit!!.getGameDescription(gameId)
    }

    override fun postGameOffer(gameOffer: GameOffer): Observable<ApiResponse<String>> {
        return mRetrofit!!.postGameOffer(gameOffer)
    }

    override fun getGameRooms(userLocation: UserLocation): Observable<RoomListResponse> {
        return mRetrofit!!.getGameRooms(userLocation)
    }

    override fun postJoinGameRoom(joinroompayload: JoinGameRoomPayload): Observable<ApiResponse<String>> {
        return mRetrofit!!.postJoinGameRoom(joinroompayload)
    }
}