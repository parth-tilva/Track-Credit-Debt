package com.example.rack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
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

        lifecycle.coroutineScope.launch {
            viewModel.GetFriendById(fId).collect {
                friend = it
                adapter.submitList(it.listOfMoney)
                        binding.totalMoney.text = "Total: ${friend.total} Rupees"
                        binding.nameFriend.text = friend.fiendName
            }
        }

        val friendlive = viewModel.getLiveFriend(fId).value
        println("live friend: $friendlive")

        //testing



//        viewModel.allFriend.observe(this.viewLifecycleOwner){
//            it.let {
//                it.forEach { F->
//                    if(F.id == fId){
//                        friend = F
//                        adapter.submitList(F.listOfMoney)
//                        binding.totalMoney.text = "Total: ${friend.total} Rupees"
//                        binding.nameFriend.text = friend.fiendName
//                    }
//                }
//            }
//        }

        binding.addMoneyBtn.setOnClickListener {
            //Toast.makeText(activity,"test ${friend}",Toast.LENGTH_SHORT).show()
            val str = binding.enterMoney.text.toString()
            val t = System.currentTimeMillis()
            val money = Money(time = t)
            if(str.isNotEmpty()){
                friend= viewModel.addDebt(fId,friend,str,money)
                println("list of Money ${friend.listOfMoney}")
                binding.totalMoney.text = "Total: ${friend.total} Rupees"
                adapter.submitList(friend.listOfMoney)
                adapter.notifyDataSetChanged()
                binding.enterMoney.setText("")
            }
        }
    }

    override fun onDelete(money: Money) {

//       friend =  viewModel.removeDebt(str,friend)
//        binding.totalMoney.text = "Total: ${friend.total} Rupees"
//        adapter.submitList(friend.listOfMoney)
//        adapter.notifyDataSetChanged()
    }


}
