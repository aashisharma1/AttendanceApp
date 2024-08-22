package com.developstudio.attendanceapp.database

import com.developstudio.attendanceapp.data.CandidateDetails

object CandidateDataProvider {

    private val demoData = listOf(
        CandidateDetails(
            name = "Amit Sharma",
            fatherName = "Rajesh Sharma",
            rollNumber = "101",
            dob = "01-01-2000",
            shift = "Morning",
            shiftDate = "01-09-2024",
            examName = "Engineering Entrance",
            isPresent = true
        ),
        CandidateDetails(
            name = "Neha Patel",
            fatherName = "Ravi Patel",
            rollNumber = "102",
            dob = "02-02-1999",
            shift = "Evening",
            shiftDate = "02-09-2024",
            examName = "Medical Entrance",
            isPresent = false
        ),
        CandidateDetails(
            name = "Rahul Gupta",
            fatherName = "Vikram Gupta",
            rollNumber = "103",
            dob = "03-03-2001",
            shift = "Morning",
            shiftDate = "03-09-2024",
            examName = "Engineering Entrance",
            isPresent = true
        ),
        CandidateDetails(
            name = "Priya Singh",
            fatherName = "Sunil Singh",
            rollNumber = "104",
            dob = "04-04-2000",
            shift = "Evening",
            shiftDate = "04-09-2024",
            examName = "Medical Entrance",
            isPresent = true
        ),
        CandidateDetails(
            name = "Rohit Mehta",
            fatherName = "Suresh Mehta",
            rollNumber = "105",
            dob = "05-05-1998",
            shift = "Morning",
            shiftDate = "05-09-2024",
            examName = "Engineering Entrance",
            isPresent = false
        ),
        CandidateDetails(
            name = "Sneha Reddy",
            fatherName = "Kumar Reddy",
            rollNumber = "106",
            dob = "06-06-1999",
            shift = "Evening",
            shiftDate = "06-09-2024",
            examName = "Medical Entrance",
            isPresent = true
        ),
        CandidateDetails(
            name = "Karan Joshi",
            fatherName = "Anil Joshi",
            rollNumber = "107",
            dob = "07-07-2000",
            shift = "Morning",
            shiftDate = "07-09-2024",
            examName = "Engineering Entrance",
            isPresent = true
        ),
        CandidateDetails(
            name = "Mitali Agarwal",
            fatherName = "Rajeev Agarwal",
            rollNumber = "108",
            dob = "08-08-1999",
            shift = "Evening",
            shiftDate = "08-09-2024",
            examName = "Medical Entrance",
            isPresent = false
        )
    )

    // Method to get the list of demo data
    fun getDemoData(): List<CandidateDetails> {
        return demoData
    }

    // Method to get a CandidateDetails object by roll number
    fun getCandidateByRollNumber(rollNumber: String): CandidateDetails? {
        return demoData.find { it.rollNumber == rollNumber }
    }
}