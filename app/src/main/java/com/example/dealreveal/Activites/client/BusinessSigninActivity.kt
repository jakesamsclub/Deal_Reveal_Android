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
import com.google.firebase.firestore.FirebaseFirestore


class BusinessSigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

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
                        var auth1=FirebaseAuth.getInstance()

                        var currentUser = auth1.currentUser
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("tes", "signInWithEmail:success")
                        val docRef = db.collection("ClientsAccounts").document(currentUser.toString())
                        docRef.get()
                            .addOnSuccessListener { documentSnapshot ->
                                val intent = Intent(this, InitalpostnewdealActivity::class.java)
                                startActivity(intent)
                            }


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("test", "no account found ", task.exception)
                        Toast.makeText(applicationContext, "No account found with these credentials", Toast.LENGTH_LONG).show()
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
