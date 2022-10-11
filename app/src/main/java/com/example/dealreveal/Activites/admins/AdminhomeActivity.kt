package com.example.dealreveal.Activites.admins

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.dealreveal.Activites.AdminApproveClients
import com.example.dealreveal.Activites.shared.Startscreen
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth


class AdminhomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminhome)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        headerandbottom()

        val Signin = findViewById<Button>(R.id.button24)
        Signin.setOnClickListener{
            val intent = Intent(this, AdminApproveClients::class.java)
            startActivity(intent)
        }
    }
    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

//        leftIcon.setVisibility(View.INVISIBLE)
        leftIcon.isVisible = false

        rightIcon.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext, Startscreen::class.java))
            finish()
        }
        title.setText("Admin Login")
    }
}