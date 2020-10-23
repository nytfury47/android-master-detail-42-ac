package com.tan.master_detail42_ac.ui

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.tan.master_detail42_ac.R
import com.tan.master_detail42_ac.ui.track_master.TrackListLoadingState

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Picasso.with(this.context)
            .load(url)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(this)
    }
}

/**
 *  Update progress bar visibility depending on TrackListLoadingState
 */
@BindingAdapter("app:isProgressComplete")
fun ProgressBar.bindVisibility(loadingState: TrackListLoadingState) {
    this.visibility = if (loadingState == TrackListLoadingState.LOADING) View.VISIBLE else View.INVISIBLE
}