package com.developstudio.attendanceapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.developstudio.attendanceapp.adapters.SingleCandidateAdapter
import com.developstudio.attendanceapp.databinding.FragmentFetchCandidateBinding
import com.developstudio.attendanceapp.viewmodels.UserViewModel

class FetchCandidate : Fragment() {
    private val viewmodel: UserViewModel by viewModels()
    private lateinit var binding: FragmentFetchCandidateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentFetchCandidateBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       
       
    }

}