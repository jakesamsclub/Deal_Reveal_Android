package com.example.dealreveal.Activites.shared

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.dealreveal.Activites.PendingapprovalActivity
import com.example.dealreveal.Activites.db
import com.example.dealreveal.R

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

            val nextbutton = findViewById<Button>(R.id.button34)
            nextbutton.setOnClickListener {

            db.collection("RejectedDeals").document(resid).collection(resid).document(uid).delete()
                .addOnSuccessListener {
                    Log.d(
                        "NumberGenerated",
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w("NumberGenerated", "Error writing document", e)
                }
                val intent = Intent(this, PendingapprovalActivity::class.java)
                startActivity(intent);

                true
        }

        }

    }
    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)
        title.isVisible = false


//        leftIcon.setVisibility(View.INVISIBLE)
        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Deal Rejected")
            intent.putExtra("desc","* Here you can see why a deal reveal admin has rejected this deal. \n\n * You can apply the feedback and submit a new deal again. \n\n * You can delete this attempted post after reading the feedback. ")
            startActivity(intent)
        }
        title.setText("Reject Deal")
    }
}