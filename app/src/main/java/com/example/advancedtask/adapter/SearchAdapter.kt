package com.example.advancedtask.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.advancedtask.data.SearchModel
import com.example.advancedtask.databinding.SearchListGridBinding

class SearchAdapter() : ListAdapter<SearchModel, SearchAdapter.Holder>(SearchModelDiffCallback)
{
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, item: SearchModel)
    }

    private var listener: OnItemClickListener? = null


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SearchListGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.setView(item)

        holder.itemView.setOnClickListener {
            Log.d("itemIsClicked","야호: $item")
            listener?.onItemClick(it,position, item)
        }
    }

    class Holder(private val binding: SearchListGridBinding, private val onItemClickListener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
        fun setView(item: SearchModel) = with(binding) {
            tvSitename.text = item.title
            tvDatetime.text = item.date
            icLike.isVisible = item.bookMark
            Log.d("item","$item")
            Glide.with(ivThumbnail.context).load(item.thumbnail).into(ivThumbnail)


        }
    }

    companion object {
        val SearchModelDiffCallback = object : DiffUtil.ItemCallback<SearchModel>() {
            override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean =
                oldItem.thumbnail == newItem.thumbnail

            override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean =
                oldItem == newItem
        }
    }







}