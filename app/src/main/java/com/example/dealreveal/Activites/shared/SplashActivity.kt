package com.example.dealreveal.Activites.shared

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.client.InitalpostnewdealActivity
import com.example.dealreveal.Activites.users.DealRevealfilterActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SplashActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    var currentuser = "1"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth=FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if(currentUser != null) {
            currentuser = FirebaseAuth.getInstance().currentUser!!.uid

        }
        loggedinuser()

    }

    fun loggedinuser() {


        val docRef = db.collection("users1").document(currentuser)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                Log.d("nullcheck", documentSnapshot.data.toString())

                if (documentSnapshot.data.toString() != "null") {
                    val intent = Intent(this, DealRevealfilterActivity::class.java)
                    startActivity(intent)
                }
                if (documentSnapshot.data.toString() == "null") {
                    Log.d("nullcheck1", documentSnapshot.data.toString())
                    Log.d("nullcheck1", currentuser)

                    val docRef1 = db.collection("ClientsAccounts1").document(currentuser)
                    docRef1.get().addOnSuccessListener { documentSnapshot1 ->

                        if (documentSnapshot1.data.toString() != "null") {
                            val intent = Intent(this, InitalpostnewdealActivity::class.java)
                            startActivity(intent)
                        }
                        if (documentSnapshot1.data.toString() == "null") {
                            startActivity(Intent(this,Startscreen::class.java))
                            finish()
                        }

                    }
                }
            }
    }
}
