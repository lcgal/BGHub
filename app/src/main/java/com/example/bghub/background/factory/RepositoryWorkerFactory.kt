package com.example.bghub.background.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.bghub.background.*
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.Http.HttpRepository


class RepositoryWorkerFactory(
        private val httpRepository: HttpRepository,
        private val dataRepository: DbContract.Repository
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