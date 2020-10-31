package com.tan.master_detail42_ac.ui.trackdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tan.master_detail42_ac.data.entity.Track

/**
 * A VM for [com.tan.master_detail42_ac.ui.trackdetail.TrackDetailFragment].
 */
class TrackDetailViewModel(track: Track, app: Application) : AndroidViewModel(app) {
    private val _selectedTrack = MutableLiveData<Track>()

    val selectedTrack: LiveData<Track> = _selectedTrack

    init {
        _selectedTrack.value = track
    }
}