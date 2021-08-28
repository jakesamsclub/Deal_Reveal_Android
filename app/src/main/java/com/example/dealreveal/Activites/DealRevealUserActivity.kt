package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DealRevealUserActivity : AppCompatActivity() {

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
    var admincheck = ""
    var Reason = ""

    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_reveal_user)
        val intent = intent
        headerandbottom()
        newdeal()
    }

    fun newdeal() {

            Address = intent.getStringExtra("Address").toString()
            CompanyURL = intent.getStringExtra("CompanyURL").toString()
            Dayofdealtext = intent.getStringExtra("DayofDeal").toString()
            Dealendtimetext = intent.getStringExtra("EndTime").toString()
            EndTimeNumber = intent.getIntExtra("EndTimeNumber",1)
            Facebook = intent.getStringExtra("Facebook").toString()
            MealImageUrl = intent.getStringExtra("MealImageUrl").toString()
            Phonenumber = intent.getStringExtra("PhoneNumber").toString()
            RestaurantName = intent.getStringExtra("RestaurantName").toString()
            Dealstarttimetext = intent.getStringExtra("StartTime").toString()
            StartTimeNumber = intent.getIntExtra("StartTimeNumber",1)
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

            //    avatar
            val Photo1 = findViewById<ImageView>(R.id.photo)
            Glide.with(this@DealRevealUserActivity)
                .load(MealImageUrl)
                .into(Photo1)

            setupheaders()

//            val nextbutton = findViewById<Button>(R.id.submitnewdealtitle)
//            nextbutton.setOnClickListener {
//                Submitnewdeal()
//            }
//            val deletebutton = findViewById<Button>(R.id.Deletedealbutton)
//            deletebutton.setOnClickListener {
//                rejectdeal()
//            }
        }


    fun setupheaders(){


        //info section
        val infoheadertitle = findViewById<TextView>(R.id.info)
        val DealTitle = findViewById<TextView>(R.id.textView52)
        DealTitle.setText(Title)
        val Dealprice= findViewById<TextView>(R.id.textView53)
        Dealprice.setText(Price)
        val DealDescription = findViewById<TextView>(R.id.textView54)
        DealDescription.setText(Description)

        val saveIcon = findViewById<ImageView>(R.id.save)
        saveIcon.setOnClickListener {
            savedeal()
        }

        //when section
        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        val WhenHeaderTitle = findViewById<TextView>(R.id.`when`)
        val DealDayofdealtext = findViewById<TextView>(R.id.Avaliableon)
        DealDayofdealtext.setText(Dayofdealtext)
        val Dealtime = findViewById<TextView>(R.id.Livefrom)
        Dealtime.setText(Dealstarttimetext + " "+ Dealendtimetext)
        val Dealpostedon = findViewById<TextView>(R.id.Dealpostedon)
        Dealpostedon.setText(currentDate)

        //where section
        val whereheadertitle = findViewById<TextView>(R.id.where)
        whereheadertitle.setText("Where")
        val Companybutton = findViewById<TextView>(R.id.`company`)
        Companybutton.setText(RestaurantName)
        val distancetitle = findViewById<TextView>(R.id.distance)

        //Extra Info
        val extraheadertitle = findViewById<TextView>(R.id.extratitle)
        val submitnewdealtitle = findViewById<TextView>(R.id.`submitnewdealtitle`)
        submitnewdealtitle.setText("Submit New Deal")
        val Deletenewdealtitle = findViewById<TextView>(R.id.`Deletedealbutton`)
        Deletenewdealtitle.setText("Delete Deal")
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
        title.setText("Post a New Deal")
    }
    fun savedeal() {
        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid
        val deal = hashMapOf(
            "Address" to Address,
            "CompanyURL" to CompanyURL,
            "DayofDeal" to Dayofdealtext,
            "EndTime" to Dealendtimetext,
            "EndTimeNumber" to EndTimeNumber,
            "Facebook" to Facebook,
            "MealImageUrl" to MealImageUrl,
            "PhoneNumber" to Phonenumber,
            "RestaurantName" to RestaurantName,
            "StartTime" to Dealstarttimetext,
            "StartTimeNumber" to StartTimeNumber,
            "Title" to Title,
            "Yelp" to Yelp,
            "category" to Category,
            "date" to currentDate,
            "description" to Description,
            "latitude" to latitude,
            "longitude" to longitude,
            "price" to Price,
            "resid" to resid,
            "uid" to uid,

            )
        db.collection("SavedDeals1").document(currentuser).collection("Deals").document(uid).set(deal)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written1! good job"
                )
            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
    }
}