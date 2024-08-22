package com.developstudio.attendanceapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.developstudio.attendanceapp.adapters.CandidateAdapter
import com.developstudio.attendanceapp.data.CandidateDetails
import com.developstudio.attendanceapp.databinding.FragmentCandidateDetailsScreenBinding
import com.developstudio.attendanceapp.utils.ShowDialog
import com.developstudio.attendanceapp.utils.UserIdManager
import com.developstudio.attendanceapp.viewmodels.UserViewModel

class CandidateDetailsScreen : Fragment() {

    private lateinit var binding: FragmentCandidateDetailsScreenBinding
    private val viewmodel: UserViewModel by viewModels()
    private var candidateList: List<CandidateDetails> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCandidateDetailsScreenBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleViewCandidate.layoutManager = LinearLayoutManager(requireContext())

        if (UserIdManager.getUserId(requireContext()).isNullOrEmpty()) {
            ShowDialog.showSnackbar(binding.l2, "Please Login")
            requireActivity().finish()
        } else {
            UserIdManager.getUserId(requireContext())?.let { viewmodel.getUserDetails(it) }
        }


        viewmodel.fetchAllCandidates()

        viewmodel.userDetails.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.centerCodeTextView.text = "Center Code : ${it.centerCode}"
                binding.textExamNameData.text = it.examName
                binding.shiftData.text = it.shiftTime
            }

        }


        binding.showPresent.setOnClickListener {

            if (candidateList.isNotEmpty()) {
                setupAdapter(presentCandidates(true))
            }
        }
        binding.show2.setOnClickListener {

            if (candidateList.isNotEmpty()) {
                setupAdapter(presentCandidates(false))
            }

        }

        viewmodel.candidatesLiveDataList.observe(viewLifecycleOwner) { itm ->
            candidateList = itm
            val presentStudents = itm.filter { it.isPresent }
            val absentStudents = itm.filter { !it.isPresent }
            binding.presentCandidatetextView.text = presentStudents.size.toString()
            binding.absentCandidate.text = absentStudents.size.toString()

        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setupAdapter(list: List<CandidateDetails>) {
        val adapter = CandidateAdapter(list)
        binding.recycleViewCandidate.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    // Method to filter candidates based on their presence
    private fun presentCandidates(isPresent: Boolean): List<CandidateDetails> {
        return candidateList.filter { it.isPresent == isPresent }
    }

/*
    // Method to filter candidates who are not present
    fun filterCandidatesNotPresent(): List<CandidateDetails> {
        return candidateList.filter { !it.isPresent }
    }

 */

}