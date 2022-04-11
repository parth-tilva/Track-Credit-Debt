package com.example.rack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rack.Friend
import com.example.rack.databinding.ItemFriendBinding

class FriendAdapter(private val listener: IFriend) :ListAdapter<Friend, FriendAdapter.FriendViewHolder>(
    DiffCallback
){


    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<Friend>(){
            override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return oldItem.fiendName == newItem.fiendName
            }
        }
    }


    class FriendViewHolder(private val binding: ItemFriendBinding): RecyclerView.ViewHolder(binding.root){
        val deleteBtn = binding.deleteButton
        fun bind(friend: Friend){
            binding.tvFriendName.text = friend.fiendName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onClicked(item)
        }
        holder.deleteBtn.setOnClickListener {
            listener.onDelete(item)
        }
    }
}

interface IFriend{
    fun onClicked(item: Friend)
    fun onDelete(item: Friend)
}

