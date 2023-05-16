package com.example.screentime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.databinding.ActivityUserCategoryBinding
import com.google.firebase.database.*
import za.ac.iie.opsc7311.orbit_prototype.CategoryAdapter

class UserCategoryActivity : AppCompatActivity() {

    private lateinit var categoryRecyclerview : RecyclerView
    private lateinit var categoryModelArrayList : ArrayList<CategoryModel>
    private lateinit var dbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryRecyclerview = binding.categorylist
        categoryRecyclerview.layoutManager = LinearLayoutManager(this)
        categoryRecyclerview.setHasFixedSize(true)

        categoryModelArrayList = arrayListOf<CategoryModel>()
        getCategoryData()



        binding.myspace.setOnClickListener(){

            val intent = Intent(this, MyspaceAdminActivity::class.java)
            startActivity(intent)


        }


        binding.library.setOnClickListener(){

            val intent = Intent(this, Category::class.java)
            startActivity(intent)


        }





    }

    private fun getCategoryData() {

        dbref = FirebaseDatabase.getInstance().getReference("Category")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val categoryModel= userSnapshot.getValue(CategoryModel::class.java)
                        categoryModelArrayList.add(categoryModel!!)

                    }

                    var adapter = CategoryAdapter(categoryModelArrayList)
                    categoryRecyclerview .adapter = adapter

                    adapter.setItemClickListener(object : CategoryAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                            val selectedCategoryName = categoryModelArrayList[position].name.toString()
                            val intent = Intent(this@UserCategoryActivity, UserBooks::class.java)
                            intent.putExtra("categoryName", selectedCategoryName)
                            startActivity(intent)
                            Toast.makeText(this@UserCategoryActivity,"Success $position", Toast.LENGTH_SHORT).show()

                        }

                    })



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })



    }
}