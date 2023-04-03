package com.tcorredo.tvshow.data.domain.usecase

import com.tcorredo.tvshow.data.domain.repository.ActorRepository

class GetActorUseCase(private val actorRepository: ActorRepository) {
    fun invoke() = actorRepository.getActors()
}