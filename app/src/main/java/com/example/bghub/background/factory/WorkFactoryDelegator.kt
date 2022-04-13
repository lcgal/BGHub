package com.example.bghub.background.factory

import androidx.work.DelegatingWorkerFactory
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.Http.HttpRepository
import javax.inject.Inject

class WorkFactoryDelegator @Inject constructor(
        httpRepository: HttpRepository,
        dataRepository: DbContract.Repository
) : DelegatingWorkerFactory() {
    init {
        addFactory(RepositoryWorkerFactory(httpRepository,dataRepository))
    }
}