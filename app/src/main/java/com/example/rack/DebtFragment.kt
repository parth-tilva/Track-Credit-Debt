package com.example.rack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rack.databinding.FragmentDebtBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DebtFragment : Fragment(), IMoney {

    private val navigationArgs: DebtFragmentArgs by navArgs()
    lateinit var friend :Friend
    private var _binding:FragmentDebtBinding? = null
    private val binding get( ) = _binding!!
    private lateinit var adapter: MoneyListAdapter
    private val viewModel:FriendViewModel by activityViewModels{
        FViewModelFactory(
            (activity?.applicationContext as FriendApplication).dataBase.friendDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDebtBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fId = navigationArgs.id
        adapter = MoneyListAdapter(this)
        binding.moneyList.adapter = adapter

        viewModel.getLiveFriend(fId).observe(this.viewLifecycleOwner){
            friend = it
            adapter.submitList(it.listOfMoney)
            binding.totalMoney.text = "Total: ${friend.total} Rupees"
            binding.nameFriend.text = friend.fiendName
            println("live friend: $friend")

        }

        binding.fabAddMoney.setOnClickListener {
            val action = DebtFragmentDirections.actionDebtFragmentToAddMoneyFragment(fId)
            this.findNavController().navigate(action)
        }

    }

    override fun onDelete(money: Money) {
           viewModel.removeDebt(money,friend)
    }

}
