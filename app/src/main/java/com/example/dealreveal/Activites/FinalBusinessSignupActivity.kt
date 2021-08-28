package com.example.dealreveal.Activites

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class FinalBusinessSignupActivity : AppCompatActivity() {
    var BusinessName = ""
    var Address = ""
    var Pointofcontact = ""
    var Phonenumber = ""
    var Websiteurl = ""
    var Yelpurl = ""
    var email = ""
    var password = ""
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    lateinit var filepath : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_business_signup)
        headerandbottom()


        val intent = intent
        BusinessName = intent.getStringExtra("BusinessName").toString()
        Address = intent.getStringExtra("Address").toString()
        Pointofcontact = intent.getStringExtra("Pointofcontact").toString()
        Phonenumber = intent.getStringExtra("Phonenumber").toString()
        Websiteurl = intent.getStringExtra("Websiteurl").toString()
        Yelpurl = intent.getStringExtra("Yelpurl").toString()
        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        filepath = intent.getParcelableExtra<Uri>("avatar")!!

        auth1 = FirebaseAuth.getInstance()


        val nextbutton = findViewById<Button>(R.id.button20)
        nextbutton.setOnClickListener {

            if (filepath != null) {
                var pd = ProgressDialog(this)
                pd.setTitle("uploading")
                var id = UUID.randomUUID().toString()

                pd.show()

                var imageRef =
                    FirebaseStorage.getInstance().reference.child("approvalMeal1").child(id)

                imageRef.putFile(filepath).addOnSuccessListener { p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_LONG).show()
                    imageRef.downloadUrl.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            Log.i("test", downloadUri.toString())
                            val url = downloadUri.toString()
                            sumbitentry(url)
                        }
                    }
                        .addOnFailureListener {
                            // Handle any errors
                        }
                }

                    .addOnFailureListener { p0 ->
                        pd.dismiss()
                        Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()

                    }
                    .addOnProgressListener { p0 ->
                        var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                        pd.setMessage("uploading")

                    }
            }


        }
    }


    private fun sumbitentry(url: String) {
        var id = UUID.randomUUID().toString()
        val setuptime = findViewById<TextView>(R.id.editTextTime2)
        var sendsetuptime = setuptime.text.toString()

        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        val possible = hashMapOf(
            "Address" to Address,
            "CUID" to id,
            "Clientname" to BusinessName,
            "CompanyURL" to Websiteurl,
            "Date" to currentDate,
            "Facebook" to "",
            "MealImageUrl" to url,
            "Password" to password,
            "PhoneNumber" to Phonenumber,
            "PointOfContact" to Pointofcontact,
            "Yelp" to Yelpurl,
            "email" to email,
            "setuptime" to sendsetuptime,


            )

        db.collection("Possibleclient1").document(id)
            .set(possible)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
        startActivity(Intent(applicationContext, Startscreen::class.java))
        finish()
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
        title.setText("New Clients")
    }

    }
