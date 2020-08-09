package com.example.bghub.Background

import android.content.Context
import android.location.Location
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.bghub.Models.ApiResponse.RoomListResponse
import com.example.bghub.Models.GameRooms.GameRoom
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
    var userLocation = dataRepository.getUserLocation()

    override fun createWork(): Single<Result> {
        fetchGamesRooms ()
        return Single.just(Result.success())
    }

    fun fetchGamesRooms ()
    {
        httpRepository.getGameRooms(userLocation)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<RoomListResponse?>() {
                    override fun onNext(result: RoomListResponse) {
                        var response = result
                        saveGameRooms(response.gameRooms)

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

    fun saveGameRooms (rooms : List<GameRoom>) {
        //TODO this should be an user preference
        var radius = 50000.00;
        
        val userLocationObj = Location("locationA")
        userLocationObj.latitude = userLocation.latitude
        userLocationObj.longitude = userLocation.longitude

        val gamesInRange = mutableListOf<GameRoom>()
        val gameRooms = dataRepository.gameRooms

        if (rooms != null || !rooms.isEmpty()) {

            rooms.forEach ForEach@{ n ->
                val roomLocation = Location("locationA")
                roomLocation.latitude = n.latitude
                roomLocation.longitude = n.longitude

                var roomDistance = userLocationObj.distanceTo(roomLocation)
                if (roomDistance < radius) {

                    if (gameRooms != null && !gameRooms.isEmpty()) {
                        var dbGameRoom = gameRooms.filter { o -> o.id == n.id }.first()
                        if (dbGameRoom != null) {

                            gamesInRange.add(dbGameRoom)
                            return@ForEach
                        }
                    }
                    n.distance = roomDistance / 1000
                    var game = dataRepository.getGameById(n.gameId)
                    n.game = game
                    gamesInRange.add(n)

                }
            }
        }
        dataRepository.saveGameRooms(gamesInRange)
    }

}