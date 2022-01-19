package com.teamfillin.fillin.config.di

import com.teamfillin.fillin.data.repository.AuthRepositoryImpl
import com.teamfillin.fillin.data.repository.PhotoPagingRepository
import com.teamfillin.fillin.data.service.experimental.PagingService
import com.teamfillin.fillin.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository = authRepository

    @Provides
    @Singleton
    fun providePhotoPagingRepository(service: PagingService): PhotoPagingRepository =
        PhotoPagingRepository(service)
}