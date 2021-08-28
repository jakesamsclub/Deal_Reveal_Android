package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.R
import java.text.SimpleDateFormat
import java.util.*

class RejectDealReasonActivity : AppCompatActivity() {

    var Title = ""
    var Price = ""
    var Category = ""
    var Dayofdealtext = ""
    var Dealstarttimetext = ""
    var Dealendtimetext = ""
    var Description = ""
    var Address = ""
    var CompanyURL = ""
    var EndTimeNumber = 0
    var Facebook = ""
    var Phonenumber = ""
    var RestaurantName = ""
    var StartTimeNumber = 0
    var Yelp = ""
    var date = ""
    var latitude = ""
    var longitude = ""
    var resid = ""
    var MealImageUrl = ""
    var uid = ""
    var Reason = ""
    var admincheck = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reject_deal_reason)
        headerandbottom()

        val intent = intent

        Address = intent.getStringExtra("Address").toString()
        CompanyURL = intent.getStringExtra("CompanyURL").toString()
        Dayofdealtext = intent.getStringExtra("DayofDeal").toString()
        Dealendtimetext = intent.getStringExtra("EndTime").toString()
        EndTimeNumber = intent.getIntExtra("EndTimeNumber", 1)
        Facebook = intent.getStringExtra("Facebook").toString()
        MealImageUrl = intent.getStringExtra("MealImageUrl").toString()
        Phonenumber = intent.getStringExtra("PhoneNumber").toString()
        RestaurantName = intent.getStringExtra("RestaurantName").toString()
        Dealstarttimetext = intent.getStringExtra("StartTime").toString()
        StartTimeNumber = intent.getIntExtra("StartTimeNumber", 1)
        Title = intent.getStringExtra("Title").toString()
        Yelp = intent.getStringExtra("Yelp").toString()
        Category = intent.getStringExtra("category").toString()
        date = intent.getStringExtra("date").toString()
        Description = intent.getStringExtra("description").toString()
        latitude = intent.getStringExtra("latitude").toString()
        longitude = intent.getStringExtra("longitude").toString()
        Price = intent.getStringExtra("price").toString()
        resid = intent.getStringExtra("resid").toString()
        uid = intent.getStringExtra("uid").toString()
        Reason = intent.getStringExtra("Reason").toString()
        admincheck = intent.getStringExtra("admincheck").toString()

        val rejecttextreason = findViewById<TextView>(R.id.rejecttext)
        if (admincheck == "Seewhy") {
            rejecttextreason.setText(Reason)
            rejecttextreason.setEnabled(false)

            db.collection("RejectedDeals1").document(resid).collection(resid).document(uid).delete()
                .addOnSuccessListener {
                    Log.d(
                        "NumberGenerated",
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w("NumberGenerated", "Error writing document", e)
                }
        }
        if (admincheck != "Seewhy") {
            val nextbutton = findViewById<Button>(R.id.button34)
            nextbutton.setOnClickListener {
                val currentDate: String =
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

                var sendreject = rejecttextreason.text.toString()

                val possible = hashMapOf(
                    "Address" to Address,
                    "CompanyURL" to CompanyURL,
                    "DayofDeal" to Dayofdealtext,
                    "EndTime" to Dealendtimetext,
                    "Facebook" to Facebook,
                    "MealImageUrl" to MealImageUrl,
                    "PhoneNumber" to Phonenumber,
                    "RestaurantName" to RestaurantName,
                    "StartTime" to Dealstarttimetext,
                    "Title" to Title,
                    "Yelp" to Yelp,
                    "category" to Category,
                    "DateRejected" to currentDate,
                    "description" to Description,
                    "latitude" to latitude,
                    "longitude" to longitude,
                    "price" to Price,
                    "resid" to resid,
                    "Reason" to sendreject,
                    "uid" to uid,
                )
                db.collection("RejectedDeals1").document(resid).collection(resid).document(uid)
                    .set(possible)
                    .addOnSuccessListener {
                        Log.d(
                            "NumberGenerated",
                            "DocumentSnapshot successfully written!"
                        )
                        Deletependingdeal()
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            "NumberGenerated",
                            "Error writing document",
                            e
                        )
                    }
            }
        }
    }

    private fun Deletependingdeal(){
        db.collection("ReviewMeals1").document("Deals").collection("Deals").document(uid).delete().addOnSuccessListener {
            Log.d(
                "NumberGenerated",
                "DocumentSnapshot successfully Deleted!"

            )
        }
        db.collection("ReviewMeals1").document(resid).collection(resid).document(uid).delete()
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully Deleted!"

                )
            }
    }
    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

//        leftIcon.setVisibility(View.INVISIBLE)
        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("Reject Deal")
    }
}