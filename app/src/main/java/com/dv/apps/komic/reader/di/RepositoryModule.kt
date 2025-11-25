package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.data.repository.SettingsManagerImpl
import com.dv.apps.komic.reader.data.repository.ThumbnailManagerImpl
import com.dv.apps.komic.reader.data.repository.VirtualFilesystemImpl
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.domain.repository.VirtualFilesystem
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::SettingsManagerImpl) bind SettingsManager::class
    singleOf(::VirtualFilesystemImpl) bind VirtualFilesystem::class
    singleOf(::ThumbnailManagerImpl) bind ThumbnailManager::class
}