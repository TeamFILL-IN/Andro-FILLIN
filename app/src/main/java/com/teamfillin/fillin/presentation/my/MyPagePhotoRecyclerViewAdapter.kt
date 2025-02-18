package com.teamfillin.fillin.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.teamfillin.fillin.data.response.ResponseUserPhotoInfo
import com.teamfillin.fillin.databinding.ItemMyPageBinding
import com.teamfillin.fillin.domain.entity.UserPhoto

class MyPagePhotoRecyclerViewAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<MyPagePhotoRecyclerViewAdapter.ViewHolder>() {
    private var photoList = listOf<UserPhoto>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemMyPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(photoList[position])
    }

    override fun getItemCount() = photoList.size

    fun replaceList(newList: List<UserPhoto>) {
        photoList = newList.toList()
        notifyDataSetChanged()
    }

    fun interface ItemClickListener {
        fun onClick(data: UserPhoto)
    }

    class ViewHolder(
        private val binding: ItemMyPageBinding,
        private val listener: ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: UserPhoto) {
            Glide.with(itemView.context).load(photo.imageUrl).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(binding.ivPhoto)
            binding.root.setOnClickListener {
                listener.onClick(photo)
            }
        }
    }
}