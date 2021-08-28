package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.R

class UserSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)

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

        val editTextTextPersonName = findViewById<TextView>(R.id.editTextTextPersonName)
        val email = findViewById<TextView>(R.id.editTextTextEmailAddress)
        val birthdate = findViewById<TextView>(R.id.editTextDate)

        val button = findViewById<Button>(R.id.button5)
        button.setOnClickListener{
            var senduserName = editTextTextPersonName.text.toString()
            var sendemail = email.text.toString()
            var sendbirthdate = birthdate.text.toString()

            val intent = Intent(this, UsersignupsecondActivity::class.java)
            intent.putExtra("userName",senduserName)
            intent.putExtra("email",sendemail)
            intent.putExtra("birthdate",sendbirthdate)
            startActivity(intent)
        }

    }
}