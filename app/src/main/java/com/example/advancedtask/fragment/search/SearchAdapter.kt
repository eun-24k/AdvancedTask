package com.example.advancedtask.fragment.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.advancedtask.databinding.SearchListGridBinding
import com.example.advancedtask.model.SearchItemModel
import com.example.advancedtask.utils.Constants
import com.example.advancedtask.utils.Utils.addPrefItem
import com.example.advancedtask.utils.Utils.deletePrefItem
import com.example.advancedtask.utils.Utils.getDateFromTimestampWithFormat

class  SearchAdapter(private val mContext: Context) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

    // 검색된 아이템들
    var items = ArrayList<SearchItemModel>()

    // 아이템 전체 삭제
    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchListGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // 데이터 바인딩
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(mContext)
            .load(items[position].url)
            .into(holder.iv_thum_image)

        val type = if (items[position].type == Constants.SEARCH_TYPE_VIDEO) "[Video] " else "[Image] "
        holder.iv_like.visibility = if (items[position].isLike) View.VISIBLE else View.INVISIBLE
        holder.tv_title.text = type + items[position].title
        holder.tv_datetime.text =

            getDateFromTimestampWithFormat(
                items[position].dateTime,
                "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
                "yyyy-MM-dd HH:mm:ss"
            )
    }

    // 아이템 수 반환
    override fun getItemCount() = items.size

    // ViewHolder 정의
    inner class ItemViewHolder(binding: SearchListGridBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var iv_thum_image: ImageView = binding.ivThumbnail
        var iv_like: ImageView = binding.icLike
        var tv_title: TextView = binding.tvSitename
        var tv_datetime: TextView = binding.tvDatetime
        var cl_thumb_item: ConstraintLayout = binding.listView

        init {
            iv_like.visibility = View.GONE
            iv_thum_image.setOnClickListener(this)
            cl_thumb_item.setOnClickListener(this)
        }

        // 클릭 이벤트 처리
        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            if (!item.isLike) {
                addPrefItem(mContext, item)
                item.isLike = true
            } else {
                deletePrefItem(mContext, item.url)
                item.isLike = false
            }

            notifyItemChanged(position)
        }
    }
}
