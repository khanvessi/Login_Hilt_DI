package com.example.logintask.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.logintask.R
import com.example.logintask.databinding.FragmentDashboardBinding
import com.example.logintask.ui.viewmodels.UserViewModel
import com.example.logintask.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard), View.OnClickListener {

    private var navController: NavController? = null
    private val viewModel: UserViewModel by viewModels()
    private var userName: String? = null
    private var password: Int? = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDashboardBinding.bind(view)


        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.btn_signin).setOnClickListener(this)
        view.findViewById<TextView>(R.id.txt_register).setOnClickListener(this)

        binding.apply {
            userName = edtUsername.text.toString()
            //password = edtPassword.text.toString().toInt()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.usersEvent.collect {event ->
                when(event){
                    is UserViewModel.UserEvent.ShowUserRegistrationMessage -> TODO()
                    is UserViewModel.UserEvent.ShowUserValidationMessage -> {
                        if(event.flag == 0)
                            Snackbar.make(requireView(), "User not found!", Snackbar.LENGTH_LONG)
                                .setAction("Cancel") {
                                    //viewModel.onUndoDeleteClick(event.task)
                                }.show()
                        else
                            Snackbar.make(requireView(), "Temporarily Signing in", Snackbar.LENGTH_LONG)
                                .setAction("Ok") {
                                    //viewModel.onUndoDeleteClick(event.task)
                                }.show()
                    }
                    //THIS IS TO AVOID ELSE CASE
                }.exhaustive
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.txt_register -> navController!!.navigate(R.id.action_dashboardFragment_to_signupFragment)
            R.id.btn_signin -> {
                var exists: Boolean = viewModel.onValidateUser(userName, password)
                if(exists){
                    navController!!.navigate(R.id.action_dashboardFragment_to_usersFragment)
                }else{
                    //TODO: SHOW SNACKBAR USING CHANNEL
                }
            }
        }
    }



}