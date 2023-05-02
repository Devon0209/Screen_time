package com.example.screentime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screentime.databinding.ActivityBooksBinding
import za.ac.iie.opsc7311.orbit_prototype.BooksAdapter
import com.example.screentime.BookActivity

class BookActivity : AppCompatActivity() {
    lateinit var binding: ActivityBooksBinding
    val activity = this
    val list: ArrayList<BooksModel> = ArrayList()
    val adapter = BooksAdapter(list, activity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            mRecyclerViewHome.adapter = adapter
            list.add(BooksModel(R.drawable.book_1,"Rich Dad Poor Dad",getString(R.string.description_1),"sample_book.pdf"))
            list.add(BooksModel(R.drawable.book_2,"Atomic Habits",getString(R.string.description_2),"sample_book.pdf"))
            list.add(BooksModel(R.drawable.book_3,"Best Self",getString(R.string.description_3),"sample_book.pdf"))
            list.add(BooksModel(R.drawable.book_4,"How To Be Fine",getString(R.string.description_4),"sample_book.pdf"))
            list.add(BooksModel(R.drawable.book_5,"Out of the Box",getString(R.string.description_5),"sample_book.pdf"))
            list.add(BooksModel(R.drawable.book_6,"Stripped",getString(R.string.description_6),"sample_book.pdf"))
        }

        binding.addBookBtn.setOnClickListener(){

            val intent = Intent(this@BookActivity, AddBook ::class.java)

            startActivity(intent)
        }

    }
}