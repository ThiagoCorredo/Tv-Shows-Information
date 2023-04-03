package com.tcorredo.tvshow.data.domain.usecase

import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.domain.repository.TvShowRepository

class GetTvShowDetailsUseCase(private val tvShowRepository: TvShowRepository) {
    fun invoke(tvShow: TvShow?) = tvShowRepository.getTvShowDetails(tvShow)
}