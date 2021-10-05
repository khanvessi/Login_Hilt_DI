package com.example.logintask.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.logintask.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logintask.databinding.FragmentUsersBinding
import com.example.logintask.ui.adapters.UserAdapter
import com.example.logintask.ui.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val viewModel: UserViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentUsersBinding.bind(view)

        val userAdapter = UserAdapter()

        binding.apply {
            recViewUsers.apply {
                adapter = userAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.users.observe(viewLifecycleOwner){
            userAdapter.submitList(it)
        }


    }
}