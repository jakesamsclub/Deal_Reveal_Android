package com.example.dealreveal.Activites.client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.ForgotpasswordActivity
import com.example.dealreveal.Activites.HelpOverviewActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth


class BusinessSigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_signin)
        headerandbottom()
        auth=FirebaseAuth.getInstance()

            val currentUser = auth.currentUser
            if(currentUser != null){
                val intent = Intent(this, InitalpostnewdealActivity::class.java)
                startActivity(intent)
            }

        val email = findViewById<TextView>(R.id.editTextTextPersonName15)
        val password = findViewById<TextView>(R.id.editTextTextPassword3)

        val businesssigninbutton = findViewById<Button>(R.id.button28)
        businesssigninbutton.setOnClickListener{
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("test", "signInWithEmail:success")
                        val intent = Intent(this, InitalpostnewdealActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("test", "signInWithEmail:failure", task.exception)
                    }
                }

        }
        val businesssignup = findViewById<Button>(R.id.button29)
        businesssignup.setOnClickListener {
            val intent = Intent(this, BusinessSignup1Activity::class.java)
            startActivity(intent)
        }
        val forgotpassword = findViewById<Button>(R.id.button30)
        forgotpassword.setOnClickListener {
            val intent = Intent(this, ForgotpasswordActivity::class.java)
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
        title.setText("")
    }
}
