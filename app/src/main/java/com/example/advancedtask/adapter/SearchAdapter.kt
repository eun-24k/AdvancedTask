package com.example.advancedtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advancedtask.databinding.SearchListGridBinding
import com.google.android.material.transition.Hold

class SearchAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var holdGrid: Hold

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    inner class Hold(binding: SearchListGridBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail = binding.ivThumbnail
        val sitename = binding.tvSitename
        val datetime = binding.tvDatetime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        Hold(SearchListGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = 80

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it,position)
        }
        holdGrid = holder as Hold
        with (holdGrid) {

        }
    }
}