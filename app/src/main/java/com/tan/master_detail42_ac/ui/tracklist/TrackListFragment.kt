package com.tan.master_detail42_ac.ui.tracklist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tan.master_detail42_ac.R
import com.tan.master_detail42_ac.data.local.AppPreferences
import com.tan.master_detail42_ac.databinding.FragmentTrackListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_track_list.*

/**
 * A fragment representing a list of Tracks.
 */
@AndroidEntryPoint
class TrackListFragment : Fragment() {
    /**
     * Lazily initialize our [TrackListViewModel].
     */
    private val viewModel: TrackListViewModel by lazy {
        ViewModelProvider(this).get(TrackListViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the TrackListFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter,
     * and other views
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentTrackListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the TrackListViewModel
        binding.viewModel = viewModel

        // RecyclerView setup
        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, 3)
        binding.tracksRecyclerView.layoutManager = if (AppPreferences.isGridLayout) gridLayoutManager else linearLayoutManager
        binding.tracksRecyclerView.adapter = TrackListAdapter(TrackListAdapter.OnClickListener {
            viewModel.onDisplayTrackDetails(it)
        })

        // For change layout option menu
        setHasOptionsMenu(true)

        // Setup ViewModel observers
        setObservers()

        // Updating the Action Bar title
        setActionBarTitle()

        return binding.root
    }

    /**
     * Inflates the overflow menu that contains the change layout option.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_recycler_manager) {
            changeLayoutManager()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setObservers() {
        // Observer for the trackList loading state
        viewModel.trackListLoadingState.observe(viewLifecycleOwner, { loadingState ->
            onTrackListLoadingStateChange(loadingState)
        })

        // Observer for action to move from master to detail
        viewModel.navigateToSelectedTrack.observe(viewLifecycleOwner, { track ->
            if (track != null) {
                this.findNavController().navigate(TrackListFragmentDirections.actionShowDetail(track, track.trackName))
                viewModel.onDisplayTrackDetailsComplete()
            }
        })
    }

    private fun onTrackListLoadingStateChange(loadingState: TrackListLoadingState) {
        if (loadingState == TrackListLoadingState.SUCCESS) {
            trackListLoadComplete()
        } else if (loadingState == TrackListLoadingState.ERROR) {
            Toast.makeText(activity, R.string.error_result, Toast.LENGTH_LONG).show()
        }
    }

    private fun trackListLoadComplete() {
        setActionBarTitle()

//        if (viewModel.trackList.value.isNullOrEmpty()) {
//            Toast.makeText(activity, R.string.no_result, Toast.LENGTH_LONG).show()
//        }
    }

    private fun setActionBarTitle() {
//        val listSize = viewModel.trackList.value?.size ?: 0
//        (activity as AppCompatActivity).supportActionBar?.title = String.format(getString(R.string.main_activity_title), listSize)
    }

    /**
     * Toggle master view's layout: either grid or linear (vertical)
     */
    private fun changeLayoutManager() {
        if (tracksRecyclerView.layoutManager == linearLayoutManager) {
            tracksRecyclerView.layoutManager = gridLayoutManager
            AppPreferences.isGridLayout = true
        } else {
            tracksRecyclerView.layoutManager = linearLayoutManager
            AppPreferences.isGridLayout = false
        }
    }
}