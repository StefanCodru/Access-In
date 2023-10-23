package com.accessin.app.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.accessin.app.databinding.ScrollableSectionLocationItemBinding
import com.accessin.app.databinding.SearchedLocationItemBinding
import com.accessin.app.model.data.dataclasses.Location
import com.squareup.picasso.Picasso

class SearchedLocationsAdapter:  RecyclerView.Adapter<SearchedLocationsAdapter.SearchedLocationsViewHolder>() {

    private val callback = object: DiffUtil.ItemCallback<Location>(){
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.geoPoint == newItem.geoPoint
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedLocationsViewHolder {
        val binding = SearchedLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedLocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchedLocationsViewHolder, position: Int) {
        val location = differ.currentList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class SearchedLocationsViewHolder(val binding: SearchedLocationItemBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(location: Location){
            binding.searchedLocationTitle.text = location.name
            binding.searchedLocationAddress.text = location.address
            binding.searchedLocationRating.rating = location.general_rating?.toFloat()!!
            Picasso.get().load("https://newsviews.thuraswiss.com/wp-content/uploads/2019/10/Starbucks-postpones-Myanmar-entry.jpg").into(binding.searchedLocationItemImage)

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