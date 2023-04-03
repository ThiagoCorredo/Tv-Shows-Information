package com.tcorredo.tvshow.di

import com.tcorredo.tvshow.data.domain.dispatchers.CoroutineDispatchers
import com.tcorredo.tvshow.data.domain.dispatchers.CoroutineDispatchersImpl
import com.tcorredo.tvshow.data.domain.repository.ActorRepository
import com.tcorredo.tvshow.data.domain.repository.TvShowRepository
import com.tcorredo.tvshow.data.domain.usecase.GetActorUseCase
import com.tcorredo.tvshow.data.domain.usecase.GetTvShowDetailsUseCase
import com.tcorredo.tvshow.data.domain.usecase.GetTvShowUseCase
import com.tcorredo.tvshow.data.impl.ActorRepositoryImpl
import com.tcorredo.tvshow.data.impl.TvShowRepositoryImpl
import com.tcorredo.tvshow.data.mapper.ActorResponseToActorDomainMapper
import com.tcorredo.tvshow.data.mapper.SearchTvShowResponseToTvShowDomainMapper
import com.tcorredo.tvshow.data.mapper.SeasonEpisodeResponseToSeasonEpisodeDomainMapper
import com.tcorredo.tvshow.data.mapper.TvShowResponseToTvShowDomainMapper
import org.koin.dsl.module

val domainModule = module {
    single<CoroutineDispatchers> { CoroutineDispatchersImpl() }

    single<TvShowRepository> {
        TvShowRepositoryImpl(
            get(),
            get(),
            responseToDomain = get<TvShowResponseToTvShowDomainMapper>(),
            searchResponseToDomain = get<SearchTvShowResponseToTvShowDomainMapper>(),
            episodeResponseToDomain = get<SeasonEpisodeResponseToSeasonEpisodeDomainMapper>()
        )
    }

    single<ActorRepository> {
        ActorRepositoryImpl(
            get(),
            get(),
            responseToDomain = get<ActorResponseToActorDomainMapper>()
        )
    }

    factory { GetTvShowUseCase(get()) }
    factory { GetTvShowDetailsUseCase(get()) }
    factory { GetActorUseCase(get()) }
}
