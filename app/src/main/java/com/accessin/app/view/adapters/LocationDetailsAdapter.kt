package com.accessin.app.view.adapters

import android.opengl.Visibility
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.accessin.app.databinding.AccessibilityDetailsRowBinding
import com.accessin.app.databinding.ScrollableSectionLocationItemBinding
import com.accessin.app.model.data.dataclasses.Location
import com.accessin.app.model.data.dataclasses.LocationAccessibilityDetails
import com.squareup.picasso.Picasso

class LocationDetailsAdapter:  RecyclerView.Adapter<LocationDetailsAdapter.LocationDetailsViewHolder>() {

    private val TAG = "LocationDetailsAdapter"

    private val callback = object: DiffUtil.ItemCallback<LocationAccessibilityDetails>(){
        override fun areItemsTheSame(oldItem: LocationAccessibilityDetails, newItem: LocationAccessibilityDetails): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: LocationAccessibilityDetails, newItem: LocationAccessibilityDetails): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDetailsViewHolder {
        val binding = AccessibilityDetailsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationDetailsViewHolder, position: Int) {
        val location = differ.currentList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class LocationDetailsViewHolder(val binding: AccessibilityDetailsRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(locationDetails: LocationAccessibilityDetails) {

            Log.i(TAG, "bind: oii we binding and $locationDetails")

            binding.areaDetailsTitle.text = locationDetails.title
            binding.areaDetailsDescription.text = locationDetails.description
            binding.areaDetailsAuditoryDisabilitiesRating.rating = locationDetails.auditoryRating.toFloat()
            binding.areaDetailsVisualDisabilitiesRating.rating = locationDetails.visualRating.toFloat()
            binding.areaDetailsMovementDisabilitiesRating.rating = locationDetails.mobilityRating.toFloat()

            binding.root.setOnClickListener {
                // Expand/Collapse Constraint Layout
                val isVisible = binding.accessibilityDetailsExtraConstraintLayout.visibility == View.VISIBLE
                val transition = AutoTransition().apply { duration = 300 } // Duration for the animation
                TransitionManager.beginDelayedTransition(binding.root, transition)

                binding.accessibilityDetailsExtraConstraintLayout.visibility = if (isVisible) View.GONE else View.VISIBLE
                binding.divider.visibility = if (isVisible) View.GONE else View.VISIBLE

            }
        }


    }


}