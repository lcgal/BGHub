package com.example.bghub.background


import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.bghub.data.models.apiResponse.GameListResponse
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.http.HttpService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

@HiltWorker
class DownloadGameListWorker
@AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val httpService: HttpService,
    private val dataService : DbContract
) : RxWorker(appContext, workerParams) {

    override fun createWork(): Single<Result> {

        downloadGameList()

        //TODO still haven't figured out how to properly use RxJava2 with WorkerManager, the return should depend on what happens with the observer...
        return Single.just(Result.success())
    }


    fun downloadGameList() : DisposableObserver<GameListResponse?> {
        val version: String = dataService.getGamesListVersion()
        return httpService.getGamesList(version)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<GameListResponse?>() {
                    override fun onNext(result: GameListResponse) {
                        if (result.isUpdate) {
                            val games = result.data
                            dataService.saveGamesList(games, result.version)
                            //return Single.just(Result.success())
                        }
                    }

                    override fun onError(e: Throwable) {
                        val test = e.message
                        //return Single.just(Result.success())
                    }

                    override fun onComplete() {
                        //return Single.just(Result.success())
                    }
                })
    }
    

}


