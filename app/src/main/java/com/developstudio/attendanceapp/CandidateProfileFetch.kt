package com.developstudio.attendanceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.developstudio.attendanceapp.adapters.SingleCandidateAdapter
import com.developstudio.attendanceapp.databinding.FragmentCandidateProfileFetchBinding
import com.developstudio.attendanceapp.viewmodels.UserViewModel


class CandidateProfileFetch : Fragment() {
private lateinit var binding :FragmentCandidateProfileFetchBinding
    private val viewmodel: UserViewModel by viewModels()
    private lateinit var singleCandidateAdapter: SingleCandidateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentCandidateProfileFetchBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())

        binding.nextButton.setOnClickListener {

            if (binding.rollNumberEditTExt.text.toString().isNotEmpty()) {
                viewmodel.fetchCandidateByRollNumber(binding.rollNumberEditTExt.text.toString())
            }


        }


        viewmodel.candidateLiveData.observe(viewLifecycleOwner){
            if (it!=null){

                singleCandidateAdapter = SingleCandidateAdapter(it)
                binding.recycleView.adapter = singleCandidateAdapter

            }else{
                Toast.makeText(requireContext(), "No data Found", Toast.LENGTH_SHORT).show()
            }
        }


    }

}