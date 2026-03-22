package com.dv.apps.komic.reader.di

import com.dv.apps.komic.reader.feature.settings.folder.FolderSourceSettingsSectionViewModel
import com.dv.apps.komic.reader.feature.settings.preview.PreviewSettingsSectionViewModel
import com.dv.apps.komic.reader.feature.shelf.ShelfScreenViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val viewModelModule = module {
    viewModel<ShelfScreenViewModel>()
    viewModel<FolderSourceSettingsSectionViewModel>()
    viewModel<PreviewSettingsSectionViewModel>()
}