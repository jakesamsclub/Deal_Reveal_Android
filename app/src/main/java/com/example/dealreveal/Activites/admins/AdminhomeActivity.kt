package com.example.dealreveal.Activites.admins

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.AdminApproveClients
import com.example.dealreveal.R


class AdminhomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminhome)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);

        val Signin = findViewById<Button>(R.id.button24)
        Signin.setOnClickListener{
            val intent = Intent(this, AdminApproveClients::class.java)
            startActivity(intent)
        }
    }
}