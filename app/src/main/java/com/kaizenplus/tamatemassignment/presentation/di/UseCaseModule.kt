package com.kaizenplus.tamatemassignment.presentation.di

import android.content.Context
import com.kaizenplus.tamatemassignment.core.repos.mainscreen.MainscreenRepo
import com.kaizenplus.tamatemassignment.core.usecases.mainscreen.MainscreenUsecase
import com.kaizenplus.tamatemassignment.core.usecases.mainscreen.MainscreenUsecaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideMainscreenUsecase(
        repo: MainscreenRepo
    ): MainscreenUsecase =
        MainscreenUsecaseImpl(repo)
}