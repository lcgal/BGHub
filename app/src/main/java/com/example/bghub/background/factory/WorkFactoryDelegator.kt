package com.example.bghub.background.factory

import androidx.work.DelegatingWorkerFactory
import com.example.bghub.repositories.data.DataContract
import com.example.bghub.repositories.Http.HttpRepository
import javax.inject.Inject

class WorkFactoryDelegator @Inject constructor(
        httpRepository: HttpRepository,
        dataRepository: DataContract.Repository
) : DelegatingWorkerFactory() {
    init {
        addFactory(RepositoryWorkerFactory(httpRepository,dataRepository))
    }
}