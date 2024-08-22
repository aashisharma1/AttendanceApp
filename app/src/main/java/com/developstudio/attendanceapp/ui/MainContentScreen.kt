package com.developstudio.attendanceapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.developstudio.attendanceapp.R
import com.developstudio.attendanceapp.databinding.FragmentMainContentScreenBinding

class MainContentScreen : Fragment() {
    private lateinit var binding: FragmentMainContentScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainContentScreenBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener { findNavController().navigate(R.id.action_mainContentScreen_to_fetchCandidate) }
    }

}