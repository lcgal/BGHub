package com.example.bghub.Background.Factory

import androidx.work.DelegatingWorkerFactory
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.Repositories.Data.DataRepository
import com.example.bghub.Repositories.Http.HttpRepository
import javax.inject.Inject

class WorkFactoryDelegator @Inject constructor(
        httpRepository: HttpRepository,
        dataRepository: DataContract.Repository
) : DelegatingWorkerFactory() {
    init {
        addFactory(RepositoryWorkerFactory(httpRepository,dataRepository))
    }
}