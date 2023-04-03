package com.tcorredo.tvshow.ui.tvshow_details

import com.tcorredo.tvshow.data.domain.entity.TvShowDetails

sealed class TvShowDetailsState {
    object Loading : TvShowDetailsState()
    data class Success(val tvShowDetails: TvShowDetails) : TvShowDetailsState()
    data class Error(val message: String?) : TvShowDetailsState()
}
