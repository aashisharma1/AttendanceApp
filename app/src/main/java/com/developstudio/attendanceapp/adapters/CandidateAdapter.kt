package com.developstudio.attendanceapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developstudio.attendanceapp.R
import com.developstudio.attendanceapp.data.CandidateDetails

class CandidateAdapter(private val candidates: List<CandidateDetails>) :
    RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidate_show, parent, false)
        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.nameTextView.text = candidate.name
        holder.rollNumberTextView.text = candidate.rollNumber
        holder.fatherName.text = candidate.fatherName
    }

    override fun getItemCount(): Int = candidates.size

    inner class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.demoName)
        val rollNumberTextView: TextView = itemView.findViewById(R.id.demoRollNumber)
        val fatherName: TextView = itemView.findViewById(R.id.demoFatherName)
    }
}