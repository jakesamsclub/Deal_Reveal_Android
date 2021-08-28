package com.example.dealreveal.Activites.admins

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.HelpOverviewActivity
import com.example.dealreveal.Activites.possibleclient3Activity
import com.example.dealreveal.R

class possibleclient2Activity : AppCompatActivity() {
    var Address = ""
    var CUID = ""
    var Clientname = ""
    var CompanyURL = ""
    var Date = ""
    var Facebook = ""
    var MealImageUrl = ""
    var Password = ""
    var PhoneNumber = ""
    var PointOfContact = ""
    var Yelp = ""
    var email = ""
    var setuptime = ""
    var Longitude = ""
    var Latitude = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_possibleclient2)
        headerandbottom()

        val intent = intent
        Address = intent.getStringExtra("Address").toString()
        CUID = intent.getStringExtra("CUID").toString()
        Clientname = intent.getStringExtra("Clientname").toString()
        CompanyURL = intent.getStringExtra("CompanyURL").toString()
        Date = intent.getStringExtra("Date").toString()
        Facebook = intent.getStringExtra("Facebook").toString()
        MealImageUrl = intent.getStringExtra("MealImageUrl").toString()
        Password = intent.getStringExtra("Password").toString()
        PhoneNumber = intent.getStringExtra("PhoneNumber").toString()
        PointOfContact = intent.getStringExtra("PointOfContact").toString()
        Yelp = intent.getStringExtra("Yelp").toString()
        email = intent.getStringExtra("email").toString()
        setuptime = intent.getStringExtra("setuptime").toString()

        val Businesswesbite = findViewById<TextView>(R.id.editTextTextPersonName10)
        Businesswesbite.setText(CompanyURL)
        val yelptext = findViewById<TextView>(R.id.editTextTextPersonName11)
        yelptext.setText(Yelp)
        val Addresstext = findViewById<TextView>(R.id.editTextTextPersonName12)
        Addresstext.setText(Address)
        val Latitudetext = findViewById<TextView>(R.id.editTextTextPersonName13)

        val Longitudetext = findViewById<TextView>(R.id.editTextTextPersonName14)

        val nextbutton = findViewById<Button>(R.id.button26)
        nextbutton.setOnClickListener {
            CompanyURL = Businesswesbite.text.toString()
            Yelp = yelptext.text.toString()
            Address = Addresstext.text.toString()
            Latitude = Latitudetext.text.toString()
            Longitude = Longitudetext.text.toString()

            val intent = Intent(this@possibleclient2Activity, possibleclient3Activity::class.java)
            intent.putExtra("Address",Address)
            intent.putExtra("CUID",CUID)
            intent.putExtra("Clientname",Clientname)
            intent.putExtra("CompanyURL",CompanyURL)
            intent.putExtra("Date",Date)
            intent.putExtra("Facebook",Facebook)
            intent.putExtra("MealImageUrl",MealImageUrl)
            intent.putExtra("Password",Password)
            intent.putExtra("PhoneNumber",PhoneNumber)
            intent.putExtra("PointOfContact",PointOfContact)
            intent.putExtra("Yelp",Yelp)
            intent.putExtra("email",email)
            intent.putExtra("setuptime",setuptime)
            intent.putExtra("Longitude",Longitude)
            intent.putExtra("Latitude",Latitude)

            startActivity(intent)
        }

    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("New Clients")
    }
}