package com.example.bghub.background

import android.content.Context
import android.location.Location
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.bghub.BGHubApplication
import com.example.bghub.data.models.apiResponse.RoomListResponse
import com.example.bghub.data.models.GameRooms.GameRoom
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.http.HttpService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

@HiltWorker
class UpdateGameRoomsWorker
@AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val httpService: HttpService,
    private val dataRepository : DbContract
) : RxWorker(appContext, workerParams) {
    var userLocation = dataRepository.getUserLocation()

    override fun createWork(): Single<Result> {
        fetchGamesRooms ()
        return Single.just(Result.success())
    }

    fun fetchGamesRooms ()
    {
        httpService.getGameRooms(userLocation)
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
                            if (dbGameRoom.game.getDescription() == null) {
                                val input = Data.Builder()
                                input.putString("GAME_ID",dbGameRoom.game.getId().toString())
                                val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(GetDescriptionWorker::class.java).setInputData(input.build()).build()
                                WorkManager.getInstance(BGHubApplication.getAppContext()).enqueue(myWorkRequest)
                            }

                            gamesInRange.add(dbGameRoom)
                            return@ForEach
                        }
                    }
                    n.distance = roomDistance / 1000
                    var game = dataRepository.getGameById(n.gameId)
                    n.game = game
                    if (game.getDescription() == null) {
                        val input = Data.Builder()
                        input.putString("GAME_ID",game.getId().toString())
                        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(GetDescriptionWorker::class.java).setInputData(input.build()).build()
                        WorkManager.getInstance(BGHubApplication.getAppContext()).enqueue(myWorkRequest)
                    }

                    gamesInRange.add(n)

                }
            }
        }
        dataRepository.saveGameRooms(gamesInRange)
    }

}