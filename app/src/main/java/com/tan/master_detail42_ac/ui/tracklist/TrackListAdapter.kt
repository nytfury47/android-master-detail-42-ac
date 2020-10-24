package com.tan.master_detail42_ac.ui.tracklist

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tan.master_detail42_ac.data.entity.Track
import com.tan.master_detail42_ac.databinding.FragmentTrackListRecyclerViewItemRowBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class TrackListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Track, TrackListAdapter.TrackViewHolder>(DiffCallback) {

    /**
     * The TrackViewHolder constructor takes the binding variable from the associated
     * RecyclerViewItem, which nicely gives it access to the full [Track] information.
     */
    class TrackViewHolder(private var binding: FragmentTrackListRecyclerViewItemRowBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.track = track
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Track]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.trackName == newItem.trackName
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TrackViewHolder {
        return TrackViewHolder(FragmentTrackListRecyclerViewItemRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(track)
        }
        holder.bind(track)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Track]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Track]
     */
    class OnClickListener(val clickListener: (track:Track) -> Unit) {
        fun onClick(track:Track) = clickListener(track)
    }

}