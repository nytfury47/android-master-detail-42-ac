package com.tan.master_detail42_ac.data.repository

import com.tan.master_detail42_ac.data.local.TrackDao
import com.tan.master_detail42_ac.data.remote.TrackRemoteDataSource
import com.tan.master_detail42_ac.util.performGetOperation
import javax.inject.Inject

/**
 * The main repository which provides the method for getting the data to be used by the ViewModel.
 * First try to get our data from the local data source (AppDatabase) if available.
 * To keep the app synced, we also fetch data from the remote data source (TrackRemoteDataSource).
 * Finally, save the result from the remote call in the database, in order to keep it updated.
 */
class TrackRepository @Inject constructor(
    private val remoteDataSource: TrackRemoteDataSource,
    private val localDataSource: TrackDao
) {
    fun getTracks() = performGetOperation(
        databaseQuery = { localDataSource.getAllTracks() },
        networkCall = { remoteDataSource.getTracks() },
        saveCallResult = { it.results?.let { it1 -> localDataSource.insertAll(it1) } }
    )
}