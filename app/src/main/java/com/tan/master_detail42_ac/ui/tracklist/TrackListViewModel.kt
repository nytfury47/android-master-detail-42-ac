package com.tan.master_detail42_ac.ui.tracklist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tan.master_detail42_ac.data.entity.Track
import com.tan.master_detail42_ac.data.TrackRepository
import kotlinx.coroutines.launch

/**
 * A VM for [com.tan.master_detail42_ac.ui.tracklist.TrackListFragment].
 */
class TrackListViewModel @ViewModelInject constructor(
    private val tracksRepository: TrackRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _trackList = MutableLiveData<List<Track>>()
    private val _trackListLoadingState = MutableLiveData<TrackListLoadingState>()
    private val _navigateToSelectedTrack = MutableLiveData<Track>()

    val trackList: LiveData<List<Track>> = _trackList
    val trackListLoadingState: LiveData<TrackListLoadingState> = _trackListLoadingState
    val navigateToSelectedTrack: LiveData<Track> = _navigateToSelectedTrack

    init {
        loadTrackList()
    }

    private fun loadTrackList() {
        viewModelScope.launch {
            _trackListLoadingState.value = TrackListLoadingState.LOADING
            try {
                _trackList.value = tracksRepository.fetchTracks()
                _trackListLoadingState.value = TrackListLoadingState.SUCCESS
            } catch (e: Exception) {
                _trackListLoadingState.value = TrackListLoadingState.ERROR
                _trackList.value = listOf()
            }
        }
    }

    fun onDisplayTrackDetails(track: Track) {
        _navigateToSelectedTrack.value = track
    }

    fun onDisplayTrackDetailsComplete() {
        _navigateToSelectedTrack.value = null
    }

}

enum class TrackListLoadingState {
    LOADING,
    SUCCESS,
    ERROR
}