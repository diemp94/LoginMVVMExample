package com.ard.neonetzexamapp.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.ard.neonetzexamapp.R
import com.ard.neonetzexamapp.data.model.UserEntity
import com.ard.neonetzexamapp.databinding.FragmentProfileBinding
import com.ard.neonetzexamapp.utils.afterTextChanged
import com.ard.neonetzexamapp.utils.toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private val viewModel : ProfileViewModel by lazy { ViewModelProvider(this).get() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        val user = arguments?.get("user") as UserEntity

        Picasso.get()
            .load(user.photoUrl)
            .resize(75,75)
            .centerCrop()
            .into(binding.ivUserPhoto)

        binding.tvUserName.text ="${user.name} ${user.lastname}"
        binding.tvUserPhone.text = user.phoneNumber

        viewModel.transaction.observe(viewLifecycleOwner,  {
            toast("ENVIO EXITOSO DE: ${it.amount} " +
                    "\n COMISIÓN DE: ${it.serviceCost} " +
                    "\nTOTAL DE LA OPERACIÓN:${it.serviceCost + it.amount} " +
                    "\nMENSAJE: ${it.message}")
        })

        viewModel.missingAmount.observe(viewLifecycleOwner,  {missingValue ->
            if(missingValue) binding.tilAmountToSend.error = "El valor debe ser mayor a 0."
            else binding.tilAmountToSend.error = null
        })

        viewModel.actualAmount.observe(viewLifecycleOwner,{amount ->
            if(amount.isEmpty()){
                binding.tilAmountToSend.error = null
                binding.etCommission.setText("")
                binding.etTotal.setText("")
            }
            else{
                val commission = amount.toDouble()*0.05
                val total : Double = commission + amount.toDouble()
                binding.etCommission.setText(commission.toString())
                binding.etTotal.setText(total.toString())
            }
        })

        binding.etAmountToSend.afterTextChanged {
            viewModel.setActualAmount(it)
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.sendAmountClick(etMessage.text.toString())
        }
    }
}