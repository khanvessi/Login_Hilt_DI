package com.example.logintask.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.logintask.data.User
import com.example.logintask.data.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao
    //@Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {


    private val userEventChannel = Channel<UserEvent>()
    //BETTET TO NOT EXPOSE TO FRAGMENTS
    val usersEvent = userEventChannel.receiveAsFlow()
    //WHENEVER THE DB UPDATES WE GET THE FLOW
    val users = userDao.getAllUsers().asLiveData()

    fun onValidateUser(userName: String?, password: Int?): Boolean {
        val user = viewModelScope.launch {
            //userDao.validateUser(userName, password)
        }

        if(user!=null){
            viewModelScope.launch {
                userEventChannel.send(UserEvent.ShowUserValidationMessage(1))
            }
            return true;
        }else{
            viewModelScope.launch {
                userEventChannel.send(UserEvent.ShowUserValidationMessage(0))
            }
            return false
        }
    }

    fun onRegisterClick(user: User) {
        viewModelScope.launch {
            val inserted: Long = userDao.inserUser(user)
            if(inserted < 0){
                userEventChannel.send(UserEvent.ShowUserRegistrationMessage(0))
            }else{
                userEventChannel.send(UserEvent.ShowUserRegistrationMessage(1))
            }
        }
    }

    sealed class UserEvent{
        data class ShowUserRegistrationMessage(val flag: Int) :UserEvent()
        data class ShowUserValidationMessage(val flag: Int) :UserEvent()
    }


}
