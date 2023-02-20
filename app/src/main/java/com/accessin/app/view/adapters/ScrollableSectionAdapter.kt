package com.accessin.app.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.accessin.app.databinding.ScrollableSectionLocationItemBinding
import com.accessin.app.model.data.dataclasses.Location
import com.squareup.picasso.Picasso

class ScrollableSectionAdapter:  RecyclerView.Adapter<ScrollableSectionAdapter.ScrollableSectionViewHolder>() {

    private val callback = object: DiffUtil.ItemCallback<Location>(){
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.geoPoint == newItem.geoPoint
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollableSectionViewHolder {
        val binding = ScrollableSectionLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScrollableSectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrollableSectionViewHolder, position: Int) {
        val location = differ.currentList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ScrollableSectionViewHolder(val binding: ScrollableSectionLocationItemBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(location: Location){
            binding.scrollableSectionLocationTitle.text = location.name
            binding.scrollableSectionLocationRating.rating = location.general_rating?.toFloat()!!
            Picasso.get().load("https://newsviews.thuraswiss.com/wp-content/uploads/2019/10/Starbucks-postpones-Myanmar-entry.jpg").into(binding.scrollableSectionLocationImage)

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