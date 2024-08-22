package com.developstudio.attendanceapp.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developstudio.attendanceapp.data.AttenderProfile
import com.developstudio.attendanceapp.data.CandidateDetails
import com.developstudio.attendanceapp.database.FirebaseHelper
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _imageUploadResult1 = MutableLiveData<Result<String>>()
    val imageUploadResult1: LiveData<Result<String>> = _imageUploadResult1

    private val _imageUploadResult2 = MutableLiveData<Result<String>>()
    val imageUploadResult2: LiveData<Result<String>> = _imageUploadResult2

    // Function to upload the first image
    fun uploadImageToFirebase(uri: Uri, userID: String, isFirstImage: Boolean) {
        val storageReference = FirebaseStorage.getInstance().reference
            .child("users/$userID/${System.currentTimeMillis()}.jpg")

        storageReference.putFile(uri)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    if (isFirstImage) {
                        _imageUploadResult1.value = Result.success(downloadUri)
                    } else {
                        _imageUploadResult2.value = Result.success(downloadUri)
                    }
                } else {
                    if (isFirstImage) {
                        _imageUploadResult1.value = Result.failure(task.exception ?: Exception("Unknown error"))
                    } else {
                        _imageUploadResult2.value = Result.failure(task.exception ?: Exception("Unknown error"))
                    }
                }
            }
    }
    val userCreateStatus = MutableLiveData<Pair<Boolean, String>>()

    fun createUser(user: AttenderProfile) {
        viewModelScope.launch {
            FirebaseHelper().createUser(user) { isSuccess, message ->
                userCreateStatus.postValue(Pair(isSuccess, message))
            }
        }
    }
    val userDetails = MutableLiveData<AttenderProfile?>()
    private val firebaseHelper = FirebaseHelper()

    fun getUserDetails(userName: String) {
        viewModelScope.launch {
            FirebaseHelper().getUserDetails(userName) { user ->
                userDetails.postValue(user)
            }
        }
    }



    // LiveData to hold the list of candidates
    val candidatesLiveDataList = MutableLiveData<List<CandidateDetails>>()
    val candidateLiveData = MutableLiveData<CandidateDetails?>()

    // Method to fetch all candidates
    fun fetchAllCandidates() {
        viewModelScope.launch {
            val candidates = firebaseHelper.getAllCandidates()
            candidatesLiveDataList.postValue(candidates)
        }
    }

    // Method to fetch a candidate by roll number
    fun fetchCandidateByRollNumber(rollNumber: String) {
        viewModelScope.launch {
            val candidate = firebaseHelper.getCandidateByRollNumber(rollNumber)
            candidateLiveData.postValue(candidate)
        }
    }


}