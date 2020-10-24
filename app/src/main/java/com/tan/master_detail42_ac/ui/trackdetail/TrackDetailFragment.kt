package com.tan.master_detail42_ac.ui.trackdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tan.master_detail42_ac.databinding.FragmentTrackDetailBinding

/**
 * This [Fragment] shows the detailed information about a selected Track.
 * It sets this information in the [TrackDetailViewModel], which it gets as a Parcelable property
 * through Jetpack Navigation's SafeArgs.
 */
class TrackDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentTrackDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val track = TrackDetailFragmentArgs.fromBundle(requireArguments()).selectedTrack
        val viewModelFactory = TrackDetailViewModelFactory(track, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(TrackDetailViewModel::class.java)

        return binding.root
    }
}