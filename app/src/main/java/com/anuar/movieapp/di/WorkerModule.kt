package com.anuar.movieapp.di


import com.anuar.movieapp.data.worker.RefreshDataWorker
import com.anuar.movieapp.data.worker.RefreshDataWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    @Binds
    fun bindRefreshDataWorkerFactory(worker: RefreshDataWorker.Factory): RefreshDataWorkerFactory
}