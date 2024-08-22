package com.developstudio.attendanceapp.database

import android.util.Log
import com.developstudio.attendanceapp.data.AttenderProfile
import com.developstudio.attendanceapp.data.CandidateDetails
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class FirebaseHelper {
    private val db = FirebaseFirestore.getInstance()


    suspend fun createUser(user: AttenderProfile, callback: (Boolean, String) -> Unit) {
        try {
            db.collection("users")
                .document(user.attenderID)
                .set(user)
                .await()
            callback(true, "User created successfully")
        } catch (e: FirebaseFirestoreException) {
            callback(false, e.message ?: "Error creating user")
        }
    }


    suspend fun getUserDetails(userName: String, callback: (AttenderProfile?) -> Unit) {
        try {
            val documentSnapshot = db.collection("users")
                .document(userName)
                .get()
                .await()
            if (documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(AttenderProfile::class.java)
                callback(user)
            } else {
                callback(null)
            }
        } catch (e: FirebaseFirestoreException) {
            callback(null)
        }
    }
/*
    private val candidatesCollection = db.collection("candidates").document("batch_2024")
        .collection("candidates")

    // Method to fetch all candidates
    suspend fun getAllCandidates(): List<CandidateDetails> {
        return try {
            val snapshot = candidatesCollection.get().await()
            if (snapshot.isEmpty) {
                Log.d("Firestore", "No documents found.")
                return emptyList()
            }
            snapshot.documents.mapNotNull { it.toObject(CandidateDetails::class.java) }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching documents", e)
            emptyList()
        }
    }


 */
private val documentRef = db.collection("candidates").document("exams")

    // Method to fetch all candidates
    suspend fun getAllCandidates(): List<CandidateDetails> {
        return try {
            val documentSnapshot = documentRef.get().await()
            val candidatesList = documentSnapshot.get("candidates") as? List<Map<String, Any>> ?: emptyList()
            Log.d("FirebaseHelper", "Fetched ${candidatesList.size} candidates")
            candidatesList.mapNotNull { data ->
                val name = data["name"] as? String ?: return@mapNotNull null
                val fatherName = data["fatherName"] as? String ?: ""
                val rollNumber = data["rollNumber"] as? String ?: ""
                val dob = data["dob"] as? String ?: ""
                val shift = data["shift"] as? String ?: ""
                val shiftDate = data["shiftDate"] as? String ?: ""
                val examName = data["examName"] as? String ?: ""
                val isPresent = data["isPresent"] as? Boolean ?: false
                CandidateDetails(
                    name = name,
                    fatherName = fatherName,
                    rollNumber = rollNumber,
                    dob = dob,
                    shift = shift,
                    shiftDate = shiftDate,
                    examName = examName,
                    isPresent = isPresent
                )
            }
        } catch (e: Exception) {
            Log.e("FirebaseHelper", "Error fetching candidates", e)
            emptyList()
        }
    }

    // Method to fetch a candidate by roll number
    suspend fun getCandidateByRollNumber(rollNumber: String): CandidateDetails? {
        return try {
            val documentSnapshot = documentRef.get().await()
            val candidatesList = documentSnapshot.get("candidates") as? List<Map<String, Any>> ?: return null
            candidatesList.mapNotNull { data ->
                val name = data["name"] as? String ?: return@mapNotNull null
                val fatherName = data["fatherName"] as? String ?: ""
                val rollNumberFromData = data["rollNumber"] as? String ?: ""
                if (rollNumberFromData == rollNumber) {
                    val dob = data["dob"] as? String ?: ""
                    val shift = data["shift"] as? String ?: ""
                    val shiftDate = data["shiftDate"] as? String ?: ""
                    val examName = data["examName"] as? String ?: ""
                    val isPresent = data["isPresent"] as? Boolean ?: false
                    return CandidateDetails(
                        name = name,
                        fatherName = fatherName,
                        rollNumber = rollNumberFromData,
                        dob = dob,
                        shift = shift,
                        shiftDate = shiftDate,
                        examName = examName,
                        isPresent = isPresent
                    )
                }
                null
            }.firstOrNull()
        } catch (e: Exception) {
            Log.e("FirebaseHelper", "Error fetching candidate by roll number", e)
            null
        }
    }
}