package com.developstudio.attendanceapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.developstudio.attendanceapp.databinding.FragmentSyncScreenBinding
import com.developstudio.attendanceapp.utils.ShowDialog
import com.developstudio.attendanceapp.utils.UserIdManager
import com.developstudio.attendanceapp.viewmodels.UserViewModel

class SyncScreen : Fragment() {

    private lateinit var binding: FragmentSyncScreenBinding
    private val viewmodel: UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSyncScreenBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (UserIdManager.getUserId(requireContext()).isNullOrEmpty()) {
            ShowDialog.showSnackbar(binding.candidate, "Please Login")
            requireActivity().finish()
        } else {
            UserIdManager.getUserId(requireContext())?.let { viewmodel.getUserDetails(it) }
        }

        viewmodel.userDetails.observe(viewLifecycleOwner) {

            if (it != null) {
                binding.centerCode.text = "Center Code ${it.centerCode}"
            }

        }


    }

}