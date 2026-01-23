package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.data.repository.CacheManagerImpl
import com.dv.apps.komic.reader.data.repository.FileReaderImpl
import com.dv.apps.komic.reader.data.repository.SettingsManagerImpl
import com.dv.apps.komic.reader.data.repository.ThumbnailManagerImpl
import com.dv.apps.komic.reader.data.repository.VirtualFileTreeManagerImpl
import com.dv.apps.komic.reader.domain.filesystem.tree.VirtualFileTreeManager
import com.dv.apps.komic.reader.domain.repository.CacheManager
import com.dv.apps.komic.reader.domain.repository.FileReader
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
    singleOf(::VirtualFileTreeManagerImpl) bind VirtualFileTreeManager::class
    singleOf(::CacheManagerImpl) bind CacheManager::class
    singleOf(::ThumbnailManagerImpl) bind ThumbnailManager::class
    singleOf(::FileReaderImpl) bind FileReader::class
}