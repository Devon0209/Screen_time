package com.example.screentime

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.screentime.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.screentime.AddBook
import java.text.SimpleDateFormat
import java.util.*
import com.example.screentime.databinding.AddbookBinding

class AddBook : AppCompatActivity() {

    lateinit var imageUri: Uri
    lateinit var storageReference: StorageReference
    lateinit var progressDialog: ProgressDialog

    private lateinit var binding : AddbookBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddbookBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.donebtn.setOnClickListener(){


            val title = binding.booktitle.text.toString()
            val isbn = binding.isbn.text.toString()
            val author = binding.author.text.toString()
            val date = binding.date.text.toString()
            val pub = binding.publisher.text.toString()
            val descrip = binding.description.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Books")
            val book = Book(title,isbn,author,date,pub,descrip)
            database.child(book.toString()).setValue(book).addOnSuccessListener {


                binding.booktitle.text.clear()
                binding.isbn.text.clear()
                binding.author.text.clear()
                binding.date.text.clear()
                binding.publisher.text.clear()
                binding.description.text.clear()

                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()


            }.addOnFailureListener{

                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()


            }

           // uploadImage()
        }

        binding.selectImagebtn.setOnClickListener(){

            selectImage()
        }
    }


 /*   For task 3

 private fun uploadImage() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading File....")
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val fileName = formatter.format(Date())
        storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                binding.firebaseimage.setImageURI(null)
                Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }
            .addOnFailureListener {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                Toast.makeText(this, "Failed to Upload", Toast.LENGTH_SHORT).show()
            }
    }
*/

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data?.data != null) {
            val imageUri = data.data
            binding.firebaseimage.setImageURI(imageUri)
        }
    }
}

