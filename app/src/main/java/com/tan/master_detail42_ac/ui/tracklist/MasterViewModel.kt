package com.tan.master_detail42_ac.ui.tracklist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tan.master_detail42_ac.data.AppPreferences
import com.tan.master_detail42_ac.data.entity.Track
import com.tan.master_detail42_ac.data.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A VM for [com.tan.master_detail42_ac.ui.tracklist.ActivityMaster].
 */
class MasterViewModel @ViewModelInject constructor(
    private val tracksRepository: TrackRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _trackList = MutableLiveData<List<Track>>(listOf())
    private val _trackListLoadingState = MutableLiveData(
        TrackListLoadingState.NOT_STARTED
    )

    val lastVisit = AppPreferences.lastVisit
    val trackList: LiveData<List<Track>> = _trackList
    val trackListLoadingState: LiveData<TrackListLoadingState> = _trackListLoadingState

    init {
        loadTrackList()
    }

    private fun loadTrackList() {
        _trackListLoadingState.value = TrackListLoadingState.LOADING

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tracks = tracksRepository.fetchTracks()
                _trackList.postValue(tracks)
                _trackListLoadingState.postValue(TrackListLoadingState.LOADED)
            } catch (e: Exception) {
                _trackListLoadingState.postValue(TrackListLoadingState.ERROR)
            }
        }
    }

    fun onTrackListLoadingComplete() {
        _trackListLoadingState.value = TrackListLoadingState.COMPLETE
    }

}

enum class TrackListLoadingState {
    NOT_STARTED,
    LOADING,
    LOADED,
    COMPLETE,
    ERROR
}