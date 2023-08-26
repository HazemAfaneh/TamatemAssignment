package com.kaizenplus.tamatemassignment.presentation.di

import android.content.Context
import com.kaizenplus.tamatemassignment.core.repos.mainscreen.MainscreenRepo
import com.kaizenplus.tamatemassignment.data.reposImpl.MainscreenRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    @TamatemURL
    fun provideTamatemURL(
    ): String = "https://tamatemplus.com"


    @Singleton
    @Provides
    @GoogleURL
    fun provideGoogleURL(
    ): String = "https://www.google.com/search?q=tamatem&sca_esv=560314180&sxsrf=AB5stBhmi7w_Aq_TB6hLEWahk8mKfFz4Uw%3A1693045456523&ei=0NLpZPu7H8_okgWkoaS4Dg&ved=0ahUKEwj745jojfqAAxVPtKQKHaQQCecQ4dUDCBA&uact=5&oq=tamatem&gs_lp=Egxnd3Mtd2l6LXNlcnAiB3RhbWF0ZW0yBBAjGCcyBBAjGCcyBxAjGIoFGCcyDhAuGIoFGMcBGNEDGJECMgUQABiABDIKEAAYgAQYFBiHAjIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgARInwdQjAVYjAVwAngAkAEAmAGeAaABlwKqAQMwLjK4AQPIAQD4AQHCAgoQABhHGNYEGLAD4gMEGAAgQYgGAZAGBg&sclient=gws-wiz-serp"


    @Singleton
    @Provides
    @FacebookURL
    fun provideFacebookURL(
    ): String = "https://www.facebook.com/tamatemgames/"


    @Singleton
    @Provides
    @InstagramURL
    fun provideInstagramURL(
    ): String = "https://www.instagram.com/tamatemgames/?hl=en"





    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TamatemURL

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GoogleURL

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class FacebookURL

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class InstagramURL

}