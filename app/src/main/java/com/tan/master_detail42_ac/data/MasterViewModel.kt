package com.tan.master_detail42_ac.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * A VM for [com.tan.master_detail42_ac.ui.ActivityMaster].
 */
class MasterViewModel : ViewModel(), TrackRequester.TrackRequesterResponse {
    private val _trackList = MutableLiveData<List<Track>>(listOf())
    private val _eventTrackListLoadFinish = MutableLiveData<Boolean?>(null)

    val lastVisit = AppPreferences.lastVisit
    val trackList: LiveData<List<Track>> = _trackList
    val eventTrackListLoadFinish: LiveData<Boolean?> = _eventTrackListLoadFinish

    init {
        loadTrackList()
    }

    private fun loadTrackList() {
        val trackRequester = TrackRequester(this)

        viewModelScope.launch {
            trackRequester.getTrack()
        }
    }

    fun onTrackListLoadFinishComplete() {
        _eventTrackListLoadFinish.value = false
    }

    /**
     * Result of fetching track list
     */
    override fun receivedNewTrackList(newTrackList: ArrayList<Track>) {
        _trackList.postValue(newTrackList)
        _eventTrackListLoadFinish.postValue(true)
    }

}