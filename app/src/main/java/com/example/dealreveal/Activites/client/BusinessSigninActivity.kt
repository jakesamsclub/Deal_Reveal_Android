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

            if (email.text.isEmpty()) {
                Toast.makeText(applicationContext, "email field is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}
            if (password.text.isEmpty()) {
                Toast.makeText(applicationContext, "password field is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            val docRef = db.collection("ClientsAccounts").whereEqualTo("Clientemail",email.text.toString()).get()
                .addOnSuccessListener { documents ->
                    if (documents.documents.size == 0) {
                        Log.d("TA3G", email.text.toString())
                        Toast.makeText(
                            applicationContext,
                            "No account is associate with this email, please create a new account.",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        auth.signInWithEmailAndPassword(
                            email.text.toString(),
                            password.text.toString()
                        )
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {

                                    val intent = Intent(this, InitalpostnewdealActivity::class.java)
                                    startActivity(intent)

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(
                                        "test",
                                        "account credentials are not right, reset your password if forgotten. ",
                                        task.exception
                                    )
                                    Toast.makeText(
                                        applicationContext,
                                        "No account found with these credentials",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
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
            intent.putExtra("page","New Business Sign Up")
            intent.putExtra("desc","* Here a existing Business can sign into their account. \n\n * If you dont have a account you can tap Sign Up. \n\n * If you forgot your password, you can tap forgot password.")
            startActivity(intent)

        }
        title.setText("")
    }
}
