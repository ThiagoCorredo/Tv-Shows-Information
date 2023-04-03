package com.tcorredo.tvshow.data.remote

import com.tcorredo.tvshow.data.remote.response.*
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeService {

    @GET("shows")
    suspend fun getTvShows(
        @Query("page") page: Int
    ): List<ShowResponse>

    @GET("search/shows")
    suspend fun searchTvShows(
        @Query("q") query: String
    ): List<SearchTvShowResponse>

    @GET("shows/{tvShowId}/seasons")
    suspend fun getTvShowSeasons(
        @Path("tvShowId") tvShowId: Long?
    ): List<SeasonResponse>

    @GET("seasons/{idSeason}/episodes")
    suspend fun getSeasonEpisodes(
        @Path("idSeason") idSeason: Long
    ): List<EpisodeResponse>

    @GET("people")
    suspend fun getActors(
        @Query("page") page: Int
    ): List<ActorResponse>

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<TvMazeService>()
    }
}