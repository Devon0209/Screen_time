package com.example.screentime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screentime.databinding.ActivityMyspaceadminBinding
import com.google.firebase.auth.FirebaseAuth


class MyspaceAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyspaceadminBinding
    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyspaceadminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser ==null){
            //not logged in goto mainscreen
            startActivity(Intent(this,  LoginActivity::class.java))
            finish()
        }
        else{

            val email= firebaseUser.email
            //set to textview of toolbar
            binding.userID.text = email
        }
    }


}
