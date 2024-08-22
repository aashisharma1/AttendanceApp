package com.developstudio.attendanceapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.developstudio.attendanceapp.databinding.FragmentCandidateDetailsScreenBinding
import com.developstudio.attendanceapp.utils.ShowDialog
import com.developstudio.attendanceapp.utils.UserIdManager
import com.developstudio.attendanceapp.viewmodels.UserViewModel

class CandidateDetailsScreen : Fragment() {

    private lateinit var binding: FragmentCandidateDetailsScreenBinding
    private val viewmodel: UserViewModel by viewModels()

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


        if (UserIdManager.getUserId(requireContext()).isNullOrEmpty()) {
            ShowDialog.showSnackbar(binding.l2, "Please Login")
            requireActivity().finish()
        } else {
            UserIdManager.getUserId(requireContext())?.let { viewmodel.getUserDetails(it) }
        }
        viewmodel.fetchAllCandidates()

        viewmodel.userDetails.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.centerCodeTextView.text = it.centerCode
                binding.textExamNameData.text = it.examName
                binding.shiftData.text = it.shiftTime
            }

        }


        binding.show1.setOnClickListener {
            Toast.makeText(requireContext(), "Not Implemented Yet.", Toast.LENGTH_SHORT).show()
        }
        binding.show2.setOnClickListener {
            Toast.makeText(requireContext(), "Not Implemented Yet.", Toast.LENGTH_SHORT).show()
        }

        viewmodel.candidatesLiveDataList.observe(viewLifecycleOwner){ itm ->

            val presentStudents = itm.filter { it.isPresent }
            val absentStudents = itm.filter { !it.isPresent }
            binding.presentCandidatetextView.text = presentStudents.size.toString()
            binding.absentCandidate.text = absentStudents.size.toString()

        }


    }


}