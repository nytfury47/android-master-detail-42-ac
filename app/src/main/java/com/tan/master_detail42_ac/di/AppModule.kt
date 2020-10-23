package com.tan.master_detail42_ac.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tan.master_detail42_ac.data.*
import com.tan.master_detail42_ac.data.remote.TrackService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

// iTunes Search API: https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all
private const val BASE_URL = "https://itunes.apple.com/"

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    /**
     * Provide the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
     * full Kotlin compatibility.
     */
    @Singleton
    @Provides
    fun provideMoshi() : Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Use the Retrofit builder to provide a retrofit object using a Moshi converter with our Moshi
     * object.
     */
    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    /**
     * An API object that exposes the Retrofit service
     */
    @Provides
    fun provideTracksApiService(retrofit: Retrofit): TrackService = retrofit.create(
        TrackService::class.java)

    /**
     * Repository
     */
    @Singleton
    @Provides
    fun provideTracksRepository(tracksApiService: TrackService) = TrackRepository(tracksApiService)
}