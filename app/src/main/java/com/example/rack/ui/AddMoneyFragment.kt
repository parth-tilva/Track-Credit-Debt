package com.example.rack.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rack.*
import com.example.rack.data.database.FriendApplication
import com.example.rack.databinding.FragmentAddMoneyBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue


class AddMoneyFragment : Fragment() {

    private val viewModel: FriendViewModel by activityViewModels {
        FViewModelFactory(
            (activity?.applicationContext as FriendApplication).dataBase.friendDao()
        )
    }
    private var _binding: FragmentAddMoneyBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: AddMoneyFragmentArgs by navArgs()
    lateinit var friend: Friend
    lateinit var toggleButton: ToggleButton

    var note = ""
    var amount = 0
    var amountStr = ""
    var currentTime = ""
    var currentDate = ""
    var isCredit = true
    var dataAndTime = ""

    var fId: Int = 0
    var timeAndDateArgs: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fId = navigationArgs.friendId
        Log.d("addmoney ", "addMoney freind id : $fId")
        timeAndDateArgs = navigationArgs.timeAndDate
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMoneyBinding.inflate(
            inflater,
            container,
            false
        )                //return inflater.inflate(R.layout.fragment_add_money, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        toggleButton = binding.toggleButtonDC
        toggleButton.isChecked = true
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.itemPriceLabel.setBackgroundColor(Color.WHITE)
                isCredit=true
            }else{
                binding.itemPriceLabel.setBackgroundColor(Color.argb(48,244,96,96))
                isCredit = false
            }
        }
        viewModel.getLiveFriend(fId).observe(this.viewLifecycleOwner) {
            friend = it
            Log.d("addmoney ", "addMoney friend: $friend")
            setup()
        }

    }

    fun setup() {
        if (timeAndDateArgs == null) {
            currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            dataAndTime = "Date: $currentDate\nTime: $currentTime"
            binding.txtTime.text = dataAndTime
            binding.btnSave.setOnClickListener {
                note = binding.itemNote.text.toString()
                var strMoney = binding.itemPrice.text.toString()
                if(strMoney!="")
                amount = strMoney.toInt()
                if (isCredit) {

                } else {
                    amount = -amount
                }
                viewModel.addMoney(friend, Money(amount, note, dataAndTime))
                val action = AddMoneyFragmentDirections.actionAddMoneyFragmentToDebtFragment2(
                        fId
                    )
                findNavController().navigate(action)
            }
        } else{
            val money = friend.listOfMoney.find{it.dateAndTime == timeAndDateArgs}
            if(money!=null){
                amount = money.amount
                if(amount<0){//debit ischeck = false
                    toggleButton.isChecked = false
                    binding.itemPriceLabel.setBackgroundColor(Color.argb(48,244,96,96))
                    amount = amount.absoluteValue // amount = -amount
                }else{
                    toggleButton.isChecked = true
                    binding.itemPriceLabel.setBackgroundColor(Color.WHITE)
                }
                binding.itemNote.setText(money.note)
                binding.itemPrice.setText(amount.toString())
                binding.txtTime.text = timeAndDateArgs

                dataAndTime = timeAndDateArgs as String
                binding.btnSave.setOnClickListener {
                    note = binding.itemNote.text.toString()
                    var strMoney = binding.itemPrice.text.toString()
                    if(strMoney!="")
                        amount = strMoney.toInt()
                    if (isCredit) {

                    } else {
                        amount = -amount
                    }
                    viewModel.removeDebt(money,friend)
                    viewModel.addMoney(friend, Money(amount, note, dataAndTime))
                    val action = AddMoneyFragmentDirections.actionAddMoneyFragmentToDebtFragment2(
                            fId
                        )
                    findNavController().navigate(action)
                }
            }else{
                Log.d("Addmoney","can't find record of money erorr")
            }
        }
    }


}

