package com.example.bghub.background.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.bghub.background.*
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.Http.HttpService


class RepositoryWorkerFactory(
    private val httpService: HttpService,
    private val dataRepository: DbContract
) : WorkerFactory() {

    override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when(workerClassName) {
            DownloadGameListWorker::class.java.name ->
                DownloadGameListWorker(appContext, workerParameters,httpService,dataRepository)
            UpdateGameRoomsWorker::class.java.name ->
                UpdateGameRoomsWorker(appContext, workerParameters,httpService,dataRepository)
            GetDescriptionWorker::class.java.name ->
                GetDescriptionWorker(appContext, workerParameters,httpService,dataRepository)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }

}