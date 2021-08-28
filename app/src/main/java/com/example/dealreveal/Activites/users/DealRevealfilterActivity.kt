package com.example.dealreveal.Activites.users

import android.Manifest
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dealreveal.Activites.CustomAdapter
import com.example.dealreveal.Activites.PendingapprovalActivity
import com.example.dealreveal.Activites.UserSavedDealsActivity
import com.example.dealreveal.Activites.admins.ApprovedealsActivity
import com.example.dealreveal.Activites.client.ClientsettingsActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.R
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryEventListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_deal_revealfilter.*
import java.util.*


class DealRevealfilterActivity : AppCompatActivity(), LocationListener {

    private lateinit var newRecyclerView: RecyclerView
    val keys = ArrayList<String>()
    val data = ArrayList<Pendingapproval>()
    val daytype: ArrayList<String> = ArrayList()
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    var distance = "15"
    var day = ""
    var Category = "Deals1"
    var time = ""
    var specifictime = "1200"
    var specifictimecheck = "no"
    var currenttime = ""
    var currenttimecheck= "no"
    lateinit var mTimePicker: TimePickerDialog
    val mcurrentTime = Calendar.getInstance()
    val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute = mcurrentTime.get(Calendar.MINUTE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_revealfilter)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        headerandbottom()
        spinnersetup()
        getLocationsafetycheck()

    }
    fun getLocationsafetycheck(){
        if (userlat == ""){
            getLocation()
        }
        if (userlat != ""){
            getrange()
        }
    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        userlong= location.longitude.toString()
        userlat = location.latitude.toString()
        setfiltervariables()
        //getUserdata()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun spinnersetup() {

        val title = findViewById<TextView>(R.id.attime)
        title.setText("12:00 PM")

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
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
                title.setText(fixedhour.toString() + ":" + fixedmin+" "+format)
                specifictime = (hourOfDay.toString()+fixedmin)
            }

        }, hour, minute, false)


        title.setOnClickListener({ v ->
            mTimePicker.show()
        })

        val distancespinner: Spinner = findViewById(R.id.distancedrop)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.distances,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            distancespinner.adapter = adapter
        }
        distancespinner.setSelection(14)

// Create an ArrayAdapter using the string array and a default spinner layout
        val daysspinner: Spinner = findViewById(R.id.daydrop)
        ArrayAdapter.createFromResource(
            this,
            R.array.days,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            daysspinner.adapter = adapter
        }

        val Categoryspinner: Spinner = findViewById(R.id.Categorydrop)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.Category,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            Categoryspinner.adapter = adapter
        }
        val Timespinner: Spinner = findViewById(R.id.timedrop)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.Time,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            Timespinner.adapter = adapter
        }
        Timespinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (Timespinner.selectedItem.toString() == "Specific Time"){
                    title.setVisibility(View.VISIBLE)
                    specifictimecheck = "yes"
                    currenttimecheck = "no"
                }
                if (Timespinner.selectedItem.toString() != "Specific Time"){
                    title.setVisibility(View.GONE)
                    specifictimecheck = "no"
                }
                if (Timespinner.selectedItem.toString() != "Current Time"){
                    currenttimecheck = "no"
                }
                if (Timespinner.selectedItem.toString() == "Current Time"){
                    currenttimecheck = "yes"
                    specifictimecheck = "no"
                    val c = Calendar.getInstance()
                    val hour = c.get(Calendar.HOUR_OF_DAY)
                    val minute = c.get(Calendar.MINUTE)
                    var fixedminute = ""

                    if (minute.toInt() <10)  {
                        fixedminute = "0"+minute.toString()
                    }
                    if (minute.toInt() >= 10)  {
                        fixedminute = minute.toString()
                    }
                    currenttime = (hour.toString()+fixedminute)
                    Log.d("currenttime", currenttime)
                }
            }

        }

        val filter = findViewById<Button>(R.id.filterbutton)
        filter.setOnClickListener {
            data.clear()
            keys.clear()
            daytype.clear()
//           newRecyclerView = findViewById(R.id.recyclerviewfilter)
//            newRecyclerView.layoutManager = LinearLayoutManager(this)
//            newRecyclerView.setHasFixedSize(true)
//            val adapter = CustomAdapter(data, userlat, userlong)
//            newRecyclerView.adapter = adapter
            setfiltervariables()
        }
    }

    fun setfiltervariables() {
        val Categoryspinner: Spinner = findViewById(R.id.Categorydrop)

        if (Categoryspinner.selectedItem.toString() == "All Deals") {
            Category = "Deals1"
        }
        if (Categoryspinner.selectedItem.toString() == "Food Deals") {
            Category = "Food1"
        }
        if (Categoryspinner.selectedItem.toString() == "Beverage Deals") {
            Category = "Beverage1"
        }
        if (Categoryspinner.selectedItem.toString() == "Activity Deals") {
            Category = "Entertainment1"
        }

        //Time variable
        val Timespinner: Spinner = findViewById(R.id.timedrop)
        if (Timespinner.selectedItem.toString() == "All Day") {
            time = "All Day"
        }
        if (Timespinner.selectedItem.toString() == "Current Time") {
            time = "Current Time"
        }
        if (Timespinner.selectedItem.toString() == "Specific Time") {
            time = "Specific Time"
        }

        //distance variable
        val distancespinner: Spinner = findViewById(R.id.distancedrop)
        var res = distancespinner.selectedItem.toString()
        var res1 = res.replace("[^0-9]".toRegex(), "")

        Log.d("tag1", res1)

        // day array setup
        val daysspinner: Spinner = findViewById(R.id.daydrop)
        if (daysspinner.selectedItem.toString() == "Monday") {
            daytype.clear()
            daytype.add("MON")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Tuesday") {
            daytype.clear()
            daytype.add("TUE")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Wedensday") {
            daytype.clear()
            daytype.add("WED")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Thursday") {
            daytype.clear()
            daytype.add("THU")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Friday") {
            daytype.clear()
            daytype.add("FRI")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Saturday") {
            daytype.clear()
            daytype.add("SAT")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Sunday") {
            daytype.clear()
            daytype.add("SUN")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Weekday") {
            daytype.clear()
            daytype.add("MON")
            daytype.add("TUE")
            daytype.add("WED")
            daytype.add("THU")
            daytype.add("FRI")
            daytype.add("MON,TUE,WED,THU,FRI")
        }
        if (daysspinner.selectedItem.toString() == "Weekend") {
            daytype.clear()
            daytype.add("SAT")
            daytype.add("SUN")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
        }
        if (daysspinner.selectedItem.toString() == "Today") {
            daytype.clear()
            var sCalendar = Calendar.getInstance();
            var dayLongName =
                sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            Log.d("tagday", dayLongName)
            if (dayLongName == "Monday") {
                daytype.clear()
                daytype.add("MON")
                daytype.add("MON,TUE,WED,THU,FRI")
                daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            }
            if (dayLongName == "Tuesday") {
                daytype.clear()
                daytype.add("TUE")
                daytype.add("MON,TUE,WED,THU,FRI")
                daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            }
            if (dayLongName == "Wedensday") {
                daytype.clear()
                daytype.add("WED")
                daytype.add("MON,TUE,WED,THU,FRI")
                daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            }
            if (dayLongName == "Thursday") {
                daytype.clear()
                daytype.add("THU")
                daytype.add("MON,TUE,WED,THU,FRI")
                daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            }
            if (dayLongName == "Friday") {
                daytype.clear()
                daytype.add("FRI")
                daytype.add("MON,TUE,WED,THU,FRI")
                daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            }
            if (dayLongName == "Saturday") {
                daytype.clear()
                daytype.add("SAT")
                daytype.add("MON,TUE,WED,THU,FRI")
                daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            }
            if (dayLongName == "Sunday") {
                daytype.clear()
                daytype.add("SUN")
                daytype.add("MON,TUE,WED,THU,FRI")
                daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")

            }
        }
        getrange()
    }

    fun getrange(){
        val ref =
            FirebaseDatabase.getInstance().getReference(Category)
        val geoFire = GeoFire(ref)

        val geoQuery = geoFire.queryAtLocation(GeoLocation(userlat.toDouble(), userlong.toDouble()), distance.toDouble())

        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                Log.i("test", String.format("Provider %s is within your search range [%f,%f]", key, location.latitude, location.longitude))
                keys.add(key)
            }

            override fun onKeyExited(key: String) {
                Log.i("TAG", String.format("Provider %s is no longer in the search area", key))
            }

            override fun onKeyMoved(key: String, location: GeoLocation) {
                Log.i("TAG", String.format("Provider %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude))
            }

            override fun onGeoQueryReady() {
                Log.i("TAG", "onGeoQueryReady")
                val Timespinner: Spinner = findViewById(R.id.timedrop)

                Log.i("specifictimecheckcehck", specifictimecheck)
                Log.i("currenttimecheck", currenttimecheck)
                if (specifictimecheck =="no"&& currenttimecheck =="no"){
                    getdeals()
                }
                if (specifictimecheck =="yes"&& currenttimecheck =="no"){
                    getdealswithpickabletime()
                }
                if (specifictimecheck =="no"&& currenttimecheck =="yes"){
                    getdealswithcurrenttime()
                }
            }

            override fun onGeoQueryError(error: DatabaseError) {
                Log.e("TAG", "error: " + error)
            }
        })
    }

    private fun getdeals() {

        Log.i("TAG","general ran")

            newRecyclerView = findViewById(R.id.recyclerviewfilter)
            newRecyclerView.layoutManager = LinearLayoutManager(this)
            newRecyclerView.setHasFixedSize(true)

            val db = FirebaseFirestore.getInstance()
            val currentuser = FirebaseAuth.getInstance().currentUser!!
                .uid

            var i = 0
            for (string in keys) {
                    Log.i("TAG9", string)// or your logic to catch the "B"

                Log.i("TAG", daytype.toString())
                            val docRef = db.collection("Deals1").whereEqualTo("uid",string).whereIn("DayofDeal",daytype)
                            docRef.get()
                                .addOnSuccessListener { documents ->

                                    for (document in documents.documents) {
                                        val myObject =
                                            document.toObject(Pendingapproval::class.java)

                                        Log.e("Object", myObject?.Address.toString())
                                        data.add(myObject!!)
                                        Log.d("TAG", data.size.toString())
                                        val adapter = CustomAdapter(data, userlat, userlong)
                                        newRecyclerView.adapter = adapter
                                    }
                                }


                                .addOnFailureListener { exception ->
                                    Log.d("test", "get failed with ", exception)
                                }

                            //check loop completion and update adapter screen
                            i++
                            if(i == (keys.size)){
                                val adapter = CustomAdapter(data,userlat,userlong)
                                newRecyclerView.adapter = adapter
                            }

                        }

        }

    private fun getdealswithpickabletime() {

        Log.i("TAG","specific ran")
        Log.d("time", specifictime)

        newRecyclerView = findViewById(R.id.recyclerviewfilter)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        var i = 0
        for (string in keys) {
            Log.i("TAG9", string)// or your logic to catch the "B"

            Log.i("TAG", daytype.toString())
            val docRef = db.collection("Deals1").whereEqualTo("uid",string).whereIn("DayofDeal",daytype)
            docRef.get()
                .addOnSuccessListener { documents ->

                    for (document in documents.documents) {
                        val myObject =
                            document.toObject(Pendingapproval::class.java)

                        Log.d("time", myObject?.StartTimeNumber.toString())

                        if (myObject?.StartTimeNumber!! <= specifictime.toInt()&&myObject?.EndTimeNumber!! >= specifictime.toInt()) {

                        data.add(myObject!!)
                        Log.d("TAG", data.size.toString())
                        val adapter = CustomAdapter(data, userlat, userlong)
                        newRecyclerView.adapter = adapter
                    }
                }
                }


                .addOnFailureListener { exception ->
                    Log.d("test", "get failed with ", exception)
                }

            //check loop completion and update adapter screen
            i++
            if(i == (keys.size)){
                val adapter = CustomAdapter(data,userlat,userlong)
                newRecyclerView.adapter = adapter
            }

        }

    }

    private fun getdealswithcurrenttime() {

        Log.d("currenttime", currenttime + "fcuk")
        newRecyclerView = findViewById(R.id.recyclerviewfilter)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        var i = 0
        for (string in keys) {
            Log.i("TAG9", string)// or your logic to catch the "B"

            Log.i("TAG", daytype.toString())
            val docRef = db.collection("Deals1").whereEqualTo("uid",string).whereIn("DayofDeal",daytype)
            docRef.get()
                .addOnSuccessListener { documents ->

                    for (document in documents.documents) {
                        val myObject =
                            document.toObject(Pendingapproval::class.java)

                        Log.d("time", myObject?.StartTimeNumber.toString())

                        if (myObject?.StartTimeNumber!! < currenttime.toInt()&&myObject?.EndTimeNumber!! > currenttime.toInt()) {

                            data.add(myObject!!)
                            Log.d("TAG", data.size.toString())
                            val adapter = CustomAdapter(data, userlat, userlong)
                            newRecyclerView.adapter = adapter
                        }
                    }
                }


                .addOnFailureListener { exception ->
                    Log.d("test", "get failed with ", exception)
                }

            //check loop completion and update adapter screen
            i++
            if(i == (keys.size)){
                val adapter = CustomAdapter(data,userlat,userlong)
                newRecyclerView.adapter = adapter
            }

        }

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
        title.setText("Deal Reveal")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.DealReveal

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.SavedDeals -> {
                    val intent = Intent(this, UserSavedDealsActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.RandomDeals -> {
                    val intent = Intent(this, PendingapprovalActivity::class.java)
                    startActivity(intent);

                    true

                }
                R.id.DealReveal -> {

                    true
                }
                R.id.BusinesssReveal -> {
                    val intent = Intent(this, ApprovedealsActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.Settings -> {
                    val intent = Intent(this, ClientsettingsActivity::class.java)
                    startActivity(intent);

                    true
                }
                else -> false
            }
        }
    }

    }
