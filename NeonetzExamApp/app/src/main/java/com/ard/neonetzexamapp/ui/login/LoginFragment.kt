package com.ard.neonetzexamapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ard.neonetzexamapp.AppDataBase
import com.ard.neonetzexamapp.R
import com.ard.neonetzexamapp.Resource
import com.ard.neonetzexamapp.data.UserDataSourceImpl
import com.ard.neonetzexamapp.data.model.UserEntity
import com.ard.neonetzexamapp.databinding.FragmentLoginBinding
import com.ard.neonetzexamapp.domain.UserRepositoryImpl
import com.ard.neonetzexamapp.ui.ViewModelFactory
import com.ard.neonetzexamapp.ui.strategy.EditTextFocusChangeStrategy
import com.ard.neonetzexamapp.utils.afterTextChanged
import com.ard.neonetzexamapp.utils.toast
import com.squareup.picasso.Picasso

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding

    private val db: AppDataBase by lazy { AppDataBase.getDatabase(requireContext()) }
    val editTextStrategy by lazy { EditTextFocusChangeStrategy() }

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory(
            UserRepositoryImpl(
                UserDataSourceImpl(db.userDao())
            )
        )
    }
    private lateinit var user : UserEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        viewModel.userInfoState.observe(viewLifecycleOwner, { result ->
            when(result){
                is Resource.Loading ->{
                    binding.pbLogin.visibility = View.VISIBLE
                }
                is Resource.Sucess<*> ->{
                    binding.pbLogin.visibility = View.GONE
                    binding.btnCreateUserInfo.visibility = View.GONE
                    user = result.data as UserEntity
                    setUserInfo(user)
                }
                is Resource.Failure->{
                    binding.pbLogin.visibility = View.GONE
                    binding.btnCreateUserInfo.visibility = View.VISIBLE
                }
            }
        })

        viewModel.userDelelteState.observe(viewLifecycleOwner,{ result ->
            when(result){
                is Resource.Loading ->{
                    binding.pbLogin.visibility = View.VISIBLE
                }
                is Resource.Sucess<*> ->{
                    binding.pbLogin.visibility = View.GONE
                    binding.btnCreateUserInfo.visibility = View.VISIBLE
                    removeUserInfo()
                }
                is Resource.Failure->{
                    binding.pbLogin.visibility = View.GONE
                    toast("Ocurrió un error al borrar al Usuario")
                }
            }
        })

        viewModel.userCreateState.observe(viewLifecycleOwner,{ result ->
            when(result){
                is Resource.Loading ->{
                    binding.pbLogin.visibility = View.VISIBLE
                }
                is Resource.Sucess<*> ->{
                    binding.pbLogin.visibility = View.GONE
                    binding.btnCreateUserInfo.visibility = View.GONE
                    user = result.data as UserEntity
                    setUserInfo(user)
                }
                is Resource.Failure->{
                    binding.pbLogin.visibility = View.GONE
                    toast("Ocurrió un error al borrar al Usuario")
                }
            }
        })

        viewModel.isPasswordComplete.observe(viewLifecycleOwner, {isPasswordComplete ->
            binding.btnLogin.isEnabled = isPasswordComplete
        })

        viewModel.isPasswordCorrect.observe(viewLifecycleOwner,{ isCorrect ->
            if(isCorrect){
                val bundle = bundleOf("user" to user)
                findNavController().navigate(R.id.action_loginFragment_to_profileFragment,bundle)
            }
            else
                toast("Contraseña Incorrecta")
        })

        binding.etPass6.afterTextChanged {
            viewModel.allPasswordCompleted(it.isNotEmpty())
        }

        binding.btnLogin.setOnClickListener {
            viewModel.checkPassword(
                editTextStrategy.getEditTextsString(arrayListOf(binding.etPass1,binding.etPass2,binding.etPass3,binding.etPass4,binding.etPass5,binding.etPass6))
            )
        }

        binding.btnCreateUserInfo.setOnClickListener {
            viewModel.createUserInfo()
        }

        binding.btnForgetUser.setOnClickListener {
            viewModel.forgetUser()
        }

        binding.btnForgotPassword.setOnClickListener {
            toast("La contraseña es:${viewModel.password.value}")
        }
    }

    override fun onResume() {
        super.onResume()
        editTextsConfiguration()
    }

    override fun onStop() {
        super.onStop()
        editTextStrategy.cleanEditText(arrayListOf(binding.etPass1,binding.etPass2,binding.etPass3,binding.etPass4,binding.etPass5,binding.etPass6))
    }

    private fun setUserInfo(user:UserEntity){
        Picasso.get()
            .load(user.photoUrl)
            .placeholder(R.drawable.thumb_profile)
            .resize(75,75)
            .centerCrop()
            .into(binding.ivUserPhoto)
        binding.tvUserName.text = "${user.name} ${user.lastname}"
    }

    private fun removeUserInfo() {
        Picasso.get()
            .load(R.drawable.thumb_profile)
            .resize(75,75)
            .centerCrop()
            .into(binding.ivUserPhoto)
        binding.tvUserName.text = ""
    }

    private fun editTextsConfiguration(){
        editTextStrategy.requestFocus(arrayListOf(binding.etPass1,binding.etPass2,binding.etPass3,binding.etPass4,binding.etPass5,binding.etPass6))
    }

}