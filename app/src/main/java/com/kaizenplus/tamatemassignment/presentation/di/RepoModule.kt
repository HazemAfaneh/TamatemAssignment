package com.kaizenplus.tamatemassignment.presentation.di

import android.content.Context
import com.kaizenplus.tamatemassignment.core.repos.mainscreen.MainscreenRepo
import com.kaizenplus.tamatemassignment.data.reposImpl.MainscreenRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {
    @Singleton
    @Provides
    fun provideMainscreenRepo(
        @ApplicationContext context: Context
    ): MainscreenRepo =
        MainscreenRepoImpl(context)

}