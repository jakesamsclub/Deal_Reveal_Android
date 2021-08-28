package com.example.dealreveal.Activites.shared

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.client.Client
import com.example.dealreveal.R
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class DealRevealActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_deal_reveal)
        val intent = intent

        admincheck = intent.getStringExtra("admincheck").toString()

        headerandbottom()
        newdeal()
        Log.d("client", admincheck)

    }

    fun newdeal() {
        if (admincheck == "") {
            lateinit var filepath : Uri


        filepath = intent.getParcelableExtra<Uri>("avatar")!!
        Price = intent.getStringExtra("Price").toString()
        Category = intent.getStringExtra("Category").toString()
        Dayofdealtext = intent.getStringExtra("Dayofdealtext").toString()
        Dealstarttimetext = intent.getStringExtra("Dealstarttimetext").toString()
        Dealendtimetext = intent.getStringExtra("Dealendtimetext").toString()
        Description = intent.getStringExtra("Description").toString()
        Title = intent.getStringExtra("Title").toString()

            //    avatar
            val Photo = findViewById<ImageView>(R.id.photo)
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            Photo.setImageBitmap(bitmap)

            val currentuser = FirebaseAuth.getInstance().currentUser!!
                .uid
            Log.d("client", currentuser)
            val docRef = db.collection("ClientsAccounts1").document(currentuser)
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val ClientInfo = documentSnapshot.toObject(Client::class.java)

                    Log.d("client", "DocumentSnapshot data: ${ClientInfo?.Clientname}")
                    Address = ClientInfo?.Clientaddy.toString()
                    CompanyURL = ClientInfo?.Clienturl.toString()
                    Facebook = ClientInfo?.Facebook.toString()
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
                        FirebaseStorage.getInstance().reference.child("approvalDeal1").child(id)

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

        if (admincheck == "approve new deal"){

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
            Glide.with(this@DealRevealActivity)
                .load(MealImageUrl)
                .into(Photo1)

            setupheaders()

            val nextbutton = findViewById<Button>(R.id.submitnewdealtitle)
            nextbutton.setOnClickListener {
                Submitnewdeal()
            }
            val deletebutton = findViewById<Button>(R.id.Deletedealbutton)
            deletebutton.setOnClickListener {
                rejectdeal()
            }
        }

        if (admincheck == "rejecteddeal"){

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
    private fun rejectdeal(){
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

        startActivity(intent)
    }
    private fun Submitnewdeal() {

        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        val possible = hashMapOf(
            "Address" to Address,
            "CompanyURL" to CompanyURL,
            "DayofDeal" to Dayofdealtext,
            "EndTime" to Dealendtimetext ,
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
            "uid" to uid ,

            )
        db.collection("Deals1").document(uid).set(possible)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written1!"
                )
                db.collection("ClientDeals1").document(resid).collection(resid).document(uid).set(possible)
                    .addOnSuccessListener {
                        Log.d(
                            "NumberGenerated",
                            "DocumentSnapshot successfully written!"
                        )

                        Deletependingdeal()
                        addgeolocations()
                    }
                    .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }

    }
    private fun addgeolocations(){
        val ref =
            FirebaseDatabase.getInstance().getReference("Deals1")
        val geoFire = GeoFire(ref)

        geoFire.setLocation(
            uid,
            GeoLocation(latitude.toDouble(), longitude.toDouble()),
            object : GeoFire.CompletionListener {
                fun onComplete(key: String?, error: FirebaseError?) {
                    if (error != null) {
                        System.err.println("There was an error saving the location to GeoFire: $error")
                    } else {
                        println("Location saved on server successfully!")

                    }
                }

                override fun onComplete(
                    key: String?,
                    error: DatabaseError?

                ){

                }
            })

        val ref1 =
            FirebaseDatabase.getInstance().getReference(Category+"1")
        val geoFire1 = GeoFire(ref1)

        geoFire1.setLocation(
            uid,
            GeoLocation(latitude.toDouble(), longitude.toDouble()),
            object : GeoFire.CompletionListener {
                fun onComplete(key: String?, error: FirebaseError?) {
                    if (error != null) {
                        System.err.println("There was an error saving the location to GeoFire: $error")
                    } else {
                        println("Location saved on server successfully!")

                    }
                }

                override fun onComplete(
                    key: String?,
                    error: DatabaseError?

                ){

                }
            })

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
                    "DocumentSnapshot successfully written!"

                )
            }
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
    db.collection("ReviewMeals1").document("Deals").collection("Deals").document(id).set(possible)
        .addOnSuccessListener {
            Log.d(
                "NumberGenerated",
                "DocumentSnapshot successfully written!"

            )

        }
        .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
    db.collection("ReviewMeals1").document(resid).collection(resid).document(id).set(possible)
        .addOnSuccessListener {
            Log.d(
                "NumberGenerated",
                "DocumentSnapshot successfully written!"

            )
            startActivity(Intent(applicationContext, Startscreen::class.java))
            finish()
        }
        .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
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
}