package com.example.dealreveal.Activites.client

import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.admins.ApprovedealsActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*
import java.util.*

class PostNewDealInfoActivity : AppCompatActivity() {

    lateinit var filepath : Uri
    lateinit var startTimePicker: TimePickerDialog
    lateinit var endTimePicker: TimePickerDialog
    val startmcurrentTime = Calendar.getInstance()
    val endmcurrentTime = Calendar.getInstance()
    val starthour = startmcurrentTime.get(Calendar.HOUR_OF_DAY)
    val startminute = startmcurrentTime.get(Calendar.MINUTE)
    var startspecifictime = "1200"
    val endhour = endmcurrentTime.get(Calendar.HOUR_OF_DAY)
    val endminute = endmcurrentTime.get(Calendar.MINUTE)
    var endspecifictime = "1200"
    var daycat = ""
    var categorycat = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_new_deal_info)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        headerandbottom()
        spinnersetup()
        textsetup()

    }


    private val mTitleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val Titletext = findViewById<TextView>(R.id.Title)
            val titlelabeltext = findViewById<TextView>(R.id.titlelabel)
            if(Titletext.text.toString().trim().length < 51){
                titlelabeltext.text = (50 - Titletext.text.toString().trim().length).toString()+" characters remaining"

            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private val mTitleTextWatcherdesc = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val Descriptiontext = findViewById<TextView>(R.id.description)
            val Descriptionlabeltext = findViewById<TextView>(R.id.titlelabel2)
            if(Descriptiontext.text.toString().trim().length < 51){
                Descriptionlabeltext.text = (200 - Descriptiontext.text.toString().trim().length).toString()+" characters remaining"
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun textsetup(){
        val intent = intent
        filepath = intent.getParcelableExtra<Uri>("avatar")!!
        val Titletext = findViewById<TextView>(R.id.Title)
        val Descriptiontext = findViewById<TextView>(R.id.description)

        Titletext.addTextChangedListener(mTitleTextWatcher)
        Descriptiontext.addTextChangedListener(mTitleTextWatcherdesc)


        val nextbutton = findViewById<Button>(R.id.button33)
        nextbutton.setOnClickListener {

            spinnercheckcat()
        }
    }
    fun spinnersetup() {
        val daysspinner: Spinner = findViewById(R.id.dealdays)
        ArrayAdapter.createFromResource(
            this,
            R.array.Postdays,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            daysspinner.adapter = adapter
        }
        daysspinner.setSelection(0)

        val Categoryspinner: Spinner = findViewById(R.id.category)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.PostCategory,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            Categoryspinner.adapter = adapter
        }

        val  dealstart = findViewById<TextView>(R.id.dealstarttime)
        startTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                var format =""
                var fixedhour = 0
                var fixedmin = ""
                Log.d("dd", String.format(hourOfDay.toString()))
                when {
                    hourOfDay == 0 -> {
                        fixedhour = hourOfDay + 12
                        format = "AM"
                    }
                    hourOfDay == 12 -> {
                        fixedhour = 12
                        format = "PM"
                    }
                    hourOfDay > 12 -> {
                        fixedhour = hourOfDay - 12
                        format = "PM"
                    }
                    hourOfDay < 12 -> {
                        fixedhour = hourOfDay
                        format = "AM"
                    }
                    else -> format = "AM"

                }
                if (minute < 10){
                    fixedmin = "0"+minute.toString()
                }
                if (minute >= 10){
                    fixedmin = minute.toString()
                }
                dealstart.setText(fixedhour.toString() + ":" + fixedmin+" "+format)
                endspecifictime = (hourOfDay.toString()+fixedmin)
            }

        }, endhour, endminute, false)


        dealstart.setOnClickListener({ v ->
            startTimePicker.show()
        })

        val  dealend = findViewById<TextView>(R.id.dealendtime)
        endTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                var format =""
                var fixedhour = 0
                var fixedmin = ""
                Log.d("dd", String.format(hourOfDay.toString()))
                when {
                    hourOfDay == 0 -> {
                        fixedhour = hourOfDay + 12
                        format = "AM"
                    }
                    hourOfDay == 12 -> {
                        fixedhour = 12
                        format = "PM"
                    }
                    hourOfDay > 12 -> {
                        fixedhour = hourOfDay - 12
                        format = "PM"
                    }
                    hourOfDay < 12 -> {
                        fixedhour = hourOfDay
                        format = "AM"
                    }
                    else -> format = "AM"

                }
                if (minute < 10){
                    fixedmin = "0"+minute.toString()
                }
                if (minute >= 10){
                    fixedmin = minute.toString()
                }
                dealend.setText(fixedhour.toString() + ":" + fixedmin+" "+format)
                startspecifictime = (hourOfDay.toString()+fixedmin)
            }

        }, starthour, startminute, false)


        dealend.setOnClickListener({ v ->
            endTimePicker.show()
        })
    }

    fun spinnercheckday(){
        val daysspinner: Spinner = findViewById(R.id.dealdays)

        if (daysspinner.selectedItem.toString() == "Live on Monday"){
            daycat = "MON"
        }
        if (daysspinner.selectedItem.toString() == "Live on Tuesday"){
            daycat = "TUE"
        }
        if (daysspinner.selectedItem.toString() == "Live on Wedensday"){
            daycat = "WED"
        }
        if (daysspinner.selectedItem.toString() == "Live on Thursday"){
            daycat = "THU"
        }
        if (daysspinner.selectedItem.toString() == "Live on Friday"){
            daycat = "FRI"
        }
        if (daysspinner.selectedItem.toString() == "Live on Saturday"){
            daycat = "SAT"
        }
        if (daysspinner.selectedItem.toString() == "Live on Sunday"){
            daycat = "SUN"
        }
        if (daysspinner.selectedItem.toString() == "Live on Weekdays(M-F)"){
            daycat = "MON,TUE,WED,THU,FRI"
        }
        if (daysspinner.selectedItem.toString() == "Live on Weekend(S-S)"){
            daycat = "SAT,SUN"
        }
        if (daysspinner.selectedItem.toString() == "Live on All Days(M-S)"){
            daycat = "MON,TUE,WED,THU,FRI,SAT,SUN"
        }
        intent()

    }
    fun spinnercheckcat(){
        val Categoryspinner: Spinner = findViewById(R.id.category)

        if (Categoryspinner.selectedItem.toString() == "This is a Food Deal"){
            categorycat = "Food"
        }
        if (Categoryspinner.selectedItem.toString() == "This is a Beverage Deal"){
            categorycat = "Beverage"
        }
        if (Categoryspinner.selectedItem.toString() == "This is a Activity Deal"){
            categorycat = "Entertainment"
        }
        spinnercheckday()

    }
    fun intent(){
        val Titletext = findViewById<TextView>(R.id.Title)
        val Descriptiontext = findViewById<TextView>(R.id.description)
        val Pricetext = findViewById<TextView>(R.id.price)
        val Categoryspinner: Spinner = findViewById(R.id.category)
        val daysspinner: Spinner = findViewById(R.id.dealdays)
        val Dealstarttimetext = findViewById<TextView>(R.id.dealstarttime)
        val Dealendtimetext = findViewById<TextView>(R.id.dealendtime)

        var safety = ""

        if (Titletext.text .isEmpty()) {
            Toast.makeText(applicationContext, "Please add a title for this deal", Toast.LENGTH_LONG).show()
            return@intent
        }
        if (Pricetext.text .isEmpty()) {
            Toast.makeText(applicationContext, "Please add a price for this deal", Toast.LENGTH_LONG).show()
            return@intent
        }
        if (Dealstarttimetext.text .isEmpty()) {
            println("# is empty.")
            Toast.makeText(applicationContext, "Please add a start time for this deal", Toast.LENGTH_LONG).show()
            return@intent
        }
        if (Dealendtimetext.text .isEmpty()) {
            println("# is empty.")
            Toast.makeText(applicationContext, "Please add a end time for this deal", Toast.LENGTH_LONG).show()
            return@intent
        }
        if (Descriptiontext.text .isEmpty()) {
            Toast.makeText(applicationContext, "Please add a description for this deal", Toast.LENGTH_LONG).show()
            return@intent
        }

            val intent = Intent(this, DealRevealActivity::class.java)
            intent.putExtra("Title", Titletext.text.toString())
            intent.putExtra("Price", Pricetext.text.toString())
            intent.putExtra("Category", categorycat)
            intent.putExtra("Dayofdealtext", daycat)
            intent.putExtra("Dealstarttimetext", Dealstarttimetext.text.toString())
            intent.putExtra("Dealendtimetext", Dealendtimetext.text.toString())
            intent.putExtra("Description", Descriptiontext.text.toString())
            intent.putExtra("admincheck", "")
            intent.putExtra("startspecifictime", startspecifictime)
            intent.putExtra("endspecifictime", endspecifictime)
            intent.putExtra("avatar", filepath)

            startActivity(intent)

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
            intent.putExtra("page","New Deal")
            intent.putExtra("desc","* Here you can add the needed information for your new deal. Make sure each field is filled out. ")
            startActivity(intent)
        }
        title.setText("Post a New Deal")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.NewDeal

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.YourDeals -> {
                    val intent = Intent(this, ClientCollectionDealActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.UnderReview-> {
                    val intent = Intent(this, ClientCollectionDealActivity::class.java)
                    startActivity(intent);

                    true

                }
                R.id.NewDeal -> {

                    true
                }
                R.id.DealAnalytics -> {
                    val intent = Intent(this, ApprovedealsActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.Settings -> {
                    val intent = Intent(this,ClientsettingsActivity::class.java)
                    startActivity(intent);
                    true
                }
                else -> false
            }
        }
    }
}