package com.developstudio.attendanceapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developstudio.attendanceapp.R
import com.developstudio.attendanceapp.data.CandidateDetails

class SingleCandidateAdapter(private val candidate: CandidateDetails) :
    RecyclerView.Adapter<SingleCandidateAdapter.CandidateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_candidate, parent, false)
        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        // Since there is only one item, binding will be done to the single candidate
        holder.nameTextView.text = candidate.name
        holder.rollNumberTextView.text = "Roll Number: ${candidate.rollNumber}"
        holder.dobTextView.text = "DOB: ${candidate.dob}"
        holder.shiftTextView.text = "Shift: ${candidate.shift}"
        holder.shiftDateTextView.text = "Shift Date: ${candidate.shiftDate}"
        holder.examNameTextView.text = "Exam: ${candidate.examName}"
        holder.isPresentTextView.text = "Present: ${if (candidate.isPresent) "Yes" else "No"}"
    }

    override fun getItemCount(): Int = 1 // Only one item to display

    inner class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val rollNumberTextView: TextView = itemView.findViewById(R.id.rollNumberTextView)
        val dobTextView: TextView = itemView.findViewById(R.id.dobTextView)
        val shiftTextView: TextView = itemView.findViewById(R.id.shiftTextView)
        val shiftDateTextView: TextView = itemView.findViewById(R.id.shiftDateTextView)
        val examNameTextView: TextView = itemView.findViewById(R.id.examNameTextView)
        val isPresentTextView: TextView = itemView.findViewById(R.id.isPresentTextView)
    }
}