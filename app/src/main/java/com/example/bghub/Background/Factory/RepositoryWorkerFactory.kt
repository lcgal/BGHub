package com.example.bghub.Background.Factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.bghub.Background.DownloadGameListWorker
import com.example.bghub.Background.GetDescriptionWorker
import com.example.bghub.Background.UpdateGameRoomsWorker
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.Repositories.Http.HttpRepository
import javax.inject.Singleton


class RepositoryWorkerFactory(
        private val httpRepository: HttpRepository,
        private val dataRepository: DataContract.Repository
) : WorkerFactory() {

    override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when(workerClassName) {
            DownloadGameListWorker::class.java.name ->
                DownloadGameListWorker(appContext, workerParameters,httpRepository,dataRepository)
            UpdateGameRoomsWorker::class.java.name ->
                UpdateGameRoomsWorker(appContext, workerParameters,httpRepository,dataRepository)
            GetDescriptionWorker::class.java.name ->
                GetDescriptionWorker(appContext, workerParameters,httpRepository,dataRepository)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }

}