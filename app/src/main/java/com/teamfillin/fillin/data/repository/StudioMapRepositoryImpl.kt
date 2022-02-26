package com.teamfillin.fillin.data.repository

import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.domain.entity.StudioDetail
import com.teamfillin.fillin.domain.entity.StudioMap
import com.teamfillin.fillin.domain.repository.StudioMapRepository
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

class StudioMapRepositoryImpl @Inject constructor(
    private val service: StudioService
) : StudioMapRepository {
    override suspend fun studioLocation(): List<StudioMap.StudioPosition>? {
        runCatching {
            service.getWholeStudio().await()
        }.fold({
            return it.data.studios.map { response ->
                response.toStudioMap()
            }
        }, { return null })
    }

    override suspend fun studioDetail(position : Int): StudioDetail.Studio? {
        runCatching {
            service.getStudioDetail(position).await()
        }.fold({
            return it.data.studio.toStudioDetail()
        }, {return null})
    }
}