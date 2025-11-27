package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.data.repository.CacheManagerImpl
import com.dv.apps.komic.reader.data.repository.SettingsManagerImpl
import com.dv.apps.komic.reader.data.repository.ThumbnailManagerImpl
import com.dv.apps.komic.reader.data.repository.VirtualFileSystemImpl
import com.dv.apps.komic.reader.domain.filesystem.VirtualFileSystem
import com.dv.apps.komic.reader.domain.repository.CacheManager
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.platform.PlatformFileManager
import com.dv.apps.komic.reader.platform.PlatformFileManagerImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::PlatformFileManagerImpl) bind PlatformFileManager::class
    singleOf(::SettingsManagerImpl) bind SettingsManager::class
    singleOf(::VirtualFileSystemImpl) bind VirtualFileSystem::class
    singleOf(::CacheManagerImpl) bind CacheManager::class
    singleOf(::ThumbnailManagerImpl) bind ThumbnailManager::class
}