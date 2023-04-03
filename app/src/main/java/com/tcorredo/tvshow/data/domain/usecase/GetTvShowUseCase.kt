package com.tcorredo.tvshow.data.domain.usecase

import com.tcorredo.tvshow.data.domain.repository.TvShowRepository

class GetTvShowUseCase(private val tvShowRepository: TvShowRepository) {
    fun invoke(query: String) = tvShowRepository.getTvShows(query)
}