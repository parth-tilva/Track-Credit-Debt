package com.example.rack.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rack.*
import com.example.rack.adapter.FriendAdapter
import com.example.rack.adapter.IFriend
import com.example.rack.data.database.FriendApplication
import com.example.rack.databinding.FragmentHomeBinding
import com.example.rack.databinding.FragmentHomeBinding.inflate


class Home : Fragment(), IFriend {


    private val viewModel: FriendViewModel by activityViewModels{
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
            addFriend()
        }
        binding.editTextTextPersonName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                addFriend();
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
            }
            false;
        }

        viewModel.allFriend.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

    }

    private fun addFriend() {
        val name = binding.editTextTextPersonName.text.toString()
        if(name.isNotEmpty()){
            viewModel.addNewFriend(name)
            binding.editTextTextPersonName.setText("")
        }
    }

    override fun onClicked(item: Friend) {
        val action = HomeDirections.actionHome2ToDebtFragment2(item.id)
        this.findNavController().navigate(action)
        Toast.makeText(activity,"id: ${item.id}: ${item.fiendName} is Clicked",Toast.LENGTH_SHORT).show()
    }

    override fun onDelete(item: Friend) {
        val dialog =  AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(android.R.string.dialog_alert_title))
        dialog.setMessage(getString(R.string.delete_question))
        dialog.setPositiveButton("OK"){ _, _ ->
            //do nothing
            viewModel.deleteFriend(item)
        }
        dialog.setNegativeButton("No"){ _,_->
            //do nothing
            Toast.makeText(context,"No Clicked", Toast.LENGTH_LONG).show()
        }
        dialog.create()
        dialog.show()
    }
}