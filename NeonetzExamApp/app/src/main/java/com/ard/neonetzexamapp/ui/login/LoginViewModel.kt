package com.ard.neonetzexamapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ard.neonetzexamapp.Resource
import com.ard.neonetzexamapp.data.model.UserEntity
import com.ard.neonetzexamapp.domain.UserRepository
import com.ard.neonetzexamapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepository) : ViewModel() {

    private var user: UserEntity? = null

    private val _userInfoState = MutableLiveData<Resource>()
    val userInfoState: LiveData<Resource> get() = _userInfoState

    init {
        viewModelScope.launch {
            _userInfoState.value = Resource.Loading()
            try {
                user = userRepo.getUserInfo()
                if(user!=null){
                    _password.value = user?.password
                    _userInfoState.value = Resource.Sucess(user)
                }
                else
                    _userInfoState.value = Resource.Failure()
            } catch (e: Exception) {
                _userInfoState.value = Resource.Failure()
            }
        }
    }

    private val _userDeleteState = MutableLiveData<Resource>()
    val userDelelteState: LiveData<Resource> get() = _userDeleteState

    private val _userCreateState = MutableLiveData<Resource>()
    val userCreateState: LiveData<Resource> get() = _userCreateState

    private val _isPasswordComplete = SingleLiveEvent<Boolean>()
    val isPasswordComplete: LiveData<Boolean> get() = _isPasswordComplete

    private val _isPasswordCorrect = SingleLiveEvent<Boolean>()
    val isPasswordCorrect: LiveData<Boolean> get() = _isPasswordCorrect

    private val _password = SingleLiveEvent<String>()
    val password: LiveData<String> get() = _password


    fun allPasswordCompleted(completed: Boolean) {
        _isPasswordComplete.value = completed
    }

    fun createUserInfo() {
        viewModelScope.launch {
            val userToInsert = UserEntity(
                name = "Fernanda",
                lastname = "Herrera",
                photoUrl = "https://es.inviptus.com/blogadmin/wp-content/uploads/2016/08/MODELO-FRENTE-GRANDE.jpg",
                phoneNumber = "55 3418 2227",
                password = "123456"
            )
            _userCreateState.value = Resource.Loading()
            try {
                val userInsert = userRepo.insertUser(userToInsert)
                if(userInsert.toInt() != 0){
                    user = userToInsert
                    _password.value = user?.password
                    _userCreateState.value = Resource.Sucess(user)
                }
                else
                    _userCreateState.value = Resource.Failure()

            }catch (e: Exception){
                _userCreateState.value = Resource.Failure()
            }

        }
    }

    fun checkPassword(password: String) {
        _isPasswordCorrect.value = user?.password == password
    }

    fun forgetUser() {
        viewModelScope.launch {
            _userDeleteState.value = Resource.Loading()
            try {
                if(user != null){
                    val status = userRepo.deleteUser(user!!)
                    if(status!= 0){
                        user = null
                        _password.value = null
                        _userDeleteState.value = Resource.Sucess(status)
                    }
                    else
                        _userDeleteState.value = Resource.Failure()
                }
            } catch (e: Exception) {
                _userDeleteState.value = Resource.Failure()
            }
        }
    }

}