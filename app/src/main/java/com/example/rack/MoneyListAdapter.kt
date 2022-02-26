package com.example.rack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rack.databinding.ItemFriendBinding

class MoneyListAdapter(private val listner:IMoney) :ListAdapter<Money,MoneyListAdapter.MoneyViewHolder>(DiffCallback) {
    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<Money>(){
            override fun areItemsTheSame(oldItem: Money, newItem: Money): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Money, newItem: Money): Boolean {
                return oldItem.time == newItem.time
            }
        }

    }
    class MoneyViewHolder(private val binding: ItemFriendBinding):RecyclerView.ViewHolder(binding.root) {
        val deleteBtn = binding.deleteButton
        fun bind(money: Money){
            binding.tvFriendName.text = money.amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        return MoneyViewHolder(
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        val moneyItem = getItem(position)
        holder.bind(moneyItem)
        holder.deleteBtn.setOnClickListener {
            listner.onDelete(moneyItem)
        }
    }
}

interface IMoney{
    fun onDelete(money: Money)
}