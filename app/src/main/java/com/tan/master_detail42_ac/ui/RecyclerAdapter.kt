package com.tan.master_detail42_ac.ui

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import com.tan.master_detail42_ac.R
import com.tan.master_detail42_ac.data.MasterViewModel
import com.tan.master_detail42_ac.data.Track
import com.tan.master_detail42_ac.databinding.RecyclerViewItemRowBinding
import com.tan.master_detail42_ac.util.inflate
import kotlinx.android.synthetic.main.recycler_view_item_row.view.*

/**
 * Main recycler adapter for the recycler view used by the master view
 */
class RecyclerAdapter(
    private val viewModel: MasterViewModel,
    private val parentLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<RecyclerAdapter.TrackHolder>()  {

    companion object: DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem.trackName == newItem.trackName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val binding = DataBindingUtil.inflate<RecyclerViewItemRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recycler_view_item_row,
            parent,
            false
        )
        return TrackHolder(binding)
    }

    override fun getItemCount() = viewModel.trackList.value?.size ?: 0

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.binding.track = viewModel.trackList.value!![position]
        holder.binding.lifecycleOwner = parentLifecycleOwner
        holder.binding.executePendingBindings()
    }

    class TrackHolder(val binding: RecyclerViewItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    //

//    class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder> (Companion) {
//
//        class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
//
//        companion object: DiffUtil.ItemCallback<User>() {
//            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem === newItem
//            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ItemUserBinding.inflate(layoutInflater)
//
//            return UserViewHolder(binding)
//        }
//
//        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//            val currentUser = getItem(position)
//            holder.binding.user = currentUser
//            holder.binding.executePendingBindings()
//        }
//    }

    //

//    class TrackHolder(private val binding: RecyclerViewItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: Track, viewLifecycleOwner: LifecycleOwner, viewModel: MasterViewModel) {
//            binding.run {
//                lifecycleOwner = viewLifecycleOwner
//                track = item
//                this.viewModel = viewModel
//
//                executePendingBindings()
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        return TrackHolder(RecyclerViewItemRowBinding.inflate(layoutInflater, parent, false))
//    }
//
//    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
//        holder.bind(viewModel.trackList.value!![position], parentLifecycleOwner, viewModel)
//    }

//

//    class TrackHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
//        private var view: View = v
//        private var track: Track? = null
//
//        init {
//            v.setOnClickListener(this)
//        }
//
//        /**
//         * Handle switching from master view to detail view
//         */
//        override fun onClick(v: View) {
//            val context = itemView.context
//            val showTrackIntent = Intent(context, ActivityDetail::class.java)
//            showTrackIntent.putExtra(TRACK_KEY, track)
//            context.startActivity(showTrackIntent)
//        }
//
//        fun bindTrack(track: Track) {
//            this.track = track
//
//            // Set track info
//            Picasso.with(view.context)
//                .load(track.artwork)
//                .placeholder(R.drawable.placeholder_image)
//                .error(R.drawable.placeholder_image)
//                .into(view.itemImage)
//            view.itemName.text = track.trackName
//            view.itemGenre.text = track.genre
//            view.itemPrice.text = String.format(view.context.getString(R.string.txt_activity_master_track_price), track.price)
//        }
//
//        companion object {
//            private const val TRACK_KEY = "TRACK"
//        }
//    }
}