package com.example.screentime


import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.screentime.databinding.ActivityCreateBookBinding
import com.example.screentime.BookData
import java.util.*

class CreateBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBookBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var selectedImageUri: Uri
    private lateinit var selectedPdfUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseStorage = FirebaseStorage.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        binding.selecImageBtn.setOnClickListener {
            openImageChooser()
        }

        binding.selectPdf.setOnClickListener {
            openPdfChooser()
        }

        binding.uploadBtn.setOnClickListener {
            uploadBook()
        }

        binding.bookBtn.setOnClickListener {
            startActivity(Intent(this, AdminBooks::class.java))
        }

        binding.mySpacBtn.setOnClickListener {
            // Handle My Space button click
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, RC_IMAGE_PICKER)
    }

    private fun openPdfChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent, RC_PDF_PICKER)
    }

    private fun uploadBook() {
        val title = binding.booktitle.text.toString().trim()
        val author = binding.author.text.toString().trim()
        val description = binding.description.text.toString().trim()

        if (validateForm(title, author, description)) {
            showProgressBar()

            val imageRef = firebaseStorage.reference.child("images/${UUID.randomUUID()}")
            val pdfRef = firebaseStorage.reference.child("pdfs/${UUID.randomUUID()}")

            // Upload image to Firebase Storage
            imageRef.putFile(selectedImageUri)
                .continueWithTask { imageUploadTask ->
                    if (!imageUploadTask.isSuccessful) {
                        imageUploadTask.exception?.let {
                            throw it
                        }
                    }
                    imageRef.downloadUrl
                }
                .addOnCompleteListener { imageDownloadTask ->
                    if (imageDownloadTask.isSuccessful) {
                        val imageUrl = imageDownloadTask.result.toString()

                        // Upload PDF to Firebase Storage
                        pdfRef.putFile(selectedPdfUri)
                            .continueWithTask { pdfUploadTask ->
                                if (!pdfUploadTask.isSuccessful) {
                                    pdfUploadTask.exception?.let {
                                        throw it
                                    }
                                }
                                pdfRef.downloadUrl
                            }
                            .addOnCompleteListener { pdfDownloadTask ->
                                if (pdfDownloadTask.isSuccessful) {
                                    val pdfUrl = pdfDownloadTask.result.toString()

                                    // Save book data to Firestore
                                    val bookData = BookData(title,description ,imageUrl, author, pdfUrl)
                                    firebaseFirestore.collection("Books")
                                        .add(bookData)
                                        .addOnSuccessListener {
                                            hideProgressBar()
                                            resetForm()
                                            Toast.makeText(this, "Book uploaded successfully", Toast.LENGTH_SHORT).show()
                                        }
                                        .addOnFailureListener { exception ->
                                            hideProgressBar()
                                            Toast.makeText(this, "Error uploading book: ${exception.message}", Toast.LENGTH_SHORT).show()
                                        }
                                } else {
                                    hideProgressBar()
                                    Toast.makeText(this, "Error uploading PDF", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        hideProgressBar()
                        Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(title: String, author: String, description: String): Boolean {
        return when {
            title.isEmpty() -> {
                binding.booktitle.error = "Please enter the book title"
                false
            }
            author.isEmpty() -> {
                binding.author.error = "Please enter the author name"
                false
            }
            description.isEmpty() -> {
                binding.description.error = "Please enter the book description"
                false
            }
            !this::selectedImageUri.isInitialized -> {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                false
            }
            !this::selectedPdfUri.isInitialized -> {
                Toast.makeText(this, "Please select a PDF", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun resetForm() {
        binding.booktitle.text.clear()
        binding.author.text.clear()
        binding.description.text.clear()
        binding.imageCardView.setImageResource(R.drawable.catbackground)
        selectedImageUri = Uri.EMPTY
        selectedPdfUri = Uri.EMPTY
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RC_IMAGE_PICKER -> {
                    data?.data?.let {
                        selectedImageUri = it
                        binding.imageCardView.setImageURI(it)
                    }
                }
                RC_PDF_PICKER -> {
                    data?.data?.let {
                        selectedPdfUri = it
                    }
                }
            }
        }
    }

    companion object {
        private const val RC_IMAGE_PICKER = 123
        private const val RC_PDF_PICKER = 456
    }
}