package com.example.appwithsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView
import com.example.appwithsignin.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_calculate.*
import kotlinx.android.synthetic.main.activity_calculate.C_Signout
import kotlinx.android.synthetic.main.activity_console.*


class Calculate : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)
        auth = FirebaseAuth.getInstance()

        C_Signout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@Calculate,MainActivity::class.java))
        }
        Backbutton.setOnClickListener {
            startActivity(Intent(this@Calculate,Console::class.java))
        }



        val Cost = intent.getIntExtra("Cost",0)
        val Type = intent.getStringExtra("Type")
        val StartStation = intent.getStringExtra("StartStation")
        val EndStation = intent.getStringExtra("EndStation")
        val StartPosition = intent.getIntExtra("StartPosition",0)
        val EndPosition = intent.getIntExtra("EndPosition",0)
        val Number = intent.getIntExtra("Number",0)

        val P = cal(StartPosition,EndPosition,Cost,1)
        val TP = cal(StartPosition,EndPosition,Cost,Number)




//Text View Show Data
        TrainType.text = Type
        SS.text = StartStation
        ES.text = EndStation
        NumberOfPassengers.text = ""+ Number
        PricePerPerson.text = ""+P
        TotalPrice.text = ""+TP

    }


    fun cal(x : Int, y : Int, Cost : Int, Number : Int) : Int {
        var D : Int = 0
        var TotalPrice : Int = 0

        if(x > y){ D = x - y; }
        else{ D = y - x; }

        while(D>0) {
            TotalPrice+=5
            D--
        }
        TotalPrice = (TotalPrice+Cost)*Number

        return TotalPrice
    }
}
