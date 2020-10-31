package com.tan.master_detail42_ac.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tan.master_detail42_ac.data.entity.Track

/**
 * The Data Access Object (DAO) interface to access and query the RoomDatabase (AppDatabase)
 */
@Dao
interface TrackDao {
    // 1: Select all tracks
    @Query("SELECT * FROM tracks")
    fun getAllTracks() : LiveData<List<Track>>

    // 2: Insert new tracks (replace/update if a track has the same id with an existing track)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<Track>)
}