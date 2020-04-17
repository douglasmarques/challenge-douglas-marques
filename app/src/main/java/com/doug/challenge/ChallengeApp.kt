package com.doug.challenge

import android.app.Application
import com.doug.challenge.di.apiModule
import com.doug.challenge.di.repositoryModule
import com.doug.challenge.di.retrofitModule
import com.doug.challenge.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ChallengeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@ChallengeApp)
            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule))
        }
    }
}
