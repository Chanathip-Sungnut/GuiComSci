package com.example.appwithsignin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.os.Bundle
import android.view.View
import com.example.appwithsignin.Calculate
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_console.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.Number

class Console : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


//******************************************************************************************************//

    val S1 = arrayOf("Select Station","Hau Lamphong","Sam Yan","Si Lom","Lumphini","Khlong Toei","Convention Centre","Sukhuvit","Phetchaburi")
    val S2 = arrayOf("Select Station","Hau Lamphong","Sam Yan","Si Lom","Lumphini","Khlong Toei","Convention Centre","Sukhuvit","Phetchaburi")

    var Cost : Int = 0
    var Type : String = ""

//******************************************************************************************************//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        auth = FirebaseAuth.getInstance()

        CS_Signout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@Console,MainActivity::class.java))
        }


        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,S1 )
        val arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,S2 )
        spinnerS.adapter = arrayAdapter
        spinnerE.adapter = arrayAdapter2




    }





    ////////******************************************************************************************************////////
    fun onClickCal(view: View) {
        val Calculate = Intent(this@Console,Calculate::class.java)
        if(G1.checkedRadioButtonId != -1) {
            if(CityRadio.isChecked){ Cost = 5; Type = "City Line"}
            else if(ExpressRadio.isChecked) {Cost = 10; Type = "ExpressLine"}
        }
        if(G2.checkedRadioButtonId != -1) {
            if(PeopleRadio.isChecked){ Cost = Cost + 5}
            else if(StudentRadio.isChecked) {Cost = Cost + 3}
        }


        Calculate.putExtra("StartStation",spinnerS.selectedItem.toString())
        Calculate.putExtra("EndStation",spinnerE.selectedItem.toString())
        Calculate.putExtra("StartPosition",spinnerS.selectedItemPosition)
        Calculate.putExtra("EndPosition",spinnerE.selectedItemPosition)
        Calculate.putExtra("Number",Number.getText().toString().toInt())
        Calculate.putExtra("Cost",Cost)
        Calculate.putExtra("Type",Type)

        if(G1.checkedRadioButtonId != -1 && G2.checkedRadioButtonId != -1)
            if(Number.getText().toString().toInt() != 0)
                if(spinnerS.selectedItemPosition != 0 && spinnerE.selectedItemPosition != 0)
                    if(spinnerS.selectedItemPosition != spinnerE.selectedItemPosition )
                        startActivity(Calculate)
                    else Toast.makeText(applicationContext,"Please Select",Toast.LENGTH_LONG).show()


    }

    fun onClickClear(view: View) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,S1 )
        val arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,S2 )
        spinnerS.adapter = arrayAdapter
        spinnerE.adapter = arrayAdapter2
        G1.clearCheck()
        G2.clearCheck()
        Number.setText("1")


    }
////////******************************************************************************************************////////





}





