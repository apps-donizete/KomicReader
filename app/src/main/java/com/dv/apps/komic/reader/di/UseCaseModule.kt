package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.domain.usecase.RefreshThumbnailUseCase
import org.koin.dsl.module
import org.koin.plugin.module.dsl.factory

val useCaseModule = module {
    factory<RefreshThumbnailUseCase>()
}