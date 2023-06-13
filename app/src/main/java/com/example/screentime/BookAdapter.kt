package com.example.screentime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.view.View

import android.content.Context
import android.content.Intent

import com.example.screentime.databinding.EachItemBinding

class BookAdapter(
    var bookList: List<BookData>,
    private val context: Context
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class BookViewHolder(private val binding: EachItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(book: BookData) {
            binding.title.text = book.title
            binding.description.text = book.description
            Picasso.get().load(book.img).into(binding.imageView)
            itemView.tag = book
        }

        override fun onClick(view: View) {
            val book = view.tag as BookData
            val intent = Intent(context, BookDetailsActivity::class.java).apply {
                putExtra("title", book.title)
                putExtra("description", book.description)
                putExtra("img", book.img)
                putExtra("pdfUrl", book.pdfUrl)
            }
            context.startActivity(intent)
        }
    }
}



