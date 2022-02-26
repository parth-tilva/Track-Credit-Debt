package com.example.rack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rack.databinding.FragmentHomeBinding
import com.example.rack.databinding.FragmentHomeBinding.inflate


class Home : Fragment(), IFriend {


    private val viewModel:FriendViewModel by activityViewModels{
        FViewModelFactory(
            (activity?.applicationContext as FriendApplication).dataBase.friendDao()
        )
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get( ) = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FriendAdapter(this)
        binding.rvListOfFriend.layoutManager = LinearLayoutManager(this.context)
        binding.rvListOfFriend.adapter = adapter

        binding.AddFriend.setOnClickListener{
            val name = binding.editTextTextPersonName.text.toString()
//            val time = System.currentTimeMillis()
//            val money = Money(time = time)
            if(name.isNotEmpty()){
                viewModel.addNewFriend(name)
                binding.editTextTextPersonName.setText("")
            }
        }
        viewModel.allFriend.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }


    }

    override fun onClicked(item: Friend) {
        val action = HomeDirections.actionHome2ToDebtFragment2(item.id)
        this.findNavController().navigate(action)
        Toast.makeText(activity,"id: ${item.id}: ${item.fiendName} is Clicked",Toast.LENGTH_SHORT).show()
    }

    override fun onDelete(item: Friend) {
        //viewModel.deleteFriend(item)
    }
}