package com.example.rack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
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


        //testing

//        lifecycle.coroutineScope.launch {
//            viewModel.GetFriendById(fId).collect {
//                binding.nameFriend.text = "Name is by id  : ${it.fiendName}"
//                println("testing 23: ${it.fiendName}")
//
//            }
//        }


        //testing



        viewModel.allFriend.observe(this.viewLifecycleOwner){
            it.let {
                it.forEach { F->
                    if(F.id == fId){
                        friend = F
                        adapter.submitList(F.list)
                        binding.totalMoney.text = "Total: ${friend.total} Rupees"
                        binding.nameFriend.text = friend.fiendName
                    }
                }
            }
        }

        binding.addMoneyBtn.setOnClickListener {
            //Toast.makeText(activity,"test ${friend}",Toast.LENGTH_SHORT).show()
            val str = binding.enterMoney.text.toString()
            if(str.isNotEmpty()){
                friend= viewModel.addDebt(fId,friend,str)
                binding.totalMoney.text = "Total: ${friend.total} Rupees"
                adapter.submitList(friend.list)
                adapter.notifyDataSetChanged()
                binding.enterMoney.setText("")
            }
        }
    }

    override fun onDelete(str: String) {
       friend =  viewModel.removeDebt(str,friend)
        binding.totalMoney.text = "Total: ${friend.total} Rupees"
        adapter.submitList(friend.list)
        adapter.notifyDataSetChanged()
    }


}