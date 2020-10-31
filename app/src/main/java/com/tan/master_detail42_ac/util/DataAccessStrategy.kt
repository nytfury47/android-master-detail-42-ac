package com.tan.master_detail42_ac.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tan.master_detail42_ac.util.Resource.Status.*
import kotlinx.coroutines.Dispatchers

/**
 * For the TrackRepository and the caching strategy.
 * First try to get our data from the local data source (AppDatabase) if available.
 * To keep the app synced, we also fetch data from the remote data source (TrackRemoteDataSource).
 * Finally, save the result from the remote call in the database, in order to keep it updated.
 */
fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }