package com.example.dealreveal.Activites.shared


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.dealreveal.Activites.client.BusinessSigninActivity
import com.example.dealreveal.Activites.usernotsignedin
import com.example.dealreveal.Activites.users.LoginActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


var userlong = ""
var userlat = ""
class Startscreen : AppCompatActivity(), LocationListener {

    var clickCount = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    val db = FirebaseFirestore.getInstance()
    var locationcheck = true

    var simpleVideoView: VideoView? = null

    var mediaControls: MediaController? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startscreen)
        buttonsetup()
        video()
        window.decorView.post {
            getLocationsafetycheck()
        }
        //dont delete this is for resetting not signed in users
        usernotsignedin = false
        
        }

    fun video() {


        val buttonTxt = findViewById<TextView>(R.id.textView18)


        simpleVideoView = findViewById<View>(R.id.videoView) as VideoView
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        //starting the animation
        simpleVideoView!!.startAnimation(animation)
        buttonTxt.startAnimation(animation)

        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.simpleVideoView)
        }

        // set the media controller for video view
        simpleVideoView!!.setMediaController(mediaControls)

        // set the absolute path of the video file which is going to be played
        simpleVideoView!!.setVideoURI(
            Uri.parse(
                "android.resource://"
                        + packageName + "/" +  R.raw.magichat
            )
        )

        simpleVideoView!!.requestFocus()
        simpleVideoView!!.setMediaController(null)

        // starting the video
        simpleVideoView!!.start()

        // display a toast message
        // after the video is completed
        simpleVideoView!!.setOnCompletionListener {
            simpleVideoView!!.start()
        }
    }
    override fun onResume() {
        super.onResume()
        simpleVideoView = findViewById<View>(R.id.videoView) as VideoView
        if (simpleVideoView  != null) {
            simpleVideoView!!.start()
        }
    }


    fun buttonsetup(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener{

            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                val helpid = "user"
                val intent = Intent(this, HelpReminderActivity::class.java)
                intent.putExtra("HELPID",helpid)
                startActivity(intent)
            } else {
                checklocation()
            }
        }
//        val button1 = findViewById<Button>(R.id.button9)
//        button1.setOnClickListener{
//            FirebaseAuth.getInstance().signOut()
//        }
        val buttonTxt = findViewById<Button>(R.id.button0)
        buttonTxt.setOnClickListener{

            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
                val helpid = "user"
                val intent = Intent(this, HelpReminderActivity::class.java)
                intent.putExtra("HELPID",helpid)
                startActivity(intent)
            } else {
                checklocation()
            }
        }
        val businesssignupbutton = findViewById<Button>(R.id.button4)
        businesssignupbutton.setOnClickListener{
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                val helpid = "Business"
                val intent = Intent(this, HelpReminderActivity::class.java)
                intent.putExtra("HELPID",helpid)
                startActivity(intent)
            } else {
                checklocation()
            }


        }
        val businesssignin = findViewById<Button>(R.id.button3)
        businesssignin.setOnClickListener{
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                val intent = Intent(this, BusinessSigninActivity::class.java)
                startActivity(intent)
            } else {
                checklocation()
            }

        }


//        val hodor = findViewById<Button>(R.id.Hodor)
//        hodor.setOnClickListener {
//            clickCount++
//            if (clickCount == 5) {
//                val intent = Intent(this, HodorActivity::class.java)
//                startActivity(intent)
//
//            }
//        }
    }

     fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)

        }
         if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
             locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
         }
        else{
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


            tv.text = "This app requires location services to be enabled. We find deals based on your location. IF YOU HIT DENY, go to your phone settings, find the app Deal Reveal and make sure location settings is enabled. Reload the app once completed. If you accepted location services ignore this message. "
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

    }
    override fun onLocationChanged(location: Location) {
        userlong= location.longitude.toString()
        userlat = location.latitude.toString()
        Log.e("Object", userlong)
        Log.e("Object", userlat)
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
    fun getLocationsafetycheck(){
        if (userlat == ""){
            getLocation()
        }
    }
    fun checklocation(){

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


            tv.text = "Deal Reveal requires location services to be enabled. Go into your phone settings, find Deal Reveal and make sure you have granted location permissions. After this you will be able to utilize the app. "
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

    }




