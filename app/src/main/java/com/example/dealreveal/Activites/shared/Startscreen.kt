package com.example.dealreveal.Activites.shared


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dealreveal.Activites.admins.HodorActivity
import com.example.dealreveal.Activites.client.BusinessSigninActivity
import com.example.dealreveal.Activites.client.InitalpostnewdealActivity
import com.example.dealreveal.Activites.users.DealRevealfilterActivity
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startscreen)
        buttonsetup()
        getLocationsafetycheck()

        }

    fun loggedinuser() {

        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        val docRef = db.collection("users1").document(currentuser)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                Log.d("nullcheck", documentSnapshot.data.toString())

                if (documentSnapshot.data.toString() != "null") {
                    val intent = Intent(this, DealRevealfilterActivity::class.java)
                    startActivity(intent)
                }
                if (documentSnapshot.data.toString() == "null") {
                    Log.d("nullcheck1", documentSnapshot.data.toString())
                    Log.d("nullcheck1", currentuser)

                    val docRef1 = db.collection("ClientsAccounts1").document(currentuser)
                    docRef1.get().addOnSuccessListener { documentSnapshot1 ->

                        if (documentSnapshot1.data.toString() != "null") {
                            val intent = Intent(this, InitalpostnewdealActivity::class.java)
                            startActivity(intent)
                        }

                    }
                }
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
            val helpid = "user"
            val intent = Intent(this, HelpReminderActivity::class.java)
            intent.putExtra("HELPID",helpid)
            startActivity(intent)
        }
        val button1 = findViewById<Button>(R.id.button9)
        button1.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
        }
        val buttonTxt = findViewById<Button>(R.id.button0)
        buttonTxt.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val businesssignupbutton = findViewById<Button>(R.id.button4)
        businesssignupbutton.setOnClickListener{
            val helpid = "Business"
            val intent = Intent(this, HelpReminderActivity::class.java)
            intent.putExtra("HELPID",helpid)
            startActivity(intent)
        }
        val businesssignin = findViewById<Button>(R.id.button3)
        businesssignin.setOnClickListener{
            val intent = Intent(this, BusinessSigninActivity::class.java)
            startActivity(intent)
        }


        val hodor = findViewById<Button>(R.id.Hodor)
        hodor.setOnClickListener {
            clickCount++
            if (clickCount == 5) {
                val intent = Intent(this, HodorActivity::class.java)
                startActivity(intent)

            }
        }
    }

     fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
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



    }
