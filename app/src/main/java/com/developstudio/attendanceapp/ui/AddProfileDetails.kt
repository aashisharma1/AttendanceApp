package com.developstudio.attendanceapp.ui

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.developstudio.attendanceapp.data.AttenderProfile
import com.developstudio.attendanceapp.databinding.FragmentAddProfileDetailsBinding
import com.developstudio.attendanceapp.utils.UserIdGenerator
import com.developstudio.attendanceapp.utils.UserIdManager
import com.developstudio.attendanceapp.viewmodels.UserViewModel
import java.io.File

class AddProfileDetails : Fragment() {

    private var _binding: FragmentAddProfileDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    private var profileImage: Uri? = null
    private var documentsImage: Uri? = null
    private var code: Int = 1
    private var userID: String = ""
    private val REQUEST_IMAGE_CAPTURE: Int = 1
    private val REQUEST_IMAGE_DOCUMENTS: Int = 2

    // Retrieve arguments passed to this fragment
    private val args: AddProfileDetailsArgs by navArgs()

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach { (permission, isGranted) ->
                if (isGranted) {
                    dispatchTakePictureIntent(code)
                } else {
                    Toast.makeText(requireContext(), "$permission denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProfileDetailsBinding.inflate(inflater, container, false)

        setupClickListeners()
        observeViewModel()

        return binding.root
    }

    // Set up click listeners for buttons
    private fun setupClickListeners() {
        binding.uploadProfileImage.setOnClickListener {
            code = REQUEST_IMAGE_CAPTURE
            checkPermissions()
        }
        binding.addDocument.setOnClickListener {
            code = REQUEST_IMAGE_DOCUMENTS
            checkPermissions()
        }
        binding.hideDoc.setOnClickListener {
            hideDocImage()
        }
        binding.submitButton.setOnClickListener {
            validateAndSubmit()
        }
    }
    private fun dispatchTakePictureIntent(code: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, code)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

    // Observe LiveData from ViewModel
    private fun observeViewModel() {
        viewModel.imageUploadResult1.observe(viewLifecycleOwner) { result1 ->
            result1.fold(
                onSuccess = { imageUrl1 ->
                    viewModel.imageUploadResult2.observe(viewLifecycleOwner) { result2 ->
                        result2.fold(
                            onSuccess = { imageUrl2 ->
                                handleImageUploadSuccess(imageUrl1, imageUrl2)
                            },
                            onFailure = { exception2 ->
                                showError(exception2.message)
                                hideProgressBar()
                            }
                        )
                    }
                },
                onFailure = { exception1 ->
                    showError(exception1.message)
                    hideProgressBar()
                }
            )
        }

        viewModel.userCreateStatus.observe(viewLifecycleOwner) { (isSuccess, message) ->
            hideProgressBar()
            if (isSuccess) {
                UserIdManager.saveUserId(requireContext(), userID)
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                showError(message)
            }
        }
    }

    // Check permissions based on Android version
    private fun checkPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.CAMERA)
        } else {
            arrayOf(Manifest.permission.CAMERA)
        }
        requestPermissions.launch(permissions)
    }

    // Handle the result of the camera intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap ?: return
            val uri = saveBitmapAndGetUri(imageBitmap)

            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    binding.ProfileImageview.setImageURI(uri)
                    profileImage = uri
                }

                REQUEST_IMAGE_DOCUMENTS -> {
                    binding.documentImage.setImageURI(uri)
                    documentsImage = uri
                    binding.hideDoc.visibility = View.VISIBLE
                    binding.documentImage.visibility = View.VISIBLE
                }
            }
        }
    }

    // Save bitmap and get URI
    private fun saveBitmapAndGetUri(bitmap: Bitmap): Uri {
        val file = File(requireActivity().cacheDir, "${System.currentTimeMillis()}.jpg")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return FileProvider.getUriForFile(
            requireActivity(),
            "${requireActivity().packageName}.provider",
            file
        )
    }

    // Upload images to Firebase
    private fun uploadImages(uri1: Uri, uri2: Uri, userID: String) {
        viewModel.uploadImageToFirebase(uri1, userID, isFirstImage = true)
        viewModel.uploadImageToFirebase(uri2, userID, isFirstImage = false)
    }

    // Handle successful image uploads
    private fun handleImageUploadSuccess(imageUrl1: String, imageUrl2: String) {
        createUser(imageUrl1, imageUrl2)
    }

    // Create user with the uploaded images
    private fun createUser(profileUrl: String, documentUrl: String) {
        val attender = AttenderProfile(
            examName = args.myData.examName,
            shiftTime = args.myData.shiftTime,
            centerCode = args.myData.centerCode,
            password = args.myData.password,
            name = binding.username.text.toString(),
            profilePicture = profileUrl,
            phoneNumber = binding.mobileNumber.text.toString(),
            documentUploaded = documentUrl,
            attenderID = userID
        )
        viewModel.createUser(attender)
    }

    // Validate input fields and initiate image upload
    private fun validateAndSubmit() {
        when {
            binding.username.text.isNullOrEmpty() -> showError("Enter Name")
            binding.mobileNumber.text.isNullOrEmpty() -> showError("Enter number")
            profileImage == null -> showError("Upload Image")
            documentsImage == null -> showError("Upload document")
            else -> {
                showProgressBar()
                userID = UserIdGenerator.generateUserId()
                uploadImages(profileImage!!, documentsImage!!, userID)
            }
        }
    }


    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.cardContainer.visibility = View.VISIBLE
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.cardContainer.visibility = View.GONE
    }

    // Hide document image
    private fun hideDocImage() {
        documentsImage = null
        binding.hideDoc.visibility = View.GONE
        binding.documentImage.visibility = View.GONE
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "An error occurred", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
