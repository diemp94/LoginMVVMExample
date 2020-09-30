package com.ard.neonetzexamapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ard.neonetzexamapp.data.model.Transaction

class ProfileViewModel :ViewModel(){

    private val _transaction = MutableLiveData<Transaction>()
    val transaction : LiveData<Transaction> get() = _transaction

    private val _missingAmount= MutableLiveData<Boolean>()
    val missingAmount : LiveData<Boolean> get() = _missingAmount

    private val _actualAmount = MutableLiveData<String>()
    val actualAmount : LiveData<String> get() = _actualAmount



    fun sendAmountClick(message:String?){
        if(actualAmount.value.isNullOrEmpty() || actualAmount.value?.toDouble()== 0.0){
            _missingAmount.value = true
        }
        else{
            _transaction.value = Transaction(actualAmount.value!!.toDouble(),
                actualAmount.value!!.toDouble()*0.05,message)
            _missingAmount.value = false
        }
    }

    fun setActualAmount(amount: String) {
        _actualAmount.value = amount
    }
}