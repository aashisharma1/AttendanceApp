package com.developstudio.attendanceapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developstudio.attendanceapp.R
import com.developstudio.attendanceapp.data.CandidateDetails
import com.google.android.material.textview.MaterialTextView

class SingleCandidateAdapter(private val candidate: CandidateDetails) :
    RecyclerView.Adapter<SingleCandidateAdapter.CandidateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_candidate, parent, false)
        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.bind(candidate)
    }

    override fun getItemCount(): Int = 1 // Only one item to display

    inner class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: MaterialTextView = itemView.findViewById(R.id.textName)
        private val textFatherName: MaterialTextView = itemView.findViewById(R.id.textFatherName)
        private val textRollNumber: MaterialTextView = itemView.findViewById(R.id.textRollNumber)
        private val textDob: MaterialTextView = itemView.findViewById(R.id.textDob)
        private val textShift: MaterialTextView = itemView.findViewById(R.id.textShift)
        private val textShiftDate: MaterialTextView = itemView.findViewById(R.id.textShiftDate)
        private val textExamName: MaterialTextView = itemView.findViewById(R.id.textExamName)
        private val textIsPresent: MaterialTextView = itemView.findViewById(R.id.textIsPresent)

        fun bind(candidate: CandidateDetails) {
            textName.text = candidate.name
            textFatherName.text = candidate.fatherName
            textRollNumber.text = candidate.rollNumber
            textDob.text = candidate.dob
            textShift.text = candidate.shift
            textShiftDate.text = candidate.shiftDate
            textExamName.text = candidate.examName
            textIsPresent.text = if (candidate.isPresent) "Yes" else "No"
        }
    }
}