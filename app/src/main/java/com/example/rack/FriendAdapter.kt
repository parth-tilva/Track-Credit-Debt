package com.example.rack

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rack.databinding.ItemFriendBinding

class FriendAdapter(private val listner: IFriend) :ListAdapter<Friend,FriendAdapter.FriendViewHolder>(DiffCallback){


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
        val holder = FriendViewHolder(
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

        return holder
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listner.onClicked(item)
        }
        holder.deleteBtn.setOnClickListener {
            listner.onDelete(item)
        }
    }
}

interface IFriend{
    fun onClicked(item:Friend)
    fun onDelete(item:Friend)
}

