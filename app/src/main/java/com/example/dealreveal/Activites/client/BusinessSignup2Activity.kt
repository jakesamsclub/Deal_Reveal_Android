package com.example.dealreveal.Activites.client

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

        val Phonenumber = findViewById<TextView>(R.id.editTextPhone5)
        val Websiteurl = findViewById<TextView>(R.id.editTextTextEmailAddress2)
        val Yelpurl = findViewById<TextView>(R.id.editTextTextEmailAddress9)
        val FBurl = findViewById<TextView>(R.id.editTextTextEmailAddress10)
        val Instaurl = findViewById<TextView>(R.id.editTextTextEmailAddress6)

       Phonenumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())


        val intent = intent
        BusinessName = intent.getStringExtra("BusinessName").toString()
        Address = intent.getStringExtra("Address").toString()
        Pointofcontact = intent.getStringExtra("Pointofcontact").toString()


        val TestURL = findViewById<Button>(R.id.button12)
        TestURL.setOnClickListener{
            var sendWebsiteurl = Websiteurl.text.toString()

            if (sendWebsiteurl.startsWith("https://") || sendWebsiteurl.startsWith("http://")) {
                val uri = Uri.parse(sendWebsiteurl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } else {
                println("invalid url"+sendWebsiteurl)
                Toast.makeText(applicationContext, "Invalid Url", Toast.LENGTH_SHORT).show()
            }

        }
        val TestYelpURL = findViewById<Button>(R.id.button17)
        TestYelpURL.setOnClickListener{
            var sendYelpurl = Yelpurl.text.toString()
            if (sendYelpurl.startsWith("https://") || sendYelpurl .startsWith("http://")) {
                val uri = Uri.parse(sendYelpurl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } else {
                println("invalid url"+sendYelpurl)
                Toast.makeText(applicationContext, "Invalid Url", Toast.LENGTH_SHORT).show()
            }

        }

        val TestFBURL = findViewById<Button>(R.id.button38)
        TestYelpURL.setOnClickListener{
            var sendFBurl = FBurl.text.toString()
            if (sendFBurl.startsWith("https://") || sendFBurl.startsWith("http://")) {
                val uri = Uri.parse(sendFBurl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } else {
                println("invalid url"+sendFBurl)
                Toast.makeText(applicationContext, "Invalid Url", Toast.LENGTH_SHORT).show()
            }

        }
        val TestInstaURL = findViewById<Button>(R.id.button39)
        TestYelpURL.setOnClickListener{
            var sendYelpurl = Yelpurl.text.toString()
            if (sendYelpurl.startsWith("https://") || sendYelpurl.startsWith("http://")) {
                val uri = Uri.parse(sendYelpurl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } else {
                println("invalid url"+sendYelpurl)
                Toast.makeText(applicationContext, "Invalid Url", Toast.LENGTH_SHORT).show()
            }

        }


        val nextbutton = findViewById<Button>(R.id.button18)
        nextbutton.setOnClickListener{


            var sendPhonenumber = Phonenumber.text.toString()
            var sendWebsiteurl = Websiteurl.text.toString()
            var sendYelpurl = Yelpurl.text.toString()
            var sendFBurl = FBurl.text.toString()
            var sendInstaBurl = Instaurl.text.toString()

            if (sendPhonenumber.length <14) {
                println("Phone Number must have 10 digits")
                Toast.makeText(applicationContext, "Phone Number must have 10 digits.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            if (sendPhonenumber.isEmpty()) {
                println("# is empty.")
                Toast.makeText(applicationContext, "Phone # field is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            val intent = Intent(this, BusinessSignup3Activity::class.java)
            intent.putExtra("Phonenumber",sendPhonenumber)
            intent.putExtra("Websiteurl",sendWebsiteurl)
            intent.putExtra("Yelpurl",sendYelpurl)
            intent.putExtra("BusinessName",BusinessName)
            intent.putExtra("Address",Address)
            intent.putExtra("Pointofcontact",Pointofcontact)
            intent.putExtra("Insta",sendInstaBurl)
            intent.putExtra("FB",sendFBurl)

            startActivity(intent)
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}