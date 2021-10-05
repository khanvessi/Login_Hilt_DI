package com.example.logintask.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.logintask.R
import com.example.logintask.data.User
import com.example.logintask.databinding.FragmentSignupBinding
import com.example.logintask.ui.viewmodels.UserViewModel
import com.example.logintask.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSignupBinding.bind(view)

        binding.apply {
            register.setOnClickListener(View.OnClickListener {
                val user = User(name = name.text.toString(), email = email.text.toString(), number = phone.text.toString().toInt(), image = "")
                viewModel.onRegisterClick(user)
            })
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.usersEvent.collect {event ->
                when(event){
                    is UserViewModel.UserEvent.ShowUserRegistrationMessage ->{
                        if(event.flag == 0)
                        Snackbar.make(requireView(), "Something Went Wrong!", Snackbar.LENGTH_LONG)
                            .setAction("Cancel") {
                                //viewModel.onUndoDeleteClick(event.task)
                            }.show()
                        else
                            Snackbar.make(requireView(), "User Added Successfully!", Snackbar.LENGTH_LONG)
                                .setAction("Cancel") {
                                    //viewModel.onUndoDeleteClick(event.task)
                                }.show()
                    }
                    is UserViewModel.UserEvent.ShowUserValidationMessage -> TODO()
                    //THIS IS TO AVOID ELSE CASE
                }.exhaustive
            }
        }

    }
}