package com.example.screentime

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.screentime.databinding.LoginUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: LoginUserBinding

    //firebase dialog
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()

        //init progress dialog, will show while creating account| Register user
        progressDialog = ProgressDialog( this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)


        //handle click, forgot password
        binding.forgotpasswordBtn.setOnClickListener {

 }//handle click, sign up
        binding.signupBtn.setOnClickListener {
startActivity(Intent( this, AccountActivity::class.java))
        }


        //handle click, login done button
        binding.usrloginBtn.setOnClickListener {
        validateData()
        }
    }


    private var email=""
    private var password=""

    private fun validateData() {
        //1) Input Data
        email= binding.emailET.text.toString().trim()
        password= binding.passwordET.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText( this,  "Confirm your email adress...", Toast.LENGTH_SHORT).show()
        }
        else{
            loginUser()
        }

    }

    private fun loginUser() {
       progressDialog.setMessage("Logging in...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener{
                        checkUser()
                    }

                .addOnFailureListener{ e->
               //failed login
               progressDialog.dismiss()
               Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()

           }
    }
    private fun checkUser() {
        /*) Check user type - Firebase Auth
        *  if User- Move to user dashboard
        *   if Admin -  Move to admin dashboard*/

         val firebaseUser = firebaseAuth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        firebaseUser?.let {
            ref.child(it.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {
                        progressDialog.dismiss()
                        //get user type eg user or admin
                        val userType = snapshot.child("userType").value

                        if (userType == "user") {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else if (userType == "admin") {
                            //its admin, open admin dashboard
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError){

                        //nothing yet
                    }
                })
        }
    }


}

