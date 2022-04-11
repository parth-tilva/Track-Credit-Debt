package com.example.rack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rack.Money
import com.example.rack.databinding.MoneyItemBinding

class MoneyListAdapter(private val listner: IMoney) :ListAdapter<Money, MoneyListAdapter.MoneyViewHolder>(
    DiffCallback
) {
    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<Money>(){
            override fun areItemsTheSame(oldItem: Money, newItem: Money): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Money, newItem: Money): Boolean {
                return oldItem.dateAndTime == newItem.dateAndTime
            }
        }

    }
    class MoneyViewHolder(private val binding: MoneyItemBinding):RecyclerView.ViewHolder(binding.root) {
        val deleteBtn = binding.deleteButton
        fun bind(money: Money){
            binding.tvFriendName.text = money.amount.toString()
            binding.txtNote.text = "Note: " + money.note
            binding.txtTimeAndDate.text = money.dateAndTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        return MoneyViewHolder(
            MoneyItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        val moneyItem = getItem(position)
        holder.bind(moneyItem)
        holder.deleteBtn.setOnClickListener {
            listner.onDelete(moneyItem)
        }
        holder.itemView.setOnClickListener {
            listner.onItemClicked(moneyItem)
        }
    }
}

interface IMoney{
    fun onDelete(money: Money)
    fun onItemClicked(money: Money)
}