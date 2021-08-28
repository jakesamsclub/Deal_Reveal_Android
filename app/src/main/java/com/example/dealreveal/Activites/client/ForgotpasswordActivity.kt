package com.example.dealreveal.Activites.client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth

class ForgotpasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)
        headerandbottom()
        auth = FirebaseAuth.getInstance()

        val email = findViewById<TextView>(R.id.editTextTextPersonName16)

        val businesssigninbutton = findViewById<Button>(R.id.button31)
        businesssigninbutton.setOnClickListener{
            auth.sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("test", "resetEmail:success")
                        val intent = Intent(this, BusinessSigninActivity::class.java)
                        startActivity(intent)
                        val text = ("Reset Password Sent to "+ email.text.toString())
                        val duration = Toast.LENGTH_LONG

                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("test", "resetWithEmail:failure", task.exception)
                        val duration = Toast.LENGTH_LONG

                        val toast = Toast.makeText(applicationContext, "No account with this email found", duration)
                        toast.show()
                    }
                }
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
        title.setText("")
    }
}