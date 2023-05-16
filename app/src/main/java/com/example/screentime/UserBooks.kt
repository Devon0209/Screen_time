package com.example.screentime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screentime.databinding.ActivityUserBooksBinding
import za.ac.iie.opsc7311.orbit_prototype.BooksAdapter

class UserBooks : AppCompatActivity() {
    lateinit var binding: ActivityUserBooksBinding
    val activity = this
    val list: ArrayList<BooksModel> = ArrayList()
    val adapter = BooksAdapter(list, activity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBooksBinding.inflate(layoutInflater)
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
    }
}