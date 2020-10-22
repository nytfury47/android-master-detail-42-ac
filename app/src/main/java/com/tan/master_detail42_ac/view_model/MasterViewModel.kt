package com.tan.master_detail42_ac.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tan.master_detail42_ac.model.AppPreferences
import com.tan.master_detail42_ac.model.Track
import com.tan.master_detail42_ac.model.TrackRequester
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A VM for [com.tan.master_detail42_ac.view.ActivityMaster].
 */
class MasterViewModel : ViewModel(), TrackRequester.TrackRequesterResponse {
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
            TrackRequester(this@MasterViewModel).getTrack()
        }
    }

    fun onTrackListLoadingComplete() {
        _trackListLoadingState.value = TrackListLoadingState.COMPLETE
    }

    /**
     * Result of fetching track list
     */
    override fun receivedNewTrackList(newTrackList: ArrayList<Track>) {
        _trackList.postValue(newTrackList)
        _trackListLoadingState.postValue(TrackListLoadingState.LOADED)
    }

}

enum class TrackListLoadingState {
    NOT_STARTED,
    LOADING,
    LOADED,
    COMPLETE,
    ERROR
}