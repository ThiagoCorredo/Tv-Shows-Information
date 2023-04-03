package com.tcorredo.tvshow.data.mapper

import com.tcorredo.tvshow.data.domain.Mapper
import com.tcorredo.tvshow.data.domain.entity.Actor
import com.tcorredo.tvshow.data.remote.response.ActorResponse

class ActorResponseToActorDomainMapper : Mapper<ActorResponse, Actor> {
    override fun invoke(actorResponse: ActorResponse): Actor {
        return Actor(
            id = actorResponse.id,
            name = actorResponse.name,
            image = actorResponse.images?.medium ?: "",
        )
    }
}
