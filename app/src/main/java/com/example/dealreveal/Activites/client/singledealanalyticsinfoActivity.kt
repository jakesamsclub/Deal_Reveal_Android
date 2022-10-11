package com.example.dealreveal.Activites.client

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import kotlinx.android.synthetic.main.dealanalyticbreakdown.*

class singledealanalyticsinfoActivity : AppCompatActivity() {

    var Avatar = ""
    var Title = ""
    var UID = ""
    var filterdatesearch = ""
    var Addresscount = 0
    var Alarm = 0
    var Facebook = 0
    var Feedbackcount = 0
    var SaveCount = 0
    var Screenshotcount = 0
    var View3to20 = 0
    var Viewless3 = 0
    var View20more = 0
    var Website = 0
    var Yelpcount = 0
    var PhoneNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singledealanalyticsinfo)
        headerandbottom()
        intentget()
    }
    fun intentget(){
        val intent = intent
        Avatar = intent.getStringExtra("Avatar").toString()
        Title = intent.getStringExtra("Title").toString()
        UID = intent.getStringExtra("UID").toString()
        filterdatesearch = intent.getStringExtra("filterdatesearch").toString()
        Addresscount = intent.getIntExtra("Address",0)
        Alarm = intent.getIntExtra("Alarm",0)
        Facebook = intent.getIntExtra("Facebook",0)
        Feedbackcount = intent.getIntExtra("Feedback",0)
        SaveCount = intent.getIntExtra("Save_Count",0)
        Screenshotcount = intent.getIntExtra("Screenshot",0)
        View3to20 = intent.getIntExtra("View_Count_3_To_20_Miles",0)
        Viewless3 = intent.getIntExtra("View_Count_Less_Than_3_Miles",0)
        View20more = intent.getIntExtra("View_Count_More_Than_20_Miles",0)
        Website = intent.getIntExtra("Website",0)
        Yelpcount = intent.getIntExtra("Yelp",0)
        PhoneNumber = intent.getIntExtra("Phone_Number",0)

        //Extra Info
        val dealinfo = findViewById<TextView>(R.id.dealanalinfo)
        dealinfo.setText(Title)
        val dealtitle = findViewById<TextView>(R.id.dealanaltitle)
        dealtitle.setText("analytics for "+ filterdatesearch)
        val dealphoto = findViewById<ImageView>(R.id.dealanalphoto)
        Glide.with(this@singledealanalyticsinfoActivity)
            .load(Avatar)
            .into(dealphoto)
        val totalview = findViewById<TextView>(R.id.Totalviewlabel)
        var total = View3to20+Viewless3+View20more
        totalview.setText(total.toString())
        val viewunder3 = findViewById<TextView>(R.id.viewunder3)
        viewunder3.setText(Viewless3.toString())
        val view3to20 = findViewById<TextView>(R.id.view3to20)
        view3to20.setText(View3to20.toString())
        val viewover20 = findViewById<TextView>(R.id.viewover20)
        viewover20.setText(View20more.toString())
        val website = findViewById<TextView>(R.id.website)
        website.setText(Website.toString())
        val phone = findViewById<TextView>(R.id.Phone)
        phone.setText(PhoneNumber.toString())
        val Yelp = findViewById<TextView>(R.id.Yelp)
        Yelp.setText(Yelpcount.toString())
        val Screenshot = findViewById<TextView>(R.id.Screenshot)
        Screenshot.setText(Screenshotcount.toString())
        val Saved = findViewById<TextView>(R.id.Saved)
        Saved.setText(SaveCount.toString())
        val Reminder = findViewById<TextView>(R.id.Reminder)
        Reminder.setText(Alarm.toString())
        val Address = findViewById<TextView>(R.id.Address)
        Address.setText(Addresscount.toString())
        val Feedback = findViewById<TextView>(R.id.Feedback)
        Feedback.setText(Feedbackcount.toString())
        val Dealcost = findViewById<TextView>(R.id.Dealcost)
        var totalcost = 0.0
        totalcost = ((Viewless3 * 0.03)+(View3to20 * 0.01))
        val rounded = String.format("%.2f", totalcost)
        Dealcost.setText("Current cost of this deal is: $"+ rounded.toString())
        val Costbreakdown = findViewById<TextView>(R.id.Costbreakdown)
        Costbreakdown.setText("See how cost is calculated here...")
        Costbreakdown.setOnClickListener(){
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        Costbreakdown.setTextColor((Color.BLUE))


    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

//        leftIcon.setVisibility(View.INVISIBLE)
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
//        title.setText("Post a New Deal")
        leftIcon.setOnClickListener {
            finish()
        }

    }
}