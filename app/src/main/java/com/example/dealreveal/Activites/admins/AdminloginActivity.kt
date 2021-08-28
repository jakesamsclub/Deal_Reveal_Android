package com.example.dealreveal.Activites.admins

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth


class AdminloginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)
        
        val verify = findViewById<Button>(R.id.button23)
        auth=FirebaseAuth.getInstance()


        verify.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextTextPersonName5).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword2).text.toString()
            Log.i("test", email)
            Log.i("test", password)

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("test", "createUserWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(applicationContext, AdminhomeActivity::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("test", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        var currentUser = auth.currentUser
        if(currentUser != null) {
            startActivity(Intent(applicationContext, AdminhomeActivity::class.java))
            finish()
        }



    }
}
