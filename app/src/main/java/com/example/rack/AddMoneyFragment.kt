package com.example.rack

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rack.databinding.FragmentAddMoneyBinding
import com.example.rack.databinding.FragmentDebtBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AddMoneyFragment : Fragment() {


    private var _binding: FragmentAddMoneyBinding? = null
    private val binding get( ) = _binding!!
    private val navigationArgs: AddMoneyFragmentArgs by navArgs()
    lateinit var friend: Friend

    var note = ""
    var amount = 0
    var amountStr=""
    var currentTime = ""
    var currentDate = ""
    var isCredit = true
    var dataAndTime =""

    var fId: Int =0
    private val viewModel:FriendViewModel by activityViewModels{
        FViewModelFactory(
            (activity?.applicationContext as FriendApplication).dataBase.friendDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fId = navigationArgs.id

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMoneyBinding.inflate(inflater,container,false)                //return inflater.inflate(R.layout.fragment_add_money, container, false)
        viewModel.getLiveFriend(fId).observe(this.viewLifecycleOwner){
            friend = it
        }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggle = binding.toggleButtonDC
        toggle.setOnCheckedChangeListener { _, isChecked ->
            isCredit = isChecked
        }
        getInfo()
        binding.btnSave.setOnClickListener {
            getInfo()
            Toast.makeText(activity,"Price: $amountStr    Date: $currentDate   Time: $currentTime  \n Note: $note \n testing  ", Toast.LENGTH_SHORT).show()
            if(amount!=0){
                viewModel.addMoney(friend,Money(amount, note, dataAndTime))
            }
            val action =  AddMoneyFragmentDirections.actionAddMoneyFragmentToDebtFragment(fId)
            findNavController().navigate(action)
        }
    }




    private fun getInfo(){
         note = binding.itemNote.text.toString()
        val moneyStr = binding.itemPrice.text.toString()
        if(moneyStr.isNotEmpty()){
            amount = moneyStr.toInt()
        }

        if (isCredit) {
             //The toggle is enabled (credit)
                amountStr =  amount.toString()
                println("str: $amountStr")
        } else {
             //The toggle is disabled (debit)
                amount = -amount
                amountStr = amount.toString()
                println("str: $amountStr")
        }
         currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
         currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        dataAndTime = "Date: $currentDate\nTime: $currentTime"
        binding.txtTime.text = dataAndTime
    }

}