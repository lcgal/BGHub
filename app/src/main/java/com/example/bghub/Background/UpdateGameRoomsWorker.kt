package com.example.bghub.Background

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.bghub.Models.ApiResponse.GameListResponse
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.Repositories.Http.HttpRepository
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class UpdateGameRoomsWorker (
        appContext: Context,
        workerParams: WorkerParameters,
        private val httpRepository: HttpRepository,
        private val dataRepository : DataContract.Repository
) : RxWorker(appContext, workerParams) {

    override fun createWork(): Single<Result> {
        TODO("Not yet implemented")
    }

    fun fetchGamesRooms ()
    {

//        httpRepository.getGameRooms(dataRepository.location)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribeWith(object : DisposableObserver<GameListResponse?>() {
//                    override fun onNext(result: GameListResponse) {
//                        if (result.isUpdate) {
//
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        val test = e.message
//                        //return Single.just(Result.success())
//                    }
//
//                    override fun onComplete() {
//                        //return Single.just(Result.success())
//                    }
//
//                })

    }

}