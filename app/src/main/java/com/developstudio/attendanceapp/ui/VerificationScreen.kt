package com.developstudio.attendanceapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.developstudio.attendanceapp.R
import com.developstudio.attendanceapp.data.AttenderProfile
import com.developstudio.attendanceapp.databinding.FragmentVerificationScreenBinding
import com.developstudio.attendanceapp.utils.ExamProvider
import com.developstudio.attendanceapp.utils.localData
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VerificationScreen : Fragment() {

    private var _binding: FragmentVerificationScreenBinding? = null
    private val binding get() = _binding!!

    private var shiftCode: String = ""
    private var examName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAutoCompleteTextViews()
        setupSubmitButton()
    }

    private fun setupAutoCompleteTextViews() {
        // Set up listeners for the AutoCompleteTextViews to capture selected items
        binding.autoCompleteTextViewExam.setOnItemClickListener { parent, _, position, _ ->
            examName = parent.getItemAtPosition(position).toString()
        }

        binding.autoCompleteTextViewShift.setOnItemClickListener { parent, _, position, _ ->
            shiftCode = parent.getItemAtPosition(position).toString()
        }
    }

    private fun setupSubmitButton() {
        // Set up the submit button click listener
        binding.submitButton.setOnClickListener {
            checkDataValidation()
        }
    }

    private fun checkDataValidation() {
        // Validate user inputs before proceeding
        when {
            examName.isEmpty() -> {
                Toast.makeText(requireContext(), "Select Exam", Toast.LENGTH_SHORT).show()
            }
            shiftCode.isEmpty() -> {
                Toast.makeText(requireContext(), "Select Shift", Toast.LENGTH_SHORT).show()
            }
            binding.centerCode.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter Exam code", Toast.LENGTH_SHORT).show()
            }
            binding.password.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter Password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                openDialog(shiftCode, binding.centerCode.text.toString())
            }
        }
    }

    private fun openDialog(shiftName: String, centerCode: String) {
        // Inflate custom dialog view
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_custom, null)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false) // Make dialog non-cancelable to ensure user interaction
            .create()

        // Initialize dialog views
        val backButton = dialogView.findViewById<MaterialButton>(R.id.backButton)
        val nextButton = dialogView.findViewById<MaterialButton>(R.id.nextButton)
        val shiftTextView = dialogView.findViewById<TextView>(R.id.shiftTextView)
        val centerCodeTextView = dialogView.findViewById<TextView>(R.id.centerCodeTextView)
        val centerNameTextView = dialogView.findViewById<TextView>(R.id.centerNameTextView)

        // Set dialog view data
        shiftTextView.text = "Shift: $shiftName"
        centerCodeTextView.text = "Center Code: $centerCode"
        centerNameTextView.text = "Center Name: " + localData.centers(shiftName)

        backButton.setOnClickListener {
            dialog.dismiss()
        }

        nextButton.setOnClickListener {
            // Prepare AttenderProfile data and navigate to the next screen
            val attenderProfile = AttenderProfile(
                centerCode = centerCode,
                shiftTime = shiftName,
                examName = examName,
                password = binding.password.text.toString()
            )

            val action = VerificationScreenDirections.actionVerificationScreen2ToAddProfileDetails2(
                attenderProfile
            )
            findNavController().navigate(action)

            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

    override fun onResume() {
        super.onResume()

        // Set adapters for Exam
        binding.autoCompleteTextViewExam.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_single_choice,
                ExamProvider.getExamNames()
            )
        )

        binding.autoCompleteTextViewShift.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_single_choice,
                ExamProvider.getShifts()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference to avoid memory leaks
        _binding = null
    }
}
