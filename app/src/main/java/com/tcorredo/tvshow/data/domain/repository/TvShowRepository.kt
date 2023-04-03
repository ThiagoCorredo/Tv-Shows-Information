package com.tcorredo.tvshow.data.domain.repository

import androidx.paging.PagingData
import com.tcorredo.tvshow.data.domain.entity.TvShow
import com.tcorredo.tvshow.data.domain.entity.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getTvShows(query: String): Flow<PagingData<TvShow>>

    fun getTvShowDetails(tvShow: TvShow?): Flow<TvShowDetails>
}