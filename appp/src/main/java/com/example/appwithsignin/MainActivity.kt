package com.example.appwithsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        SI_Createaccount.setOnClickListener {
            startActivity(Intent(this@MainActivity,CreateAccount::class.java))
            finish()
        }

        SI_Signin.setOnClickListener {
            LogIn()
        }



    }

    private fun LogIn() {
        if (SI_Username.text.toString().isEmpty()) {
            SI_Username.error = "Please Enter Email"
            SI_Username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(SI_Username.text.toString()).matches()) {
            SI_Username.error = "Please Enter Valid Email"
            SI_Username.requestFocus()
            return
        }

        if (SI_Password.text.toString().isEmpty()) {
            SI_Password.error = "Please Enter Password"
            SI_Password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(SI_Username.text.toString(),SI_Password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }
            }
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?){

        if(currentUser != null){
            if(currentUser.isEmailVerified){
            startActivity(Intent(this@MainActivity,Console::class.java))
            finish()
            }
            else{
                Toast.makeText(baseContext, "Please Verify Your Email Address",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        else {
            Toast.makeText(baseContext, "Sign In failed",
                Toast.LENGTH_SHORT
            ).show()

        }



    }
}


