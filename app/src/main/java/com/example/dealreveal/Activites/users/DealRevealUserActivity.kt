package com.example.dealreveal.Activites.users

import android.Manifest
import android.Manifest.permission.CALL_PHONE
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.akexorcist.screenshotdetection.ScreenshotDetectionDelegate
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.shared.DealfeedbacktActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.service.AlarmService
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.Activites.usernotsignedin
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*



class DealRevealUserActivity : AppCompatActivity(), ScreenshotDetectionDelegate.ScreenshotDetectionListener, LocationListener   {

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
    var Instagram = ""
    var date = ""
    var latitude = ""
    var longitude = ""
    var resid = ""
    var MealImageUrl = ""
    var uid = ""
    var admincheck = ""
    var Reason = ""
    var currentuser = ""
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private val screenshotDetectionDelegate = ScreenshotDetectionDelegate(this, this)

    var today = Calendar.getInstance(Locale.getDefault())
    var cal = Calendar.getInstance(Locale.getDefault())
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH).toInt() + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val monthyear = (month.toString() + "-" + year.toString())

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 3009
    }


    private val mNotificationTime = Calendar.getInstance().timeInMillis + 20000 //Set after 5 seconds from the current time.
    private var mNotified = false
    lateinit var alarmService: AlarmService

    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_reveal_user)
        val intent = intent
        headerandbottom()
        newdeal()
        signedincheck()
        checkReadExternalStoragePermission()
        spin()
        getLocation()



    }
    fun signedincheck(){
        if (usernotsignedin == false)
        {
            currentuser = FirebaseAuth.getInstance().currentUser!!
                .uid
            viewanalytics()
        }
        if (usernotsignedin == true) {
            Log.d("ff", "YEs")
            currentuser =  Settings.Secure.getString(
                this.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            Log.d("UUID",currentuser)
            viewanalytics()
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

        val distancetitle = findViewById<TextView>(R.id.distance)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)

        //calculate distance
        val loc1 = Location("")
        loc1.latitude = latitude.toDouble()
        loc1.longitude = longitude.toDouble()

        val loc2 = Location("")
        loc2.latitude = userlat.toDouble()
        loc2.longitude = userlong.toDouble()


        val distanceInMeters = loc1.distanceTo(loc2)
        val distanceInMiles = distanceInMeters/1609.34
        val rounded = String.format("%.2f", distanceInMiles)
        distancetitle.text = rounded + " Miles Away"
    }

    private fun popup(){
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.changerangepopup, null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )

        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut

        }

        // Get the widgets reference from custom view
        val tv = view.findViewById<TextView>(R.id.textView58)
        val buttonPopup = view.findViewById<Button>(R.id.button_popup)
        val seek = view.findViewById<SeekBar>(R.id.seekBar)
        val close = view.findViewById<TextView>(R.id.text_view)


        tv.text = "This function can only be used if you create a account."
        close.text = ""
        seek.isVisible = false


        // Set click listener for popup window's text view
        tv.setOnClickListener {
            // Change the text color of popup window's text view
            tv.setTextColor(Color.BLACK)
        }

        // Set a click listener for popup's button widget
        buttonPopup.setOnClickListener {
            // Dismiss the popup window
            popupWindow.dismiss()
            val tv = view.findViewById<TextView>(R.id.textView58)
            tv.text = "This function can only be used if you create a account."

        }

        // Set a dismiss listener for popup window
        popupWindow.setOnDismissListener {

        }


        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(view as ViewGroup?)
        popupWindow.showAtLocation(
            view as ViewGroup?, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )

    }

   fun spin(){
       val rotate = RotateAnimation(
           0F,
           10000F,
           Animation.RELATIVE_TO_SELF,
           0.5f,
           Animation.RELATIVE_TO_SELF,
           0.5f
       )
       rotate.duration = 100000
       rotate.interpolator = LinearInterpolator()


   }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun newdeal() {

            Address = intent.getStringExtra("Address").toString()
            uid = intent.getStringExtra("dealuid").toString()
            Log.d("god", uid)
            CompanyURL = intent.getStringExtra("CompanyURL").toString()
            Dayofdealtext = intent.getStringExtra("DayofDeal").toString()
            Dealendtimetext = intent.getStringExtra("EndTime").toString()
            EndTimeNumber = intent.getIntExtra("EndTimeNumber",1)
            Facebook = intent.getStringExtra("Facebook").toString()
            Instagram = intent.getStringExtra("Insta").toString()
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
            Log.d("Insta",Instagram)
            //    avatar
            val Photo1 = findViewById<ImageView>(R.id.photo)
            Glide.with(this@DealRevealUserActivity)
                .load(MealImageUrl)
                .into(Photo1)

            setupheaders()


        }


    @SuppressLint("RemoteViewLayout")
    fun setupheaders() {
        val rotate = RotateAnimation(
            0F,
            360F,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 2000
        rotate.interpolator = LinearInterpolator()

        //info section
        val infoheadertitle = findViewById<TextView>(R.id.inforeveal)
        infoheadertitle.setPaintFlags(infoheadertitle.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        val DealTitle = findViewById<TextView>(R.id.textView52)
        DealTitle.setText(Title)
        val Dealprice = findViewById<TextView>(R.id.textView53)
        Dealprice.setText("$: " + Price)
        val DealDescription = findViewById<TextView>(R.id.textView54)
        DealDescription.setText(Description)

        val saveIcon = findViewById<ImageView>(R.id.save)
        saveIcon.startAnimation(rotate)
        saveIcon.setOnClickListener {
            if (usernotsignedin == false)
            {
                savedeal()
            }
            if (usernotsignedin == true) {
                popup()
            }
            Log.d("tAG", "save")

        }
        val alarm = findViewById<ImageView>(R.id.dealalarm)
        alarm.startAnimation(rotate)
        alarm.setOnClickListener {
            Toast.makeText(applicationContext,"You will be notified next time this deal is live. Beep Boop <3",Toast.LENGTH_LONG).show()
            Log.d("tAG", "alarm")
                alarmService = AlarmService(this)

                val MON = Dayofdealtext.contains("MON")
                val TUE = Dayofdealtext.contains("TUE")
                val WED = Dayofdealtext.contains("WED")
                val THU = Dayofdealtext.contains("THU")
                val FRI = Dayofdealtext.contains("FRI")
                val SAT = Dayofdealtext.contains("SAT")
                val SUN = Dayofdealtext.contains("SUN")

                //must set the current date so it overrides the set alarm date to the next time the deal is avaliable
                cal.setTimeInMillis(System.currentTimeMillis())
                //Dont delete me daddy^

                if (MON) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                    Log.d("Monday", Dayofdealtext)
                } else if (TUE) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
                    Log.d("TUESDAY", Dayofdealtext)
                } else if (WED) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
                } else if (THU) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
                } else if (FRI) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
                } else if (SAT) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                } else if (SUN) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                    Log.d("Sunday", Dayofdealtext)
                }

                var StartTime = StartTimeNumber.toString()
                var startmin = StartTime.toString()
                var startminSTR = startmin.takeLast(2)
                var starthour = StartTime.toString()
                var starthourSTR = starthour.dropLast(2)


                cal.set(Calendar.HOUR_OF_DAY, starthourSTR.toInt());
                cal.set(Calendar.MINUTE, startminSTR.toInt());
                cal.set(Calendar.SECOND, 0);
                Log.d("time", cal.getTimeInMillis().toString())

                var alarmdate = cal.getTimeInMillis()

                if (alarmdate.toInt() < today.getTimeInMillis().toInt()) {
                    alarmdate += 1000 * 60 * 60 * 24 * 7
                    Log.d("time", "we need to add 7 days" + alarmdate.toString())

                }

                var alarmid = uid.filter { it.isDigit() }
                Log.d("uid", alarmid.toString())


                alarmService.setReptetitveAlarm(alarmdate, 1000, Title)


            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid).update("Alarm", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written ")
            }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("Alarm", FieldValue.increment(1))
                        }
                    }

        }
        val yelp = findViewById<ImageView>(R.id.dealyelp)
        yelp.startAnimation(rotate)
        yelp.setOnClickListener {

            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid).update("Yelp", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("Yelp", FieldValue.increment(1))
                    }
                }
            var result = Yelp.contains(".")
            if (result){

                intent = Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(Yelp))
                startActivity(intent)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "We do not have a Yelp link on file for this company.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        val Insta = findViewById<ImageView>(R.id.Insta)
        Insta.startAnimation(rotate)
        Insta.setOnClickListener {

            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                .update("Insta", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("Insta", FieldValue.increment(1))
                    }
                }

            var result = Instagram.contains(".")
            if (result){

                intent = Intent(
                    android.content.Intent.ACTION_VIEW,
                    Uri.parse(Instagram)
                )
                startActivity(intent)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "We do not have a Insta link on file for this company.",
                    Toast.LENGTH_SHORT
                ).show()
            }



        }
        val FB = findViewById<ImageView>(R.id.Facebook)
        FB.startAnimation(rotate)
        FB.setOnClickListener {

            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                .update("FB", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("FB", FieldValue.increment(1))
                    }
                }

            var result = Facebook.contains(".")
            if (result){

                    intent = Intent(
                        android.content.Intent.ACTION_VIEW,
                        Uri.parse(Facebook)
                    )
                    startActivity(intent)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "We do not have a FB link on file for this company.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val website = findViewById<ImageView>(R.id.dealwebsite)
        website.startAnimation(rotate)
        website.setOnClickListener {

            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid).update("Website", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("Website", FieldValue.increment(1))
                    }
                }

            var result = CompanyURL.contains(".")
            if (result){

                intent = Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(CompanyURL))
                startActivity(intent)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "We do not have a website link on file for this company.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        val map = findViewById<ImageView>(R.id.dealmap)
        map.startAnimation(rotate)
        map.setOnClickListener {

            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                .update("Address", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("Address", FieldValue.increment(1))
                    }
                }

                    Log.d("tAG", "dealmap")
                    intent = Intent(
                        android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + userlat + "," + userlong + "&daddr=" + latitude + "," + longitude)
                    );
                    startActivity(intent)
        }

        val phone = findViewById<ImageView>(R.id.dealphone)
        phone.startAnimation(rotate)
        phone.setOnClickListener {


            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:$"+Phonenumber)
//            startActivity(intent)

            if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
                db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                    .update("Phone_Number", FieldValue.increment(1))
                    .addOnSuccessListener { documentReference ->
                        Log.d("TAG", "DocumentSnapshot written ")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error adding document", e)

                        if (e != null) {
                            print("we should make a document")

                            val dictfix = hashMapOf(
                                "UID" to uid,
                                "Avatar" to MealImageUrl,
                                "Title" to Title,
                            )
                            db.collection("Dealanalytics").document(resid).collection(monthyear)
                                .document(uid).set(dictfix)
                            db.collection("Dealanalytics").document(resid).collection(monthyear)
                                .document(uid).update("Phone_Number", FieldValue.increment(1))
                        }
                    }
            } else {
            Toast.makeText(
                applicationContext,
                "Enable call permissons first. Go into your phones setting select Deal Reveal and enable call permissons and reload the app.",
                Toast.LENGTH_SHORT
            ).show()
            }
        }

            //when section
            val currentDate: String =
                SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
            val WhenHeaderTitle = findViewById<TextView>(R.id.`when`)
            WhenHeaderTitle.setPaintFlags(WhenHeaderTitle.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            val DealDayofdealtext = findViewById<TextView>(R.id.Avaliableon)
            DealDayofdealtext.setText("Avaliable on " +Dayofdealtext)
            val Dealtime = findViewById<TextView>(R.id.Livefrom)
            Dealtime.setText("Live from "+Dealstarttimetext + " to " + Dealendtimetext)
            val Dealpostedon = findViewById<TextView>(R.id.Dealpostedon)
            Dealpostedon.setText("Deal posted on " +currentDate)

            //where section
            val whereheadertitle = findViewById<TextView>(R.id.where)
            whereheadertitle.setText("Where")
            whereheadertitle.setPaintFlags(whereheadertitle.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            val Companybutton = findViewById<TextView>(R.id.`company`)
            Companybutton.setText(RestaurantName)
            val distancetitle = findViewById<TextView>(R.id.distance)

            //Extra Info
            val extraheadertitle = findViewById<TextView>(R.id.extratitle)
            extraheadertitle.setPaintFlags(extraheadertitle.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            val ratebutton = findViewById<TextView>(R.id.`submitnewdealtitle`)
            ratebutton.setText("Rate Deal")
            ratebutton.setOnClickListener() {

                if (usernotsignedin == false)
                {
                    val intent = Intent(this, DealfeedbacktActivity::class.java)
                    intent.putExtra("dealtitletext", Title)
                    intent.putExtra("Businessnametext", RestaurantName)
                    intent.putExtra("Avatartext", MealImageUrl)
                    intent.putExtra("dealpricetext", Price)
                    intent.putExtra("resid", resid)
                    intent.putExtra("uid", uid)
                    intent.putExtra("reportkey", "reviewdeal")

                    startActivity(intent)
                }
                if (usernotsignedin == true) {
                    popup()
                }

            }
            val falsedealbutton = findViewById<TextView>(R.id.`Deletedealbutton`)
            falsedealbutton.setText("Report False Deal")
            falsedealbutton.setOnClickListener() {

                if (usernotsignedin == false)
                {
                    val intent = Intent(this, DealfeedbacktActivity::class.java)
                    intent.putExtra("dealtitletext", Title)
                    intent.putExtra("Businessnametext", RestaurantName)
                    intent.putExtra("Avatartext", MealImageUrl)
                    intent.putExtra("dealpricetext", Price)
                    intent.putExtra("resid", resid)
                    intent.putExtra("uid", uid)
                    intent.putExtra("reportkey", "reportkey")

                    startActivity(intent)
                }
                if (usernotsignedin == true) {
                    popup()
                }

            }


//        btnalarm.setOnClickListener {
//
//            Toast.makeText(
//                applicationContext,
//                "Reminder for when this deal is active has been set.",
//                Toast.LENGTH_LONG
//            ).show()
//
//
//        }
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
            intent.putExtra("page", "Deal Reveal")
            intent.putExtra(
                "desc",
                "* Here you can see all information we have on this deal. \n\n * You can see the image that represents this deal, the time its live, the day its live, the description, the price, and how it is from you. \n\n * You can also use the buttons to save this deal, set a reminder, see their yelp, insta,FB, or website, Rate the deal or submit a false posting report."
            )
            startActivity(intent)
        }
        title.setText("")
    }
    fun savedeal() {

        val docRef = db.collection("SavedDeals").document(currentuser).collection("Deals").document(uid)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                Log.d("nullcheck", documentSnapshot.data.toString())

                if (documentSnapshot.data.toString() == "null"){

                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH).toInt() + 1
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    val hour = c.get(Calendar.HOUR_OF_DAY)
                    val monthyear = (month.toString() + "-" + year.toString())


                    db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                        .update("Save_Count", FieldValue.increment(1))
                        .addOnSuccessListener { documentReference ->
                            Log.d("TAG", "DocumentSnapshot written ")
                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error adding document", e)

                            if (e != null) {
                                print("we should make a document")

                                val dictfix = hashMapOf(
                                    "UID" to uid,
                                    "Avatar" to MealImageUrl,
                                    "Title" to Title,
                                )
                                db.collection("Dealanalytics").document(resid).collection(monthyear)
                                    .document(uid).set(dictfix)
                                db.collection("Dealanalytics").document(resid).collection(monthyear)
                                    .document(uid).update("Save_Count", FieldValue.increment(1))
                            }
                        }
                    val currentDate: String =
                        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

                    val deal = hashMapOf(
                        "Address" to Address,
                        "CompanyURL" to CompanyURL,
                        "DayofDeal" to Dayofdealtext,
                        "EndTime" to Dealendtimetext,
                        "EndTimeNumber" to EndTimeNumber,
                        "Facebook" to Facebook,
                        "Insta" to Instagram,
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
                    db.collection("SavedDeals").document(currentuser).collection("Deals").document(uid).set(deal)
                        .addOnSuccessListener {
                            Log.d(

                                "NumberGenerated",
                                "DocumentSnapshot successfully written1! good job"
                            )
                            Toast.makeText(applicationContext, "Deal saved", Toast.LENGTH_LONG)
                                .show()
                        }
                        .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
                }

                if (documentSnapshot.data.toString() != "null") {
                    db.collection("SavedDeals").document(currentuser).collection("Deals")
                        .document(uid).delete()
                    Log.d("logout", "Logout")
                    Toast.makeText(applicationContext, "Deal Unsaved", Toast.LENGTH_LONG)
                        .show()
                }


            }
            .addOnFailureListener { exception ->

            }

    }
    private fun viewanalytics() {
        val loc1 = Location("")
        loc1.latitude = latitude.toDouble()
        loc1.longitude = longitude.toDouble()

        val loc2 = Location("")
        loc2.latitude = userlat.toDouble()
        loc2.longitude = userlong.toDouble()


        val distanceInMeters = loc1.distanceTo(loc2)
        val distanceInMiles = distanceInMeters / 1609.34
        val rounded = String.format("%.2f", distanceInMiles)

        Log.d("Distance", rounded)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH).toInt() + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val monthyear = (month.toString() + "-" + year.toString())


        var analyticsid = currentuser + "-" + day + "-" + month + "-" + year + "-" + hour

        val userinfoanlaytics = hashMapOf(
            "Distance" to rounded,
            "Latitude" to CompanyURL,
            "Longitude" to Dayofdealtext,
            "User" to currentuser,
        )

        db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
            .collection("Usersviewed").document(analyticsid).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists() == true) {
                    Log.d("Analy Exists", "yep it does")
                } else {
                    Log.d("Analy No Exists", "Nope it does not")
                    addspecificuserviewinfo()
                }
            }
    }
    private fun addspecificuserviewinfo(){
        val loc1 = Location("")
        loc1.latitude = latitude.toDouble()
        loc1.longitude = longitude.toDouble()

        val loc2 = Location("")
        loc2.latitude = userlat.toDouble()
        loc2.longitude = userlong.toDouble()


        val distanceInMeters = loc1.distanceTo(loc2)
        val distanceInMiles = distanceInMeters / 1609.34
        val rounded = String.format("%.2f", distanceInMiles).toDouble()

        Log.d("Distance", rounded.toString())

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH).toInt() + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val monthyear = (month.toString() + "-" + year.toString())

        var analyticsid = currentuser + "-" + day + "-" + month + "-" + year + "-" + hour

        val userinfoanlaytics = hashMapOf(
            "Distance" to rounded,
            "Latitude" to CompanyURL,
            "Longitude" to Dayofdealtext,
            "User" to currentuser,
        )

        if (rounded.toDouble()<=3){
            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                .update("View_Count_Less_Than_3_Miles", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("View_Count_3_To_20_Miles", FieldValue.increment(1))
                    }
                }
        }
        if (rounded.toDouble()> 3&& rounded.toInt()<=20){

            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                .update("View_Count_3_To_20_Miles", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("View_Count_3_To_20_Miles", FieldValue.increment(1))
                    }
                }
        }
        if (rounded.toDouble()>20){
            db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
                .update("View_Count_More_Than_20_Miles", FieldValue.increment(1))
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot written ")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)

                    if (e != null) {
                        print("we should make a document")

                        val dictfix = hashMapOf(
                            "UID" to uid,
                            "Avatar" to MealImageUrl,
                            "Title" to Title,
                        )
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).set(dictfix)
                        db.collection("Dealanalytics").document(resid).collection(monthyear)
                            .document(uid).update("View_Count_More_Than_20_Miles", FieldValue.increment(1))
                    }
                }
        }


        db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid).collection("Usersviewed").document(analyticsid).set(userinfoanlaytics)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written1! good job"
                )
            }

    }
    //screenshot stuff


    override fun onStart() {
        super.onStart()
        screenshotDetectionDelegate.startScreenshotDetection()
    }

    override fun onStop() {
        super.onStop()
        screenshotDetectionDelegate.stopScreenshotDetection()
    }

    override fun onScreenCaptured(path: String) {
        Log.d("screenshot", "onScreenCaptured: "+path)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH).toInt() + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val monthyear = (month.toString() + "-" + year.toString())

        db.collection("Dealanalytics").document(resid).collection(monthyear).document(uid)
            .update("Screenshot", FieldValue.increment(1))
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written ")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)

                if (e != null) {
                    print("we should make a document")

                    val dictfix = hashMapOf(
                        "UID" to uid,
                        "Avatar" to MealImageUrl,
                        "Title" to Title,
                    )
                    db.collection("Dealanalytics").document(resid).collection(monthyear)
                        .document(uid).set(dictfix)
                    db.collection("Dealanalytics").document(resid).collection(monthyear)
                        .document(uid).update("Screenshot", FieldValue.increment(1))
                }
            }
    }

    override fun onScreenCapturedWithDeniedPermission() {
        Log.d("screenshot", "onScreenCaptured: ")
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH).toInt() + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val monthyear = (month.toString() + "-" + year.toString())
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults.getOrNull(0) == PackageManager.PERMISSION_DENIED) {
                    showReadExternalStoragePermissionDeniedMessage()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestReadExternalStoragePermission()
        }
    }

    private fun requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION)
    }

    private fun showReadExternalStoragePermissionDeniedMessage() {

    }
}
