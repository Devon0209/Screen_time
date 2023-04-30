package com.example.screentime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screentime.databinding.ActivityMyspaceBinding
import com.google.firebase.auth.FirebaseAuth


class MyspaceUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyspaceBinding
    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyspaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity( Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser ==null){
            //not logged in, user can stay in user My space without login to binding.
            binding.userID.text = "not Logged In"

        }
        else{

            val email= firebaseUser.email
            //set to textview of toolbar
            binding.userID.text = email
        }
    }


}
