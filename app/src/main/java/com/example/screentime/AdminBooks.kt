package com.example.screentime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.example.screentime.databinding.ActivityAdminBooksBinding


class AdminBooks : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBooksBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bookBtn.setOnClickListener {
            startActivity(Intent(this, CreateBookActivity::class.java))
        }

        initVars()
        retrieveBooks()
    }

    private fun initVars() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        adapter = BookAdapter(emptyList(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun retrieveBooks() {
        firebaseFirestore.collection("Books").get()
            .addOnSuccessListener { documents ->
                val authorList = mutableListOf<BookData>()
                for (document in documents) {
                    val book = document.toObject(BookData::class.java)
                    authorList.add(book)
                }
                adapter.bookList = authorList
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error retrieving books: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

