package com.dv.apps.komic.reader.di

import androidx.room.Room
import com.dv.apps.komic.reader.data.KomicReaderDatabase
import org.koin.dsl.module

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            KomicReaderDatabase::class.java,
            "komic.db"
        ).fallbackToDestructiveMigration(true).build()
    }

    single { get<KomicReaderDatabase>().thumbnailDAO }
}