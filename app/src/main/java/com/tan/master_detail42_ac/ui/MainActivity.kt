package com.tan.master_detail42_ac.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tan.master_detail42_ac.R
import com.tan.master_detail42_ac.data.local.AppPreferences
import com.tan.master_detail42_ac.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The app's main activity
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    /**
     * Set the content view that contains the Navigation Host.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment

        drawerLayout = binding.drawerLayout
        navController = navHostFragment.navController
        binding.lastVisit.text = String.format(getString(R.string.app_last_visit), AppPreferences.lastVisit)

        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp() = NavigationUI.navigateUp(navController, drawerLayout)

    override fun onResume() {
        super.onResume()

        // Display error message if internet is not available
        if (!isInternetAvailable()) Toast.makeText(this, R.string.check_internet, Toast.LENGTH_LONG).show()
    }

    /**
     * Confirm exit from app
     */
    override fun onBackPressed() {
        if (navController.currentDestination?.id != R.id.trackListFragment) {
            super.onBackPressed()
            return
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.toast_a_backToExit, Toast.LENGTH_SHORT).show()
        Handler().postDelayed( { doubleBackToExitPressedOnce = false }, DELAY_EXIT)
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
