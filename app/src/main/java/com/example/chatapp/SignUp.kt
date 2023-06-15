package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

   lateinit var edtName :EditText
    lateinit var edtEmail : EditText
    lateinit var edtPassword : EditText
    lateinit var btnSignUp: Button
    lateinit var mAuth: FirebaseAuth // declaring firebase Authentication
    lateinit var mDBRef :DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword= findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignUp)

        mAuth = FirebaseAuth.getInstance() // initializing firebase Auth


        btnSignUp.setOnClickListener{
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(name,email,password)
        }

    }

   private fun signUp(name :String, email:String,password:String){
       // logic of creating user
       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
           if (task.isSuccessful){
               // code for jumping to home activity
               addUserToDB(name, email, mAuth.currentUser?.uid!!)
               val intent = Intent(this@SignUp, AllUserActivity::class.java)
               finish()
               startActivity(intent)

           }else{
               Toast.makeText(this@SignUp,"Some error occurred",Toast.LENGTH_SHORT).show()
           }
       }
    }

    private fun addUserToDB(name: String, email: String, uid:String){
        mDBRef = FirebaseDatabase.getInstance().getReference()

        mDBRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}
