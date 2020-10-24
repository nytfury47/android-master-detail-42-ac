package com.tan.master_detail42_ac.ui.tracklist

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tan.master_detail42_ac.data.entity.Track
import com.tan.master_detail42_ac.databinding.RecyclerViewItemRowBinding

/**
 * Main recycler adapter for the recycler view used by the master view
 */
class RecyclerAdapter : ListAdapter<Track, RecyclerAdapter.ViewHolder>(TrackDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: RecyclerViewItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Track) {
            binding.track = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewItemRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem.trackName == newItem.trackName
    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem == newItem
}