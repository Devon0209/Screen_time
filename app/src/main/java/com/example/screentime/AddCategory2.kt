package com.example.screentime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.screentime.databinding.ActivityAddCategory2Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddCategory2 : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddCategory2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddCat.setOnClickListener() {

            val catName = binding.editTitleName.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Category")
            val categoryModel = CategoryModel(catName)
            database.child(categoryModel.toString()).setValue(categoryModel).addOnSuccessListener {


                binding.editTitleName.text.clear()

                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {

                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()


            }


        }

    }
}