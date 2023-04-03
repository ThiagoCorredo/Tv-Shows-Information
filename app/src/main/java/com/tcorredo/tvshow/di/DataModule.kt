package com.tcorredo.tvshow.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcorredo.tvshow.BuildConfig
import com.tcorredo.tvshow.data.mapper.ActorResponseToActorDomainMapper
import com.tcorredo.tvshow.data.mapper.SearchTvShowResponseToTvShowDomainMapper
import com.tcorredo.tvshow.data.mapper.SeasonEpisodeResponseToSeasonEpisodeDomainMapper
import com.tcorredo.tvshow.data.mapper.TvShowResponseToTvShowDomainMapper
import com.tcorredo.tvshow.data.remote.TvMazeService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "BASE_URL"

val dataModule = module {
    single { TvMazeService(get()) }

    single { TvShowResponseToTvShowDomainMapper() }

    single { SearchTvShowResponseToTvShowDomainMapper() }

    single { SeasonEpisodeResponseToSeasonEpisodeDomainMapper() }

    single { ActorResponseToActorDomainMapper() }

    single { provideRetrofit(get(named(BASE_URL)), get(), get()) }

    single { provideMoshi() }

    single { provideOkHttpClient() }

    single(named(BASE_URL)) { "https://api.tvmaze.com/" }
}

private fun provideMoshi(): Moshi {
    return Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

private fun provideRetrofit(baseUrl: String, moshi: Moshi, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
        )
        .build()
}