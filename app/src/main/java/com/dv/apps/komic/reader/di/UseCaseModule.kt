package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.domain.usecase.RefreshThumbnailUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::RefreshThumbnailUseCase)
}