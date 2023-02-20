package com.accessin.app.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.accessin.app.databinding.ActivityMainBinding.inflate
import com.accessin.app.databinding.SavedLocationItemBinding
import com.accessin.app.databinding.ScrollableSectionLocationItemBinding
import com.accessin.app.databinding.ScrollableSectionsItemBinding
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.squareup.picasso.Picasso

class SavedLocationsAdapter: RecyclerView.Adapter<SavedLocationsAdapter.SavedLocationsViewHolder>() {

    private val callback = object: DiffUtil.ItemCallback<Location>(){
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.geoPoint == newItem.geoPoint
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedLocationsViewHolder {
        val binding = SavedLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedLocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedLocationsViewHolder, position: Int) {
        val location = differ.currentList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SavedLocationsViewHolder(val binding: SavedLocationItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(location: Location) {
            binding.savedLocationTitle.text = location.name
            binding.savedLocationAddress.text = location.address
            binding.savedLocationRating.rating = location.general_rating?.toFloat()!!
            Picasso.get().load("https://newsviews.thuraswiss.com/wp-content/uploads/2019/10/Starbucks-postpones-Myanmar-entry.jpg").into(binding.savedLocationItemImage)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(location)
                }
            }
        }
    }

    private var onItemClickListener: ((Location) -> Unit)? = null

    fun setOnItemClickListener(listener : (Location) -> Unit){
        onItemClickListener = listener
    }

}