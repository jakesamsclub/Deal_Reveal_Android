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

        Log.i("test", BusinessName)
        Log.i("test", Address)
        Log.i("test", Pointofcontact)
        Log.i("test", Phonenumber)
        Log.i("test", Websiteurl)
        Log.i("test", Yelpurl)

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
        title.setText("")

        val email = findViewById<TextView>(R.id.editTextTextEmailAddress4)
        val password = findViewById<TextView>(R.id.editTextTextPassword)

        val nextbutton = findViewById<Button>(R.id.button19)
        nextbutton.setOnClickListener {
            var sendemail = email.text.toString()
            var sendpassword = password.text.toString()

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
