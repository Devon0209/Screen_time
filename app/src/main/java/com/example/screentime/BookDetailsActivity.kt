package com.example.screentime


import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.example.screentime.databinding.ActivityBookDetailsBinding
import android.widget.Toast
import java.io.File
import java.io.IOException
import android.view.View
import okhttp3.*
import java.io.FileOutputStream
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.content.Intent


class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private var pdfUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val img = intent.getStringExtra("img")
        pdfUrl = intent.getStringExtra("pdfUrl")

        // Use the retrieved data to populate the views in your activity
        binding.name.text = title
        binding.info.text = description
        // Load the image using Picasso or any other image loading library
        Picasso.get().load(img).into(binding.img)

        binding.button.setOnClickListener {
            pdfUrl?.let { url ->
                openPdfUrl(url)
            }
        }
    }

    private fun openPdfUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}






