package com.example.dealreveal.Activites.client

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Businessupdate1Activity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    var Address = ""
    var CompanyURL = ""
    var Facebook = ""
    var Insta = ""
    var Phonenumber = ""
    var RestaurantName = ""
    var Yelp = ""
    var latitude = ""
    var longitude = ""
    var resid = ""
    var POC = ""
    var avatar = ""
    lateinit var filepath : Uri
    var adminaccess = "null"
    val Changed = java.util.ArrayList<String>()
    var date = ""
    var AdminChanged = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_businessupdate1)
        headerandbottom()
        admincheck()
    }
    private fun admincheck(){

        val intent = intent
        Address = intent.getStringExtra("Address").toString()
        avatar = intent.getStringExtra("Avatar").toString()
        date = intent.getStringExtra("date").toString()
        CompanyURL = intent.getStringExtra("CompanyURL").toString()
        Facebook = intent.getStringExtra("Facebook").toString()
        Phonenumber = intent.getStringExtra("Phonenumber").toString()
        RestaurantName = intent.getStringExtra("RestaurantName").toString()
        Yelp = intent.getStringExtra("Yelp").toString()
        latitude = intent.getStringExtra("latitude").toString()
        longitude = intent.getStringExtra("longitude").toString()
        resid = intent.getStringExtra("resid").toString()
        POC = intent.getStringExtra("POC").toString()
        AdminChanged = intent.getStringExtra("Changed").toString()
        adminaccess = intent.getStringExtra("adminaccess").toString()

        setup()
        nextbuttonsetup()

        Log.d("TAG", adminaccess)

        if (adminaccess == "null") {
            getclientinfo()
        }
    }

    private fun nextbuttonsetup() {

        val next = findViewById<Button>(R.id.nextbutton)
        next.setText("Continue")

        if (adminaccess == "null") {

            next.setOnClickListener {

                val intent = Intent(this, Businessupdate2Activity::class.java)

                if (::filepath.isInitialized) {
                    Log.d("TAG", "file" + filepath.path.toString())
                    intent.putExtra("changedavatar", filepath)
                    Changed.add("Avatar")
                } else {
                    filepath = Uri.EMPTY
                    Log.d("TAG", "file did not get changed" + filepath.path.toString())
                    intent.putExtra("avatar", avatar)
                    intent.putExtra("changedavatar", filepath)
                }
                val Businessnametext = findViewById<TextView>(R.id.editBusinessname)
                if (Businessnametext.text.toString() != RestaurantName.toString()) {
                    Changed.add("Busname")
                }
                val POCtext = findViewById<TextView>(R.id.editPOC)
                if (POCtext.text.toString() != POC.toString()) {
                    Changed.add("POC")
                }

                val Addresstext = findViewById<TextView>(R.id.editAddress)
                if (Addresstext.text.toString() != Address.toString()) {
                    Changed.add("Address")
                }

                intent.putExtra("Address", Addresstext.text.toString())
                intent.putExtra("CompanyURL", CompanyURL)
                intent.putExtra("Facebook", Facebook)
                intent.putExtra("Insta", Insta)
                intent.putExtra("Phonenumber", Phonenumber)
                intent.putExtra("RestaurantName", Businessnametext.text.toString())
                intent.putExtra("Yelp", Yelp)
                intent.putExtra("latitude", latitude)
                intent.putExtra("longitude", longitude)
                intent.putExtra("resid", resid)
                intent.putExtra("POC", POCtext.text.toString())
                intent.putExtra("Changed", Changed.distinct().toString())

                startActivity(intent);
            }
        }
            if (adminaccess != "null") {

                next.setOnClickListener {

                    val intent = Intent(this, Businessupdate2Activity::class.java)
                    Log.d("TAG", "failed to send")
                val Addresstext = findViewById<TextView>(R.id.editAddress)
                val POCtext = findViewById<TextView>(R.id.editPOC)
                val Businessnametext = findViewById<TextView>(R.id.editBusinessname)

                if (::filepath.isInitialized) {
                    Log.d("TAG", "file"+filepath.path.toString())
                    intent.putExtra("changedavatar",filepath)
                    Changed.add("Avatar")
                } else {
                    filepath = Uri.EMPTY
                    Log.d("TAG", "file did not get changed"+filepath.path.toString())
                    intent.putExtra("avatar",avatar)
                    intent.putExtra("changedavatar",filepath)
                }

                    intent.putExtra("Address",Addresstext.text.toString())
                    intent.putExtra("CompanyURL",CompanyURL)
                    intent.putExtra("Facebook",Facebook)
                    intent.putExtra("Phonenumber",Phonenumber)
                    intent.putExtra("RestaurantName",Businessnametext.text.toString())
                    intent.putExtra("Yelp",Yelp)
                    intent.putExtra("resid",resid)
                    intent.putExtra("adminaccess",adminaccess)
                    intent.putExtra("POC",POCtext.text.toString())

                    startActivity(intent);
                }
                }


    }

    private fun getclientinfo(){
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
                POC = ClientInfo?.Restpointofcontact.toString()
                avatar = ClientInfo?.avatar.toString()
                setup()
                Log.d("TAG", "setup running for"+ClientInfo?.resid.toString())
            }
    }
    private fun setup(){

        val Businessnametext = findViewById<TextView>(R.id.editBusinessname)
        Businessnametext.setText(RestaurantName)
        val POCtext = findViewById<TextView>(R.id.editPOC)
        POCtext.setText(POC)
        val Addresstext = findViewById<TextView>(R.id.editAddress)
        Addresstext.setText(Address)
        val avatar1 = findViewById<ImageView>(R.id.avatar)
        val changedtext = findViewById<TextView>(R.id.doubletap)
        AdminChanged = AdminChanged.replace("[", "")
        AdminChanged = AdminChanged.replace("]", "")


        Glide.with(this@Businessupdate1Activity)
            .load(avatar)
            .into(avatar1)
        avatar1.setOnClickListener {
            startFileChooder()
        }
    }
    private fun startFileChooder() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose Picture"), 111)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null) {
            val imageViewv = findViewById<ImageView>(R.id.avatar)
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageViewv.setImageBitmap(bitmap)
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
                val intent = Intent(this, UpdateBusinessInfoActivity::class.java)
                startActivity(intent)
            }
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Update Business Info")
            intent.putExtra("desc","* Here you can update your companys avatar, name, POC, and address that deal reveal has on file.")
            startActivity(intent)
        }
        title.setText("Update Business Info")
    }
}