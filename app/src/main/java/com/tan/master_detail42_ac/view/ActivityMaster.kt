package com.tan.master_detail42_ac.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_master.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tan.master_detail42_ac.R
import com.tan.master_detail42_ac.model.AppPreferences
import com.tan.master_detail42_ac.view_model.MasterViewModel
import com.tan.master_detail42_ac.view_model.TrackListLoadingState
import com.tan.master_detail42_ac.databinding.ActivityMasterBinding

/**
 * Activity class for the master view of the track list from iTunes Search API
 */
class ActivityMaster : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private var doubleBackToExitPressedOnce = false

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProvider(this).get(MasterViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMasterBinding = DataBindingUtil.setContentView(this, R.layout.activity_master)
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.viewmodel = viewModel

        setupViews()
        setupObservers()
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

    private fun onTrackListLoadingStateChange(loadingState: TrackListLoadingState) {
        if (loadingState == TrackListLoadingState.LOADED) {
            trackListLoadFinish()
            viewModel.onTrackListLoadingComplete()
        }
    }

    private fun trackListLoadFinish() {
        updateActivityTitle()
        if (viewModel.trackList.value.isNullOrEmpty()) {
            Toast.makeText(this, R.string.no_result, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateActivityTitle() {
        val listSize = viewModel.trackList.value?.size ?: 0
        title = String.format(getString(R.string.main_activity_title), listSize)
    }

    private fun setupViews() {
        // Activity Title
        updateActivityTitle()

        // RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = if (AppPreferences.isGridLayout) gridLayoutManager else linearLayoutManager
        recyclerView.adapter = RecyclerAdapter()
    }

    private fun setupObservers() {
        // Observer for trackList
        viewModel.trackList.observe(this, {
            val adapter = recyclerView.adapter as RecyclerAdapter
            adapter.submitList(it)
        })

        // Observer for the trackList loading state
        viewModel.trackListLoadingState.observe(this, { loadingState ->
            onTrackListLoadingStateChange(loadingState)
        })
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
