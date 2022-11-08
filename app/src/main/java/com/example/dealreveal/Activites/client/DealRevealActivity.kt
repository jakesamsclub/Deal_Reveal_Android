package com.example.dealreveal.Activites.client

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.PendingapprovalActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.RejectDealReasonActivity
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class DealRevealActivity : AppCompatActivity(), LocationListener {

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
    var Insta = ""
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
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2


    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_reveal)
        val intent = intent
        admincheck = intent.getStringExtra("admincheck").toString()
        headerandbottom()
        Log.d("client", admincheck)
        getLocation()
        deal()

    }

    fun deal() {
        //this is for newdeals posted by client
        if (admincheck == "") {
            lateinit var filepath : Uri
            val deletebutton = findViewById<Button>(R.id.Deletedealbutton)
            deletebutton.isVisible = false

        filepath = intent.getParcelableExtra<Uri>("avatar")!!
        Price = intent.getStringExtra("Price").toString()
        Category = intent.getStringExtra("Category").toString()
        Dayofdealtext = intent.getStringExtra("Dayofdealtext").toString()
        Dealstarttimetext = intent.getStringExtra("Dealstarttimetext").toString()
        Dealendtimetext = intent.getStringExtra("Dealendtimetext").toString()
        Description = intent.getStringExtra("Description").toString()
            StartTimeNumber = intent.getStringExtra("startspecifictime")!!.toInt()
            EndTimeNumber = intent.getStringExtra("endspecifictime")!!.toInt()
        Title = intent.getStringExtra("Title").toString()
            Log.d("client", StartTimeNumber.toString())
            Log.d("client", EndTimeNumber.toString())
            Log.d("client", Title.toString())

            //    avatar
            val Photo = findViewById<ImageView>(R.id.photo)
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            Photo.setImageBitmap(bitmap)

            val currentuser = FirebaseAuth.getInstance().currentUser!!
                .uid
            Log.d("client", currentuser)
            val docRef = db.collection("ClientsAccounts").document(currentuser)
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val ClientInfo = documentSnapshot.toObject(Client::class.java)

                    Log.d("client", "DocumentSnapshot data: ${ClientInfo?.Clientname}")
                    Address = ClientInfo?.Clientaddy.toString()
                    CompanyURL = ClientInfo?.Clienturl.toString()
                    Facebook = ClientInfo?.Facebook.toString()
                    Insta = ClientInfo?.Insta.toString()
                    Phonenumber = ClientInfo?.ClientPhone.toString()
                    RestaurantName = ClientInfo?.Clientname.toString()
                    Yelp = ClientInfo?.Yelp.toString()
                    latitude = ClientInfo?.Restlat.toString()
                    longitude = ClientInfo?.Restlong.toString()
                    resid = ClientInfo?.resid.toString()
                    setupheaders()


                }
                .addOnFailureListener { exception ->
                    Log.d("test", "get failed with ", exception)
                }

            val nextbutton = findViewById<Button>(R.id.submitnewdealtitle)
            nextbutton.setOnClickListener {

                if (filepath != null) {
                    var pd = ProgressDialog(this)
                    pd.setTitle("uploading")
                    var id = UUID.randomUUID().toString()

                    pd.show()

                    var imageRef =
                        FirebaseStorage.getInstance().reference.child("approvalDeal").child(id)

                    imageRef.putFile(filepath).addOnSuccessListener { p0 ->
                        pd.dismiss()
                        Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_LONG)
                            .show()
                        imageRef.downloadUrl.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val downloadUri = task.result
                                Log.i("test", downloadUri.toString())
                                val url = downloadUri.toString()
                                sumbitpossibledeal(url)
                            }
                        }
                            .addOnFailureListener {
                                // Handle any errors
                            }
                    }

                        .addOnFailureListener { p0 ->
                            pd.dismiss()
                            Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()

                        }
                        .addOnProgressListener { p0 ->
                            var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                            pd.setMessage("uploading")

                        }
                }
            }
        }

        if (admincheck == "Review"){

            Address = intent.getStringExtra("Address").toString()
            CompanyURL = intent.getStringExtra("CompanyURL").toString()
            Dayofdealtext = intent.getStringExtra("DayofDeal").toString()
            Dealendtimetext = intent.getStringExtra("EndTime").toString()
            Facebook = intent.getStringExtra("Facebook").toString()
            MealImageUrl = intent.getStringExtra("MealImageUrl").toString()
            Phonenumber = intent.getStringExtra("PhoneNumber").toString()
            RestaurantName = intent.getStringExtra("RestaurantName").toString()
            Dealstarttimetext = intent.getStringExtra("StartTime").toString()
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
            Insta = intent.getStringExtra("Insta").toString()
            Reason = intent.getStringExtra("reason").toString()

            //    avatar
            val Photo1 = findViewById<ImageView>(R.id.photo)
            Glide.with(this@DealRevealActivity)
                .load(MealImageUrl)
                .into(Photo1)

            val nextbutton = findViewById<Button>(R.id.submitnewdealtitle)
            nextbutton.setText("Delete Deal")
            nextbutton.setOnClickListener {
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

                builder.setTitle("Confirm")
                builder.setMessage("Are you sure you want to delete this live deal?")

                builder.setPositiveButton(
                    "YES",
                    DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                        dialog.dismiss()

                        Toast.makeText(
                            applicationContext,
                            "Pending deal deleted!",
                            Toast.LENGTH_SHORT
                        ).show()

                        db.collection("Deals").document(uid).delete().addOnSuccessListener {
                            Log.d(
                                "NumberGenerated",
                                "DocumentSnapshot successfully Deleted!"

                            )
                        }
                    })

                builder.setNegativeButton(
                    "NO",
                    DialogInterface.OnClickListener { dialog, which -> // Do nothing
                        dialog.dismiss()
                    })

                val alert: android.app.AlertDialog? = builder.create()
                alert!!.show()

            }
            val deletebutton = findViewById<Button>(R.id.Deletedealbutton)
            deletebutton.isVisible = false


            setupheaders()
        }


        if (admincheck == "pendingdeal"){

            Address = intent.getStringExtra("Address").toString()
            CompanyURL = intent.getStringExtra("CompanyURL").toString()
            Dayofdealtext = intent.getStringExtra("DayofDeal").toString()
            Dealendtimetext = intent.getStringExtra("EndTime").toString()
            Facebook = intent.getStringExtra("Facebook").toString()
            Insta = intent.getStringExtra("Insta").toString()
            MealImageUrl = intent.getStringExtra("MealImageUrl").toString()
            Phonenumber = intent.getStringExtra("PhoneNumber").toString()
            RestaurantName = intent.getStringExtra("RestaurantName").toString()
            Dealstarttimetext = intent.getStringExtra("StartTime").toString()
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
            Reason = intent.getStringExtra("reason").toString()

            //    avatar
            val Photo1 = findViewById<ImageView>(R.id.photo)
            Glide.with(this@DealRevealActivity)
                .load(MealImageUrl)
                .into(Photo1)

            val nextbutton = findViewById<Button>(R.id.submitnewdealtitle)
            nextbutton.setText("Delete Pending Deal")
            nextbutton.setOnClickListener {
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

                builder.setTitle("Confirm")
                builder.setMessage("Are you sure you want to delete this pending deal?")

                builder.setPositiveButton(
                    "YES",
                    DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                        dialog.dismiss()

                        Deletependingdeal()
                    })

                builder.setNegativeButton(
                    "NO",
                    DialogInterface.OnClickListener { dialog, which -> // Do nothing
                        dialog.dismiss()
                    })

                val alert: android.app.AlertDialog? = builder.create()
                alert!!.show()

            }
            val deletebutton = findViewById<Button>(R.id.Deletedealbutton)
            deletebutton.isVisible = false


            setupheaders()
        }


        if (admincheck == "rejecteddeal"){

            Address = intent.getStringExtra("Address").toString()
            CompanyURL = intent.getStringExtra("CompanyURL").toString()
            Dayofdealtext = intent.getStringExtra("DayofDeal").toString()
            Dealendtimetext = intent.getStringExtra("EndTime").toString()
            Facebook = intent.getStringExtra("Facebook").toString()
            Insta = intent.getStringExtra("Insta").toString()
            MealImageUrl = intent.getStringExtra("MealImageUrl").toString()
            Phonenumber = intent.getStringExtra("PhoneNumber").toString()
            RestaurantName = intent.getStringExtra("RestaurantName").toString()
            Dealstarttimetext = intent.getStringExtra("StartTime").toString()
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
            Reason = intent.getStringExtra("reason").toString()

            //    avatar
            val Photo1 = findViewById<ImageView>(R.id.photo)
            Glide.with(this@DealRevealActivity)
                .load(MealImageUrl)
                .into(Photo1)

            setupheaders()

            val nextbutton = findViewById<Button>(R.id.submitnewdealtitle)
            nextbutton.setText("Delete Rejected Deal")
            nextbutton.setOnClickListener {
                val intent = Intent(this, RejectDealReasonActivity::class.java)
                intent.putExtra("Address", Address)
                intent.putExtra("CompanyURL", CompanyURL)
                intent.putExtra("DayofDeal", Dayofdealtext)
                intent.putExtra("EndTime", Dealendtimetext)
                intent.putExtra("EndTimeNumber", EndTimeNumber)
                intent.putExtra("Facebook", Facebook)
                intent.putExtra("MealImageUrl", MealImageUrl)
                intent.putExtra("PhoneNumber", Phonenumber)
                intent.putExtra("RestaurantName", RestaurantName)
                intent.putExtra("StartTime", Dealstarttimetext )
                intent.putExtra("StartTimeNumber", StartTimeNumber)
                intent.putExtra("Title", Title)
                intent.putExtra("Yelp", Yelp)
                intent.putExtra("category", Category)
                intent.putExtra("date", date)
                intent.putExtra("description", Description)
                intent.putExtra("latitude", latitude)
                intent.putExtra("longitude", longitude)
                intent.putExtra("price", Price)
                intent.putExtra("resid", resid)
                intent.putExtra("uid", uid)
                intent.putExtra("Reason", Reason)
                intent.putExtra("admincheck","Seewhy")

                startActivity(intent)
            }
            val deletebutton = findViewById<Button>(R.id.Deletedealbutton)
            deletebutton.isVisible = false
        }
    }

    private fun Deletependingdeal(){
        Toast.makeText(
            applicationContext,
            "Pending deal deleted!",
            Toast.LENGTH_SHORT
        ).show()

        db.collection("ReviewMeals").document(uid).delete().addOnSuccessListener {
            Log.d(
                "NumberGenerated",
                "DocumentSnapshot successfully Deleted!"

            )
        }

        val intent = Intent(this, PendingapprovalActivity::class.java)
        startActivity(intent);

    }



private fun sumbitpossibledeal(url: String) {
    var id = UUID.randomUUID().toString()


    val currentDate: String =
        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

    val possible = hashMapOf(
        "Address" to Address,
         "CompanyURL" to CompanyURL,
        "DayofDeal" to Dayofdealtext,
       "EndTime" to Dealendtimetext ,
        "EndTimeNumber" to EndTimeNumber,
        "Facebook" to Facebook,
        "Insta" to Insta,
        "MealImageUrl" to url,
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
        "uid" to id ,



        )
    db.collection("ReviewMeals").document(id).set(possible)
        .addOnSuccessListener {
            Log.d(
                "NumberGenerated",
                "DocumentSnapshot successfully written!"

            )
            Toast.makeText(this, "New deal submitted for review.", Toast.LENGTH_SHORT).show()

        }
    val intent = Intent(this, InitalpostnewdealActivity::class.java)
    startActivity(intent);
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
        distancetitle.text = rounded + " Mi away"
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


    fun setupheaders(){
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

        val saveIcon = findViewById<ImageView>(R.id.save)
        saveIcon.startAnimation(rotate)
        saveIcon.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will save the deal to the users profile so they can easily find it later.",
                Toast.LENGTH_LONG
            ).show()

        }
        val alarm = findViewById<ImageView>(R.id.dealalarm)
        alarm.startAnimation(rotate)
        alarm.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will set a alarm on a users phone, next time the deal is live they will be reminded.",
                Toast.LENGTH_LONG
            ).show()
        }
        val yelp = findViewById<ImageView>(R.id.dealyelp)
        yelp.startAnimation(rotate)
        yelp.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will open to your yelp page if you have provided it, if you need to update the link go to the settings page.",
                Toast.LENGTH_LONG
            ).show()
        }
        val Insta = findViewById<ImageView>(R.id.Insta)
        Insta.startAnimation(rotate)
        Insta.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will open to your Insta page if you have provided it, if you need to update the link go to the settings page.",
                Toast.LENGTH_LONG
            ).show()
        }
        val FB = findViewById<ImageView>(R.id.Facebook)
        FB.startAnimation(rotate)
        FB.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will open to your Facebook page if you have provided it, if you need to update the link go to the settings page.",
                Toast.LENGTH_LONG
            ).show()
        }
        val website = findViewById<ImageView>(R.id.dealwebsite)
        website.startAnimation(rotate)
        website.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will open to your website page if you have provided it, if you need to update the link go to the settings page.",
                Toast.LENGTH_LONG
            ).show()
        }
        val map = findViewById<ImageView>(R.id.dealmap)
        map.startAnimation(rotate)
        map.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will open google maps and provide directions to your business. If you need to update the link go to the settings page.",
                Toast.LENGTH_LONG
            ).show()
        }
        val phone = findViewById<ImageView>(R.id.dealphone)
        phone.startAnimation(rotate)
        phone.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will prompt your phone numbers to call your business. If you need to update the link go to the settings page.",
                Toast.LENGTH_LONG
            ).show()
        }


        //info section
        val infoheadertitle = findViewById<TextView>(R.id.info)
        val DealTitle = findViewById<TextView>(R.id.textView52)
        DealTitle.setText(Title)
        val Dealprice= findViewById<TextView>(R.id.textView53)
        Dealprice.setText("Price: "+Price)
        val DealDescription = findViewById<TextView>(R.id.textView54)
        DealDescription.setText(Description)

        //when section
        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        val WhenHeaderTitle = findViewById<TextView>(R.id.`when`)
        val DealDayofdealtext = findViewById<TextView>(R.id.Avaliableon)
        DealDayofdealtext.setText("Avaliable on " + Dayofdealtext)
        val Dealtime = findViewById<TextView>(R.id.Livefrom)
        Dealtime.setText("Live from "+ Dealstarttimetext + " to "+ Dealendtimetext)
        val Dealpostedon = findViewById<TextView>(R.id.Dealpostedon)
        Dealpostedon.setText("Deal posted on " + currentDate)

        //where section
        val whereheadertitle = findViewById<TextView>(R.id.where)
        whereheadertitle.setText("Where")
        val Companybutton = findViewById<TextView>(R.id.`company`)
        Companybutton.setText(RestaurantName)
        Companybutton.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "This button will direct the user to your company page to see all of your active deals.",
                Toast.LENGTH_LONG
            ).show()
        }


        //Extra Info
        val extraheadertitle = findViewById<TextView>(R.id.extratitle)
//        val submitnewdealtitle = findViewById<TextView>(R.id.`submitnewdealtitle`)
//        submitnewdealtitle.setText("Submit New Deal")
//        val Deletenewdealtitle = findViewById<TextView>(R.id.`Deletedealbutton`)
//        Deletenewdealtitle.setText("Delete Deal")
    }

    private fun headerandbottom() {


        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Deal Review Page")
            intent.putExtra("desc","* Here you can see what a new or existing looks like for the normal users. \n\n * If this deal posting needs to be updated, just delete this deal and create a new one.")
            startActivity(intent)

        }

//        leftIcon.setVisibility(View.INVISIBLE)
        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("")
    }

}