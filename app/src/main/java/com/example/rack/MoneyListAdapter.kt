package com.example.rack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rack.databinding.ItemFriendBinding

class MoneyListAdapter(private val listner:IMoney) :ListAdapter<String,MoneyListAdapter.MoneyViewHolder>(DiffCallback) {
    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }

    }
    class MoneyViewHolder(private val binding: ItemFriendBinding):RecyclerView.ViewHolder(binding.root) {
        val deleteBtn = binding.deleteButton
        fun bind(string: String){
            binding.tvFriendName.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        return MoneyViewHolder(
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        val str = getItem(position)
        holder.bind(str)
        holder.deleteBtn.setOnClickListener {
            listner.onDelete(str)
        }
    }


}

interface IMoney{
    fun onDelete(str: String)
}