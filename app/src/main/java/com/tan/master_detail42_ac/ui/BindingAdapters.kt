package com.tan.master_detail42_ac.ui

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tan.master_detail42_ac.R
import com.tan.master_detail42_ac.data.entity.Track
import com.tan.master_detail42_ac.ui.tracklist.TrackListAdapter
import com.tan.master_detail42_ac.ui.tracklist.TrackListLoadingState

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Picasso.with(this.context)
            .load(url)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
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

/**
 * When there is no Track property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("app:listData")
fun RecyclerView.bindData(data: List<Track>?) {
    val adapter = this.adapter as TrackListAdapter
    adapter.submitList(data)
}