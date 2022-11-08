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
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentuser = FirebaseAuth.getInstance().currentUser!!.uid
            Log.d("nullcheck", "User seems to be logged in")
            loggedinusercheck()
        }
        if (currentUser == null) {
            startActivity(Intent(this, Startscreen::class.java))
            finish()
        }


    }

    fun loggedinusercheck() {


        val docRef = db.collection("users").document(currentuser)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                Log.d("nullcheck fart", documentSnapshot.data.toString())

                if (documentSnapshot.data.toString() != "null") {
                    val intent = Intent(this, DealRevealfilterActivity::class.java)
                    startActivity(intent)
                }
                if (documentSnapshot.data.toString() == "null") {
                    val intent = Intent(this, DealRevealfilterActivity::class.java)
                    Log.d("nullcheck1", "shit aint found in user db")
                    val docRef1 = db.collection("ClientsAccounts").document(currentuser)
                    docRef1.get()
                        .addOnSuccessListener { documentSnapshot1 ->

                            if (documentSnapshot1.data.toString() != "null") {
                                val intent = Intent(this, InitalpostnewdealActivity::class.java)
                                startActivity(intent)
                            }
                            if (documentSnapshot1.data.toString() == "null") {
                                Log.d("nullcheck1", "shit aint found in client db")
                                FirebaseAuth.getInstance().signOut()
                                startActivity(Intent(this, Startscreen::class.java))
                                finish()
                            }
                        }
                        .addOnFailureListener {
                            Log.d("nullcheck1", "shit aint found in client db")
                            FirebaseAuth.getInstance().signOut()
                            startActivity(Intent(this, Startscreen::class.java))
                            finish()
                        }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                Log.d("nullcheck1", "shit aint found in user db")
                val docRef1 = db.collection("ClientsAccounts").document(currentuser)
                docRef1.get()
                    .addOnSuccessListener { documentSnapshot1 ->

                        if (documentSnapshot1.data.toString() != "null") {
                            val intent = Intent(this, InitalpostnewdealActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener {
                        Log.d("nullcheck1", "shit aint found in client db")
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(this, Startscreen::class.java))
                        finish()
                    }
            }
    }
}
