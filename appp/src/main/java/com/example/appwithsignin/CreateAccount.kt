package com.example.appwithsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*

class CreateAccount : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        auth = FirebaseAuth.getInstance()

        CA_Createaccount.setOnClickListener {
            SingUpUser()
        }
        CA_Signin.setOnClickListener {
            startActivity(Intent(this@CreateAccount,MainActivity::class.java))

        }
    }

    private fun SingUpUser(){
        if(CA_Username.text.toString().isEmpty()){
            CA_Username.error = "Please Enter Email"
            CA_Username.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(CA_Username.text.toString()).matches()){
            CA_Username.error = "Please Enter Valid Email"
            CA_Username.requestFocus()
            return
        }

        if(CA_Password.text.toString().isEmpty()){
            CA_Password.error = "Please Enter Password"
            CA_Password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(CA_Username.text.toString(),CA_Password.text.toString())
            .addOnCompleteListener(this) { task ->
                val user = auth.currentUser
                if (task.isSuccessful) {
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this@CreateAccount,MainActivity::class.java))
                                finish()
                            }
                        }
                    startActivity(Intent(this@CreateAccount,MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Create Account Failed. Try Again.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
