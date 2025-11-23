package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.data.file.DocumentTreeManagerImpl
import com.dv.apps.komic.reader.data.file.FileTreeManagerImpl
import com.dv.apps.komic.reader.domain.file.DocumentTreeManager
import com.dv.apps.komic.reader.domain.file.FileTreeManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::DocumentTreeManagerImpl) bind DocumentTreeManager::class
    singleOf(::FileTreeManagerImpl) bind FileTreeManager::class
}