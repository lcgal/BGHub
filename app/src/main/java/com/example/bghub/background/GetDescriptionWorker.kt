package com.example.bghub.background

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.bghub.data.models.apiResponse.ApiResponse
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.Http.HttpService
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class GetDescriptionWorker (
    appContext: Context,
    workerParams: WorkerParameters,
    private val httpService: HttpService,
    private val dataRepository : DbContract
    ) : RxWorker(appContext, workerParams) {

    val KEY_GAME_ID = "GAME_ID"

    override fun createWork(): Single<Result> {
        val gameId = inputData.getString(KEY_GAME_ID)

        if (gameId != null && gameId?.toLong() != null) {
            getDescription(gameId)
        }
        //TODO find out how to work with workmanager and rxjava
        return  Single.just(Result.success())
    }

    fun getDescription(gameId : String?) : DisposableObserver<ApiResponse<String>> {
        return httpService.getGameDescription(gameId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<ApiResponse<String>>() {
                    override fun onNext(result: ApiResponse<String>) {
                        if (result.result) {
                            var description = result.returnData
                            dataRepository.updateGameDescription(gameId!!.toLong(),description)
                            //TODO event to signal that the game description has been updated
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