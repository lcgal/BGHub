package com.example.bghub.background.factory

import androidx.work.DelegatingWorkerFactory
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.http.HttpService
import javax.inject.Inject

class WorkFactoryDelegator @Inject constructor(
    httpService: HttpService,
    dataRepository: DbContract
) : DelegatingWorkerFactory() {
    init {
        addFactory(RepositoryWorkerFactory(httpService,dataRepository))
    }
}