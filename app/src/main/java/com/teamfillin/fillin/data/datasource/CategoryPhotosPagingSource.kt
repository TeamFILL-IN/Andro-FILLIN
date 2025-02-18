package com.teamfillin.fillin.data.datasource

import com.teamfillin.fillin.core.base.OffsetPagingSource
import com.teamfillin.fillin.core.base.START_POSITION_INDEX
import com.teamfillin.fillin.data.response.experimental.CategoryPhotoDto
import com.teamfillin.fillin.data.service.PagingService
import timber.log.Timber

class CategoryPhotosPagingSource(
    private val service: PagingService,
    private val styleId: Int,
    private val filmId: Int
) : OffsetPagingSource<CategoryPhotoDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryPhotoDto> {
        val currentPosition = params.key ?: START_POSITION_INDEX

        val response = runCatching {
            if (styleId != -1) service.retrievePhotosByStyle(styleId, currentPosition)
            else service.retrievePhotosByFilm(filmId, currentPosition)
        }.getOrElse {
            Timber.e(it)
            return LoadResult.Error(it)
        }

        val nextPositon = if (response.data.photos.isEmpty()) null else currentPosition + 1
        val previousPosition =
            if (currentPosition != START_POSITION_INDEX) null else currentPosition - 1
        return runCatching {
            LoadResult.Page(
                data = response.data.photos,
                prevKey = previousPosition,
                nextKey = nextPositon
            )
        }.getOrElse {
            Timber.e(it)
            LoadResult.Error(it)
        }
    }
}