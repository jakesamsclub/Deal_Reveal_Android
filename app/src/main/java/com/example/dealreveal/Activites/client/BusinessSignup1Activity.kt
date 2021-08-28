package com.example.dealreveal.Activites.client

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.HelpOverviewActivity
import com.example.dealreveal.Activites.HelpReminderActivity
import com.example.dealreveal.R

class BusinessSignup1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_signup1)

        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener{
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("")

        val Signin = findViewById<Button>(R.id.button15)
        Signin.setOnClickListener{
            val helpid = "user"
            val intent = Intent(this, HelpReminderActivity::class.java)
            intent.putExtra("HELPID",helpid)
            startActivity(intent)
        }

        val BusinessName = findViewById<TextView>(R.id.editTextTextPersonName2)
        val Address = findViewById<TextView>(R.id.editTextTextPostalAddress)
        val Pointofcontact = findViewById<TextView>(R.id.editTextTextPersonName3)

        val nextbutton = findViewById<Button>(R.id.button14)
        nextbutton.setOnClickListener{
            var sendBusinessName = BusinessName.text.toString()
            var sendAddress = Address.text.toString()
            var sendPointofcontact = Pointofcontact.text.toString()

            val intent = Intent(this, BusinessSignup2Activity::class.java)
            intent.putExtra("BusinessName",sendBusinessName)
            intent.putExtra("Address",sendAddress)
            intent.putExtra("Pointofcontact",sendPointofcontact)
            startActivity(intent)
        }
    }
}