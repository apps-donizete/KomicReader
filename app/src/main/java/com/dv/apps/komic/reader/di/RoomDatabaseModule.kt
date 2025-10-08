package com.dv.apps.komic.reader.di

import androidx.room.Room
import com.dv.apps.komic.reader.data.KomicReaderDatabase
import com.dv.apps.komic.reader.data.folder.FolderManagerImpl
import com.dv.apps.komic.reader.domain.folder.FolderManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            KomicReaderDatabase::class.java,
            "komic.db"
        ).fallbackToDestructiveMigration(true).build()
    }

    single { get<KomicReaderDatabase>().folderDAO }

    singleOf(::FolderManagerImpl) bind FolderManager::class
}