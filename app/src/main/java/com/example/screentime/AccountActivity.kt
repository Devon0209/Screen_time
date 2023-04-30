package com.example.screentime

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.screentime.databinding.CreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AccountActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: CreateAccountBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog

    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog, will show while creating account | Register user
        progressDialog = ProgressDialog( this)
        progressDialog.setTitle("please wait")
        progressDialog.setCanceledOnTouchOutside(false)
        // Hide the Action Bar
        supportActionBar?.hide()

        //handle back button click, goto previous screen
        binding.backbtn1.setOnClickListener{
        onBackPressed()  //goto previous screen
        }

        //handle click, begin register
        binding.signupBtn.setOnClickListener {
            /* Steps
            1) Input data
            2) Validate Data
            3) Create Account - Firebase Auth
            4) Save User Information- Firebase Realtime Database*/
            validateData()
        }

    }

    private var name = ""
    private var email = ""
    private var password =""
    private var conpassword =""

    private fun validateData(){
        //1) Input Data
        name = binding.nameET.text.toString().trim()
        email = binding.emailET.text.toString().trim()
        password= binding.passwordET.text.toString().trim()
        conpassword= binding.conpasswordET.text.toString().trim()

       // 2) Validate Data
        if (name.isEmpty()){
            //empty name...
            Toast.makeText( this,  "Enter your name...", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            //empty password...
            Toast.makeText( this,  "Enter a password...", Toast.LENGTH_SHORT).show()
        }

        else if (conpassword.isEmpty()){
            // confirming the empty email...
            Toast.makeText( this,  "Confirm your email adress...", Toast.LENGTH_SHORT).show()

        }
        else if (email.isEmpty()){
            //empty email...
            Toast.makeText( this,  "Enter your email adress...", Toast.LENGTH_SHORT).show()
        }
       else{
           createUserAccount()
       }


    }

    private fun createUserAccount() {
       //3) Create Account- Firebase auth
        //show progress
        progressDialog.setMessage("Create Account...")
        progressDialog.show()

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)

            .addOnSuccessListener {
                //account creation
                updateUserInfo()
            }
            .addOnFailureListener {   e->
                //failed creating account
                progressDialog.dismiss()
                Toast.makeText( this,  "Failed creating account due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun updateUserInfo(){
        //4) Save User Info - Firebase Realtime Database

        progressDialog.setMessage("Saving user info...")

        //
        val timestamp = System.currentTimeMillis()

        val uid = firebaseAuth.uid

        //set data to add in db
        val hashMap: HashMap<String, Any?> = HashMap()
            hashMap["uid"] = uid
            hashMap["email"] = email
            hashMap["name"] = name
            hashMap["userType"] = "user"
            hashMap["timestamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference( "Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {  e->
                progressDialog.dismiss()
                Toast.makeText( this,  "Account created...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AccountActivity, MainActivity::class.java))
                finish()

            }
            .addOnFailureListener {   e->
                //failed adding data to db
                progressDialog.dismiss()
                Toast.makeText( this,  "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

}