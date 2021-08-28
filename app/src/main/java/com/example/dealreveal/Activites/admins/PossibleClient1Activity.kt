package com.example.dealreveal.Activites.admins

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R

class PossibleClient1Activity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_possible_client1)
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

        val Businesstext = findViewById<TextView>(R.id.editTextTextPersonName6)
        Businesstext.setText(Clientname)
        val POCtext = findViewById<TextView>(R.id.editTextTextPersonName7)
        POCtext.setText(PointOfContact)
        val Emailtext = findViewById<TextView>(R.id.editTextTextPersonName8)
        Emailtext.setText(email)
        val passwordtext = findViewById<TextView>(R.id.editTextTextPersonName9)
        passwordtext.setText(Password)
        val phonetext = findViewById<TextView>(R.id.editTextPhone6)
        phonetext.setText(PhoneNumber)
        val avatar = findViewById<ImageView>(R.id.imageView4)
        Glide.with(this@PossibleClient1Activity)
            .load(MealImageUrl)
            .into(avatar)

        val nextbutton = findViewById<Button>(R.id.button25)
        nextbutton.setOnClickListener {
            Clientname = Businesstext.text.toString()
            PointOfContact = POCtext.text.toString()
            email = Emailtext.text.toString()
            Password = passwordtext.text.toString()
            PhoneNumber = phonetext.text.toString()

            val intent = Intent(this@PossibleClient1Activity, possibleclient2Activity::class.java)
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