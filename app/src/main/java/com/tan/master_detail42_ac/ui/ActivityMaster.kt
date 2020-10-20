package com.tan.master_detail42_ac.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_master.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.tan.master_detail42_ac.R
import com.tan.master_detail42_ac.data.AppPreferences
import com.tan.master_detail42_ac.data.MasterViewModel
import com.tan.master_detail42_ac.data.Track
import com.tan.master_detail42_ac.data.TrackRequester
import com.tan.master_detail42_ac.databinding.ActivityMasterBinding

/**
 * Activity class for the master view of the track list from iTunes Search API
 */
class ActivityMaster : AppCompatActivity(), TrackRequester.TrackRequesterResponse {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var trackList: ArrayList<Track> = ArrayList()
    private lateinit var trackRequester: TrackRequester
    private var doubleBackToExitPressedOnce = false

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MasterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Last visit info
        val binding: ActivityMasterBinding = DataBindingUtil.setContentView(this, R.layout.activity_master)
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.viewmodel = viewModel

        // Setup master view's components
        title = String.format(getString(R.string.main_activity_title), trackList.size)

        linearLayoutManager = LinearLayoutManager(this)
        gridLayoutManager = GridLayoutManager(this, 3)

        // Use last layout used
        recyclerView.layoutManager = if (AppPreferences.isGridLayout) gridLayoutManager else linearLayoutManager
        adapter = RecyclerAdapter(trackList)
        recyclerView.adapter = adapter

        trackRequester = TrackRequester(this)
    }

    override fun onStart() {
        super.onStart()

        // Start request for track list
        if (trackList.size == 0) {
            progressBar.visibility = View.VISIBLE
            trackRequester.getTrack()
        }
    }

    override fun onResume() {
        super.onResume()

        // Display error message if internet is not available
        if (!isInternetAvailable()) Toast.makeText(this, R.string.check_internet, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_recycler_manager) {
            changeLayoutManager()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Confirm exit from app
     */
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.toast_a_backToExit, Toast.LENGTH_SHORT).show()
        Handler().postDelayed( { doubleBackToExitPressedOnce = false }, DELAY_EXIT)
    }

    /**
     * Handle and start displaying track data
     */
    override fun receivedNewTrackList(newTrackList: ArrayList<Track>) {
        runOnUiThread {
            progressBar.visibility = View.INVISIBLE

            for (index in newTrackList.indices) {
                trackList.add(newTrackList[index])
                adapter.notifyItemInserted(trackList.size - 1)
            }

            title = String.format(getString(R.string.main_activity_title), trackList.size)

            // No result
            if (newTrackList.isEmpty()) {
                Toast.makeText(this, R.string.no_result, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Toggle master view's layout: either grid or linear (vertical)
     */
    private fun changeLayoutManager() {
        if (recyclerView.layoutManager == linearLayoutManager) {
            recyclerView.layoutManager = gridLayoutManager
            AppPreferences.isGridLayout = true
        } else {
            recyclerView.layoutManager = linearLayoutManager
            AppPreferences.isGridLayout = false
        }
    }

    /**
     * Check for internet connection
     */
    @Suppress("DEPRECATION")
    private fun isInternetAvailable(): Boolean {
        var result = false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)        -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)    -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)    -> true
                        else                                                    -> false
                    }
                }
            }
        } else {
            val networkInfo = cm.activeNetworkInfo
            result = (networkInfo !=null && networkInfo.isConnected)
        }

        return result
    }

    companion object {
        private const val DELAY_EXIT = 2000L
    }
}
