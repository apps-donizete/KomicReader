package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.data.repository.FileReaderImpl
import com.dv.apps.komic.reader.data.repository.SettingsManagerImpl
import com.dv.apps.komic.reader.data.repository.ThumbnailManagerImpl
import com.dv.apps.komic.reader.data.repository.VirtualFileTreeManagerImpl
import com.dv.apps.komic.reader.domain.repository.FileReader
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.filesystem.platform.PlatformFileManager
import com.dv.apps.komic.reader.filesystem.platform.PlatformFileManagerImpl
import com.dv.apps.komic.reader.filesystem.tree.VirtualFileTreeManager
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val repositoryModule = module {
    single<PlatformFileManagerImpl>() bind PlatformFileManager::class
    single<SettingsManagerImpl>() bind SettingsManager::class
    single<VirtualFileTreeManagerImpl>() bind VirtualFileTreeManager::class
    single<ThumbnailManagerImpl>() bind ThumbnailManager::class
    single<FileReaderImpl>() bind FileReader::class
}