package com.example.dealreveal.Activites.client

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R

class BusinessSignup2Activity : AppCompatActivity() {
    var BusinessName = ""
    var Address = ""
    var Pointofcontact = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_signup2)


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

        val intent = intent
        BusinessName = intent.getStringExtra("BusinessName").toString()
        Address = intent.getStringExtra("Address").toString()
        Pointofcontact = intent.getStringExtra("Pointofcontact").toString()

        val TestURL = findViewById<Button>(R.id.button16)
        TestURL.setOnClickListener{

        }
        val TestYelpURL = findViewById<Button>(R.id.button17)
        TestYelpURL.setOnClickListener{

        }

        val Phonenumber = findViewById<TextView>(R.id.editTextPhone5)
        val Websiteurl = findViewById<TextView>(R.id.editTextTextEmailAddress2)
        val Yelpurl = findViewById<TextView>(R.id.editTextTextEmailAddress3)

        val nextbutton = findViewById<Button>(R.id.button18)
        nextbutton.setOnClickListener{
            var sendPhonenumber = Phonenumber.text.toString()
            var sendWebsiteurl = Websiteurl.text.toString()
            var sendYelpurl = Yelpurl.text.toString()

            val intent = Intent(this, BusinessSignup3Activity::class.java)
            intent.putExtra("Phonenumber",sendPhonenumber)
            intent.putExtra("Websiteurl",sendWebsiteurl)
            intent.putExtra("Yelpurl",sendYelpurl)
            intent.putExtra("BusinessName",BusinessName)
            intent.putExtra("Address",Address)
            intent.putExtra("Pointofcontact",Pointofcontact)

            startActivity(intent)
        }
    }
}