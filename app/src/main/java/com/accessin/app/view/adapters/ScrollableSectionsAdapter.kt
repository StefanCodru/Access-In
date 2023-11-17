package com.accessin.app.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.accessin.app.databinding.ScrollableSectionsItemBinding
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.ScrollableSection
import com.squareup.picasso.Picasso

class ScrollableSectionsAdapter: RecyclerView.Adapter<ScrollableSectionsAdapter.ScrollableSectionsViewHolder>() {

    private val callback = object: DiffUtil.ItemCallback<ScrollableSection>(){
        override fun areItemsTheSame(oldItem: ScrollableSection, newItem: ScrollableSection): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ScrollableSection, newItem: ScrollableSection): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollableSectionsViewHolder {
        val binding = ScrollableSectionsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScrollableSectionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrollableSectionsViewHolder, position: Int) {
        val scrollableSection = differ.currentList[position]
        holder.bind(scrollableSection)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ScrollableSectionsViewHolder(val binding: ScrollableSectionsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(scrollableSection: ScrollableSection) {
            binding.scrollableSectionTitle.text = scrollableSection.name // Set title

            Log.i("TAG", "getScrollableSections: adapter is good ")

            val childMembersAdapter = ScrollableSectionAdapter()
            binding.scrollableSectionItemsRecyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            binding.scrollableSectionItemsRecyclerview.adapter = childMembersAdapter
            childMembersAdapter.differ.submitList(scrollableSection.locations)

            childMembersAdapter.setOnItemClickListener { location ->
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
