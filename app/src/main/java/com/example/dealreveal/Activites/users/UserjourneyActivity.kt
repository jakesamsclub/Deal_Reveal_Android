package com.example.dealreveal.Activites.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dealreveal.Activites.client.BusinessSignup1Activity
import com.example.dealreveal.Activites.usernotsignedin
import com.example.dealreveal.R

class UserjourneyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userjourney)

        val Skipped = findViewById<Button>(R.id.skip)
        val Signup = findViewById<Button>(R.id.Signup)
        val SignIn = findViewById<Button>(R.id.SignIn)

        Skipped.setOnClickListener {
            usernotsignedin = true
            startActivity(Intent(applicationContext, DealRevealfilterActivity::class.java))
            finish()
        }
        Signup.setOnClickListener {
            val intent = Intent(this, UserSignUpActivity::class.java)
            startActivity(intent)
        }
        SignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}