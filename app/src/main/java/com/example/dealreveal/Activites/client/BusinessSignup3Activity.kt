package com.example.dealreveal.Activites.client

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R


class BusinessSignup3Activity : AppCompatActivity() {
    var BusinessName = ""
    var Address = ""
    var Pointofcontact = ""
    var Phonenumber = ""
    var Websiteurl = ""
    var Yelpurl = ""
    var Instaurl = ""
    var FBurl = ""
    val emailPattern = """[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"""
    lateinit var filepath : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_signup3)

        val intent = intent
        BusinessName = intent.getStringExtra("BusinessName").toString()
        Address = intent.getStringExtra("Address").toString()
        Pointofcontact = intent.getStringExtra("Pointofcontact").toString()
        Phonenumber = intent.getStringExtra("Phonenumber").toString()
        Websiteurl = intent.getStringExtra("Websiteurl").toString()
        Yelpurl = intent.getStringExtra("Yelpurl").toString()
        FBurl = intent.getStringExtra("FB").toString()
        Instaurl = intent.getStringExtra("Insta").toString()

        Log.i("test", BusinessName)
        Log.i("test", Address)
        Log.i("test", Pointofcontact)
        Log.i("test", Phonenumber)
        Log.i("test", Websiteurl)
        Log.i("test", Yelpurl)
        Log.i("test", FBurl)
        Log.i("test", Instaurl)

        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)



        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","New Business Sign Up")
            intent.putExtra("desc","* You must enter the following fields to finish creating a new business account...\n\n    1) A image to represent your business 'company logo'. \n\n    2) A company email that will be used to login to the account. \n\n    3) A password for the account. \n\n * Once you have entered all the fields you can press Next")
            startActivity(intent)
        }
        title.setText("")

        val email = findViewById<TextView>(R.id.editTextTextEmailAddress4)
        val password = findViewById<TextView>(R.id.editTextTextPassword)

        val nextbutton = findViewById<Button>(R.id.button19)
        nextbutton.setOnClickListener {
            var sendemail = email.text.toString()
            var sendpassword = password.text.toString()

            if (sendemail.matches(emailPattern.toRegex())) {
                println("email is okay")
            } else {
                Toast.makeText(applicationContext, "Invalid email address",
                    Toast.LENGTH_SHORT).show()
                println("email is wrong")
                return@setOnClickListener
            }

            if (sendpassword.length <6) {
                println("Password must be 6 characters")
                Toast.makeText(applicationContext, "Password must be 6 characters.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            if (sendemail.isEmpty()) {
                println("Email is empty.")
                Toast.makeText(applicationContext, "Business email field is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}



            val intent = Intent(this, FinalBusinessSignupActivity::class.java)
            intent.putExtra("email", sendemail)
            intent.putExtra("password", sendpassword)
            intent.putExtra("Yelpurl", Yelpurl)
            intent.putExtra("BusinessName", BusinessName)
            intent.putExtra("Address", Address)
            intent.putExtra("Pointofcontact", Pointofcontact)
            intent.putExtra("Phonenumber",Phonenumber)
            intent.putExtra("Websiteurl",Websiteurl)
            intent.putExtra("avatar",filepath)
            intent.putExtra("Insta",Instaurl)
            intent.putExtra("FB",FBurl)



            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.button21)
        button.setOnClickListener {
            startFileChooder()
        }
        }

    private fun startFileChooder() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose Picture"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null) {
           val imageViewv = findViewById<ImageView>(R.id.imageView2)
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageViewv.setImageBitmap(bitmap)
        }
    }
}
