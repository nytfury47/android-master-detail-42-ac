package com.tan.master_detail42_ac.data.remote

import com.tan.master_detail42_ac.data.entity.TrackList
import retrofit2.Response
import retrofit2.http.GET

/**
 * A public interface that exposes the [getTrackListAsync] method
 */
interface TrackService {
    /**
     * Returns a Coroutine [TrackList] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "search" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("search?term=star&country=au&media=movie&all")
    suspend fun getTrackListAsync(): Response<TrackList>
}