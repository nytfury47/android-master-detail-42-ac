package com.tan.master_detail42_ac.ui.tracklist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tan.master_detail42_ac.data.entity.Track
import com.tan.master_detail42_ac.data.repository.TrackRepository

/**
 * A VM for [com.tan.master_detail42_ac.ui.tracklist.TrackListFragment].
 */
class TrackListViewModel @ViewModelInject constructor(
    repository: TrackRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _navigateToSelectedTrack = MutableLiveData<Track>()

    val trackList = repository.getTracks()
    val navigateToSelectedTrack: LiveData<Track> = _navigateToSelectedTrack

    fun onDisplayTrackDetails(track: Track) {
        _navigateToSelectedTrack.value = track
    }

    fun onDisplayTrackDetailsComplete() {
        _navigateToSelectedTrack.value = null
    }
}