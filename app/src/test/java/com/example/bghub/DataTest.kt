package com.example.bghub

import com.example.bghub.data.models.Games.Game
import com.example.bghub.data.models.apiResponse.GameListResponse
import com.example.bghub.data.services.http.HttpService
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import org.junit.Assert
import org.junit.Test

class DataTest {
    lateinit var httpService : HttpService

    @Test
    fun getServerGamesList() {

        val syncObject = Any()

        val mockInterceptor : Interceptor = MockInterceptor()
        httpService = HttpService(mockInterceptor)

        lateinit var games : List<Game>

        httpService.getGamesList("0")
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeWith(object : DisposableObserver<GameListResponse?>() {
                override fun onNext(result: GameListResponse) {
                    if (result.isUpdate) {
                        games = result.data
                    }
                }
                override fun onError(e: Throwable) {
                    synchronized(syncObject) {
                        syncObject.notifyAll();
                    }
                }
                override fun onComplete() {
                    synchronized(syncObject) {
                        syncObject.notifyAll();
                    }
                }
            })
        synchronized (syncObject){
            syncObject.wait();
        }
        Assert.assertNotNull(games)
//        Assert.assertTrue(checkGamesList(games))
//        Assert.assertTrue(checkGameMapping(games))
    }

    fun checkGamesList(networkGameList: List<Game>) : Boolean {
        networkGameList.forEach {

            if(it.id == null) {
                return false
            }
            if(it.name == null) {
                return false
            }
        }

        return true
    }
}