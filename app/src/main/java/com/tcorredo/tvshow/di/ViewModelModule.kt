package com.tcorredo.tvshow.di

import com.tcorredo.tvshow.ui.actor.ActorViewModel
import com.tcorredo.tvshow.ui.tvshow_details.TvShowDetailsViewModel
import com.tcorredo.tvshow.ui.tvshow_list.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TvShowViewModel(get()) }
    viewModel { TvShowDetailsViewModel(get()) }
    viewModel { ActorViewModel(get()) }
}