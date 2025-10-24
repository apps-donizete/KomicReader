package com.dv.apps.komic.reader

import android.app.Application
import com.dv.apps.komic.reader.di.repositoryModule
import com.dv.apps.komic.reader.di.roomDatabaseModule
import com.dv.apps.komic.reader.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.dsl.koinConfiguration

class MainApplication : Application(), KoinStartup {

    override fun onKoinStartup() = koinConfiguration {
        androidLogger()
        androidContext(this@MainApplication)
        modules(
            roomDatabaseModule,
            repositoryModule,
            viewModelModule
        )
    }
}