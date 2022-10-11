package com.example.dealreveal.Activites.client

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.admins.AdminloginActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class Businessupdate2Activity : AppCompatActivity() {
    var Address = ""
    var CompanyURL = ""
    var Facebook = ""
    var Phonenumber = ""
    var RestaurantName = ""
    var Yelp = ""
    var Insta = ""
    var latitude = ""
    var longitude = ""
    var resid = ""
    var POC = ""
    var avatar = ""
    var changedavatar = ""
    var adminaccess = "null"
    lateinit var filepath : Uri
    val Changed = java.util.ArrayList<String>()
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_businessupdate2)
        headerandbottom()
        buttonsetup()
        Log.d("TAG", "onCreate:failed ")
    }

    private fun buttonsetup() {

        val intent = intent
        Address = intent.getStringExtra("Address").toString()
        CompanyURL = intent.getStringExtra("CompanyURL").toString()
        Facebook = intent.getStringExtra("Facebook").toString()
        Insta = intent.getStringExtra("Insta").toString()
        Phonenumber = intent.getStringExtra("Phonenumber").toString()
        RestaurantName = intent.getStringExtra("RestaurantName").toString()
        Yelp = intent.getStringExtra("Yelp").toString()
        latitude = intent.getStringExtra("latitude").toString()
        longitude = intent.getStringExtra("longitude").toString()
        resid = intent.getStringExtra("resid").toString()
        POC = intent.getStringExtra("POC").toString()
        avatar = intent.getStringExtra("avatar").toString()
        adminaccess = intent.getStringExtra("adminaccess").toString()
        filepath = intent.getParcelableExtra<Uri>("changedavatar")!!
        Changed.add(intent.getStringExtra("Changed").toString())

        Log.d("TAG", "File " + filepath.path.toString() +" Insta" + Insta + " Companyurl: "+ CompanyURL + " FB: " +Facebook + " Phonenumber: "+ Phonenumber +" Companyname: "+ RestaurantName + " yelp: " +Yelp + " latitude: " + latitude + " longitude: " + longitude + " resid: " + resid +" POC: " + POC + " avatar: " + avatar + " changed: " + Changed)

        val phonenumbertext = findViewById<TextView>(R.id.editphonenumber)
        phonenumbertext.setText(Phonenumber)
        val websitetext = findViewById<TextView>(R.id.editwebsite)
        websitetext.setText(CompanyURL)
        val facebooktext = findViewById<TextView>(R.id.editFacebookLink)
        facebooktext.setText(Facebook)
        val yelptext = findViewById<TextView>(R.id.editYelp)
        yelptext.setText(Yelp)
        val Instatext = findViewById<TextView>(R.id.editInsta)
        Instatext.setText(Insta)

        val next = findViewById<Button>(R.id.Continue)
        next.setText("Submit For Review")
        next.setOnClickListener {
            Log.d("admincheck", adminaccess)
            if (adminaccess != "null") {
                submitadminchange()
            }

            else if (filepath.path.toString()=="") {
                    Log.d("TAG1", "filepath is null"+filepath.path.toString())
                    Submitnewchange()
                }
                else {
                    Log.d("TAG", "filepath is null" + filepath.path.toString())
                    Submitnewchangewithphotochange()
                }

        }

    }

    private fun Submitnewchange() {
        val phonenumbertext = findViewById<TextView>(R.id.editphonenumber)
        val websitetext = findViewById<TextView>(R.id.editwebsite)
        val facebooktext = findViewById<TextView>(R.id.editFacebookLink)
        val yelptext = findViewById<TextView>(R.id.editYelp)
        val Instatext = findViewById<TextView>(R.id.editInsta)

        if (phonenumbertext.text.toString() != Phonenumber){
            Changed.add("Phone")
        }
        if (websitetext.text.toString() != CompanyURL){
            Log.d("website change", "was:"+ CompanyURL +" now:" + websitetext.text.toString())
            Changed.add("CompURL")
        }
        if (facebooktext.text.toString() != Facebook){
            Changed.add("FB")
        }
        if (yelptext.text.toString() != Yelp){
            Changed.add("Yelp")
        }
        if (Instatext.text.toString() != Insta){
            Changed.add("IG")
        }

        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        val possible = hashMapOf(
            "Changed" to Changed.toString(),
            "date" to currentDate,
            "PhoneNumber" to phonenumbertext.text.toString(),
            "Pointofcontact" to POC,
            "CompanyURL" to websitetext.text.toString(),
            "Address" to Address,
            "avatar" to avatar,
            "business" to RestaurantName,
            "Facebook" to facebooktext.text.toString(),
            "Insta" to Instatext.text.toString(),
            "latitude" to latitude,
            "longitude" to longitude,
            "resid" to resid,
            "Yelp" to yelptext.text.toString(),

            )
        db.collection("Businesschangerequest").document(resid).set(possible)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written!"

                )
                val intent = Intent(this,ClientsettingsActivity::class.java)
                startActivity(intent);

            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
    }

    private fun Submitnewchangewithphotochange() {
        if (filepath != null) {
            var pd = ProgressDialog(this)
            pd.setTitle("uploading")
            var id = UUID.randomUUID().toString()

            pd.show()

            var imageRef =
                FirebaseStorage.getInstance().reference.child("Businesschange").child(id)

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

    private fun sumbitpossibledeal(url: String) {

    val phonenumbertext = findViewById<TextView>(R.id.editphonenumber)
    val websitetext = findViewById<TextView>(R.id.editwebsite)
    val facebooktext = findViewById<TextView>(R.id.editFacebookLink)
    val yelptext = findViewById<TextView>(R.id.editYelp)
        val Instatext = findViewById<TextView>(R.id.Insta)


    if (phonenumbertext.text.toString() == Phonenumber){
        Changed.add("Phone")
    }
        if (Instatext.text.toString() != Insta){
            Changed.add("IG")
        }
        if (websitetext.text.toString() == CompanyURL){
        Changed.add("CompURL")
    }
    if (facebooktext.text.toString() == Facebook){
        Changed.add("FB")
    }
    if (yelptext.text.toString() == Yelp){
        Changed.add("Yelp")
    }


    val currentDate: String =
        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

    val possible = hashMapOf(
        "Changed" to Changed.toString(),
        "date" to currentDate,
        "PhoneNumber" to phonenumbertext.text.toString(),
        "Pointofcontact" to POC,
        "CompanyURL" to websitetext.text.toString(),
        "Address" to Address,
        "avatar" to url,
        "Insta" to Instatext.text.toString(),
        "business" to RestaurantName,
        "Facebook" to facebooktext.text.toString(),
        "latitude" to latitude,
        "longitude" to longitude,
        "resid" to resid,
        "Yelp" to yelptext.text.toString(),

        )
    db.collection("Businesschangerequest").document(resid).set(possible)
        .addOnSuccessListener {
            Log.d(
                "NumberGenerated",
                "DocumentSnapshot successfully written!"

            )
            val intent = Intent(this,ClientsettingsActivity::class.java)
            startActivity(intent);

        }
        .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }



    //allow customer to see change request
//    db.collection("ReviewMeals1").document(resid).collection(resid).document(id).set(possible)
//        .addOnSuccessListener {
//            Log.d(
//                "NumberGenerated",
//                "DocumentSnapshot successfully written!"
//
//            )
//            startActivity(Intent(applicationContext, Startscreen::class.java))
//            finish()
//        }
//        .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
}

    private fun submitadminchange() {
        val phonenumbertext = findViewById<TextView>(R.id.editphonenumber)
        val websitetext = findViewById<TextView>(R.id.editwebsite)
        val facebooktext = findViewById<TextView>(R.id.editFacebookLink)
        val yelptext = findViewById<TextView>(R.id.editYelp)

        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())


        db.collection("Deals1").whereEqualTo("resid", resid).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    val myObject = document.toObject(Pendingapproval::class.java)

                    val dealupdates = mapOf(
                        "PhoneNumber" to phonenumbertext.text.toString(),
                        "CompanyURL" to websitetext.text.toString(),
                        "Facebook" to facebooktext.text.toString(),
                        "Yelp" to yelptext.text.toString(),
                        "resid" to resid,
                        "RestaurantName" to RestaurantName,
                        "Address" to Address,
                        "latitude" to latitude,
                        "longitude" to longitude,

                        )

                    db.collection("Deals1").document(myObject.uid).update(dealupdates)
                    db.collection("ClientDeals1").document(resid).collection(resid).document(myObject.uid).update(dealupdates)
                    Log.e("TAG", myObject.resid) // Print : ""
                }

                //update clientaccounts

                val clientupdate = mapOf(
                    "ClientPhone" to phonenumbertext.text.toString(),
                    "Clienturl" to websitetext.text.toString(),
                    "Clientaddy" to Address,
                    "Yelp" to yelptext.text.toString(),
                    "Clientname" to RestaurantName,
                    "avatar" to avatar,
                    "Facebook" to facebooktext.text.toString(),
                    "Restpointofcontact" to POC,
                    "Restlong" to longitude,
                    "Restlat" to latitude
                )

                db.collection("ClientsAccounts").document(resid).update(clientupdate)
                db.collection("Businesschangerequest").document(resid).delete()

                val intent = Intent(this, AdminloginActivity::class.java)
                startActivity(intent)

            }

            .addOnFailureListener { e ->
                Log.w(
                    "NumberGenerated",
                    "Error writing document",
                    e
                )
            }

    }


    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

//        leftIcon.setVisibility(View.INVISIBLE)
        leftIcon.setOnClickListener {

            if (adminaccess != "null") {
                finish()
            }
            else {
                val intent = Intent(this, Businessupdate1Activity::class.java)
                startActivity(intent)
            }
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("Update Business Info")
    }
}
