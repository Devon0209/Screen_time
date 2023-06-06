package com.example.screentime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener




class SplashActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        firebaseAuth = FirebaseAuth.getInstance()
        Handler().postDelayed(Runnable {
            checkUser()
        },  1000) //means 1 seconds
    }

            private fun checkUser () {
//get current ver, if logged in or not
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser == null) {
                    //user not logged in, goto main screen
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
//user logged in, check user type, same as done in login page

                    val firebaseUser = firebaseAuth.currentUser
                    val ref = FirebaseDatabase.getInstance().getReference("Users")
                    firebaseUser?.let {
                        ref.child(it.uid)
                            .addListenerForSingleValueEvent(object : ValueEventListener {

                                override fun onDataChange(snapshot: DataSnapshot) {

                                    //get user type eg user or admin
                                    val userType = snapshot.child("userType").value

                                    if (userType == "user") {
                                        startActivity(
                                            Intent(
                                                this@SplashActivity,
                                                LoginActivity::class.java
                                            )
                                        )
                                        finish()
                                    } else if (userType == "admin") {
                                        //its admin, open admin dashboard
                                        startActivity(
                                            Intent(
                                                this@SplashActivity,
                                                LoginActivity::class.java
                                             )
                                        )
                                        finish()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                    //nothing yet
                                }
                            })
                    }
                }
            }}




/*Keep user logged in
* 1) Check if user logged in
* 2) check type of user*/