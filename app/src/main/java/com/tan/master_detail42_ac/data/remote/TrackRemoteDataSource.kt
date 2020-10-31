package com.tan.master_detail42_ac.data.remote

import javax.inject.Inject

/**
 * Provides the method for fetching the track list from the remote source.
 */
class TrackRemoteDataSource @Inject constructor(
    private val trackService: TrackService
): BaseDataSource() {
    suspend fun getTracks() = getResult { trackService.getTrackListAsync() }
}