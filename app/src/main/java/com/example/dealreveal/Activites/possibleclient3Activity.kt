package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.admins.AdminloginActivity
import com.example.dealreveal.R
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class possibleclient3Activity : AppCompatActivity() {
    var Address = ""
    var CUID = ""
    var Clientname = ""
    var CompanyURL = ""
    var Date = ""
    var Facebook = ""
    var MealImageUrl = ""
    var Password = ""
    var PhoneNumber = ""
    var PointOfContact = ""
    var Yelp = ""
    var email = ""
    var setuptime = ""
    var Longitude = ""
    var Latitude = ""
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_possibleclient3)
        headerandbottom()
        auth=FirebaseAuth.getInstance()

        val intent = intent
        Address = intent.getStringExtra("Address").toString()
        CUID = intent.getStringExtra("CUID").toString()
        Clientname = intent.getStringExtra("Clientname").toString()
        CompanyURL = intent.getStringExtra("CompanyURL").toString()
        Date = intent.getStringExtra("Date").toString()
        Facebook = intent.getStringExtra("Facebook").toString()
        MealImageUrl = intent.getStringExtra("MealImageUrl").toString()
        Password = intent.getStringExtra("Password").toString()
        PhoneNumber = intent.getStringExtra("PhoneNumber").toString()
        PointOfContact = intent.getStringExtra("PointOfContact").toString()
        Yelp = intent.getStringExtra("Yelp").toString()
        email = intent.getStringExtra("email").toString()
        setuptime = intent.getStringExtra("setuptime").toString()
        Latitude = intent.getStringExtra("Latitude").toString()
        Longitude = intent.getStringExtra("Longitude").toString()

        val avatar = findViewById<ImageView>(R.id.imageView5)
        Glide.with(this@possibleclient3Activity)
            .load(MealImageUrl)
            .into(avatar)

        val Businessnametext = findViewById<TextView>(R.id.textView31)
        Businessnametext.setText("Business Name: " + Clientname)
        val Addresstext = findViewById<TextView>(R.id.textView32)
        Addresstext.setText("Address: " + Address)
        val Phonetext = findViewById<TextView>(R.id.textView33)
        Phonetext.setText("Phone Number: " + PhoneNumber)
        val POCtext = findViewById<TextView>(R.id.textView34)
        POCtext.setText("PointOfContact: " + PointOfContact)
        val Emailtext = findViewById<TextView>(R.id.textView35)
        Emailtext.setText("Email: " + email)
        val passwordtext = findViewById<TextView>(R.id.textView36)
        passwordtext.setText("Password: " + Password)
        val facebooktext = findViewById<TextView>(R.id.textView37)
        facebooktext.setText("Facebook: " + Facebook)
        val yelptext = findViewById<TextView>(R.id.textView38)
        yelptext.setText("Yelp: " + Yelp)
        val businessurltext = findViewById<TextView>(R.id.textView39)
        businessurltext.setText("CompanyURL: " + CompanyURL)
        val latitudetext = findViewById<TextView>(R.id.textView40)
        latitudetext.setText("Latitude: " + Latitude)
        val longitudetext = findViewById<TextView>(R.id.textView41)
        longitudetext.setText("Longitude: " + Longitude)

        val nextbutton = findViewById<Button>(R.id.button27)
        nextbutton.setOnClickListener {

            auth.createUserWithEmailAndPassword(email, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("test", "createUserWithEmail:success")

                        var id = UUID.randomUUID().toString()
                        val currentuser = FirebaseAuth.getInstance().currentUser!!
                            .uid

                        val currentDate: String =
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                        val client = hashMapOf(
                            "Clientaddy" to Address,
                            "resid" to currentuser,
                            "Clientname" to Clientname,
                            "Clienturl" to CompanyURL,
                            "Date" to currentDate,
                            "Facebook" to "",
                            "avatar" to MealImageUrl,
                            "ClientPhone" to PhoneNumber,
                            "Restpointofcontact" to PointOfContact,
                            "Yelp" to Yelp,
                            "Clientemail" to email,
                            "Beverage" to 0,
                            "Entertainment" to 0,
                            "Food" to 0,
                            "Restlat" to Latitude,
                            "Restlong" to Longitude,
                            "uid" to id,
                            "email" to email


                            )

                        db.collection("ClientsAccounts1").document(currentuser)
                            .set(client)
                            .addOnSuccessListener {
                                Log.d(
                                    "NumberGenerated",
                                    "DocumentSnapshot successfully written!"

                                )
                                val ref =
                                    FirebaseDatabase.getInstance().getReference("GeoClients")
                                val geoFire = GeoFire(ref)

                                geoFire.setLocation(
                                    currentuser,
                                    GeoLocation(Latitude.toDouble(), Longitude.toDouble()),
                                    object : GeoFire.CompletionListener {
                                        fun onComplete(key: String?, error: FirebaseError?) {
                                            if (error != null) {
                                                System.err.println("There was an error saving the location to GeoFire: $error")
                                            } else {
                                                println("Location saved on server successfully!")

                                            }
                                        }

                                        override fun onComplete(
                                            key: String?,
                                            error: DatabaseError?

                                        ) {
                                            val intent = Intent(this@possibleclient3Activity, AdminloginActivity::class.java)
                                            startActivity(intent)
                                        }
                                    })
                            }
                            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("test", "createUserWithEmail:failure", task.exception)

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
        title.setText("New Clients")
    }
}