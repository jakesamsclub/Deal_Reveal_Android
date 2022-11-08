package com.example.dealreveal.Activites.client

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.HelpReminderActivity
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
            intent.putExtra("page","New Business Sign Up")
            intent.putExtra("desc","* You must enter the following fields to start creating a new business account...\n\n    1) Your business name \n\n    2) Your business Address \n\n    3) The main name of the contact at the business who will be running the Deal Reveal account. \n\n * Once you have entered all the fields you can press Next")
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

            if (sendBusinessName.isEmpty()) {
                println("Name is empty.")
                Toast.makeText(applicationContext, "Business name field is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            if (sendAddress.isEmpty()) {
                println("Email is empty.")
                Toast.makeText(applicationContext, "Address field is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            if (sendPointofcontact.isEmpty()) {
                println("Point of contact field is empty.")
                Toast.makeText(applicationContext, "Email is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            val intent = Intent(this, BusinessSignup2Activity::class.java)
            intent.putExtra("BusinessName",sendBusinessName)
            intent.putExtra("Address",sendAddress)
            intent.putExtra("Pointofcontact",sendPointofcontact)
            startActivity(intent)
        }
    }
}