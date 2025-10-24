package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.data.file.FileManagerImpl
import com.dv.apps.komic.reader.domain.file.FileManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::FileManagerImpl) bind FileManager::class
}