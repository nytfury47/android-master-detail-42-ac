package com.tan.master_detail42_ac.data

import com.tan.master_detail42_ac.data.entity.Track
import com.tan.master_detail42_ac.data.remote.TrackService
import javax.inject.Inject

class TrackRepository @Inject constructor(private val trackService: TrackService) {
    suspend fun fetchTracks(): List<Track>? {
        val response = trackService.getTrackListAsync()

        return if (response.isSuccessful) {
            response.body()?.results
        } else {
            throw Exception()
        }
    }
}