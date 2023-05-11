package com.example.dealreveal.Activites.client

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dealreveal.Activites.PendingapprovalActivity
import com.example.dealreveal.Activites.clientcollectionviewadapter
import com.example.dealreveal.Activites.collectionviewadapter
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ClientCollectionDealActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    private lateinit var newRecyclerView: RecyclerView
    private var gridlayoutManager: GridLayoutManager? = null
    private lateinit var newArrayList: ArrayList<Pendingapproval>
    private lateinit var querylist: CollectionReference
    var AllDeals = "yes"
    var dealtype = ""
    var day = ""
    var companyname = ""
    var address = ""
    var yelp = ""
    var phone = ""
    var website = ""
    val data = java.util.ArrayList<Pendingapproval>()
    val filtereddata = java.util.ArrayList<Pendingapproval>()
    var AllDays = "yes"
    val companycode = ""
    var lat = ""
    var long = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_collection_deal)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

        newRecyclerView = findViewById(R.id.recyclerview1)
        gridlayoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        newRecyclerView?.layoutManager = gridlayoutManager
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        getUserdata()
        headerandbottom()
        daybuttonsetup()
        Catsetup()
        Feedbackondealsetup()

    }
    private fun Feedbackondealsetup(){
        val dealreviews = findViewById<Button>(R.id.ALL)
        dealreviews.setOnClickListener {
            startActivity(Intent(applicationContext, clientreviewfeedbackActivity::class.java))
        }
    }

    private fun Catsetup() {
        val Allcategory = findViewById<Button>(R.id.ALLcat)
        val food = findViewById<Button>(R.id.Food)
        val bev = findViewById<Button>(R.id.Beverages)
        val act = findViewById<Button>(R.id.Activitys)
        Allcategory.isSelected = !Allcategory.isSelected
        Allcategory.setBackgroundColor(Color.WHITE)
        Allcategory.setTextColor(Color.BLACK)

        Allcategory.setOnClickListener {
            buttoncatcolorset()
            Allcategory.setBackgroundColor(Color.WHITE)
            Allcategory.setTextColor(Color.BLACK)

            if (dealtype == ""){

            }
            else {
                dealtype = ""
                Allcategory.isSelected = !Allcategory.isSelected
                food.isSelected = false
                bev.isSelected = false
                act.isSelected = false
                updaterecycler()
            }

        }
        food.setOnClickListener {
            buttoncatcolorset()
            food.setBackgroundColor(Color.WHITE)
            food.setTextColor(Color.BLACK)

            if (dealtype == "Food"){

            }
            else {
                dealtype = "Food"
                AllDeals = "no"
                food.isSelected = !food.isSelected
                Allcategory.isSelected = false
                bev.isSelected = false
                act.isSelected = false
                updaterecycler()
            }
        }

        bev.setOnClickListener {
            buttoncatcolorset()
            bev.setBackgroundColor(Color.WHITE)
            bev.setTextColor(Color.BLACK)

            if (dealtype == "Beverage"){

            }
            else {

                dealtype = "Beverage"
                AllDeals = "no"
                bev.isSelected = !bev.isSelected
                Allcategory.isSelected = false
                food.isSelected = false
                act.isSelected = false
                updaterecycler()
            }
        }
        act.setOnClickListener {
            buttoncatcolorset()
            act.setBackgroundColor(Color.WHITE)
            act.setTextColor(Color.BLACK)
            if (dealtype == "Entertainment"){

            }
            else {
                dealtype = "Entertainment"
                AllDeals = "no"
                act.isSelected = !act.isSelected
                Allcategory.isSelected = false
                bev.isSelected = false
                food.isSelected = false
                updaterecycler()
            }
        }
    }

    private fun daybuttonsetup() {
        val Allday = findViewById<Button>(R.id.ALLDAY)
        val MON = findViewById<Button>(R.id.MON)
        val TUE = findViewById<Button>(R.id.TUE)
        val WED = findViewById<Button>(R.id.WED)
        val THU = findViewById<Button>(R.id.THU)
        val FRI = findViewById<Button>(R.id.FRI)
        val SAT = findViewById<Button>(R.id.SAT)
        val SUN = findViewById<Button>(R.id.SUN)
        Allday.isSelected = !Allday.isSelected
        Allday.setBackgroundColor(Color.WHITE)
        Allday.setTextColor(Color.BLACK)

        Allday.setOnClickListener {
            buttoncolorset()
            Allday.setBackgroundColor(Color.WHITE)
            Allday.setTextColor(Color.BLACK)
            if (day == ""){

            }
            else {
                day = ""
                Allday.isSelected = !Allday.isSelected
                MON.isSelected = false
                TUE.isSelected = false
                WED.isSelected = false
                THU.isSelected = false
                FRI.isSelected = false
                SAT.isSelected = false
                SUN.isSelected = false
                updaterecycler()
            }

        }
        MON.setOnClickListener {
            buttoncolorset()
            MON.setBackgroundColor(Color.WHITE)
            MON.setTextColor(Color.BLACK)
            if (day == "MON"){

            }
            else {
                day = "MON"
                MON.isSelected = !MON.isSelected
                Allday.isSelected = false
                TUE.isSelected = false
                WED.isSelected = false
                THU.isSelected = false
                FRI.isSelected = false
                SAT.isSelected = false
                SUN.isSelected = false
                updaterecycler()
            }

        }

        TUE.setOnClickListener {
            buttoncolorset()
            TUE.setBackgroundColor(Color.WHITE)
            TUE.setTextColor(Color.BLACK)
            if (day == "TUE"){

            }
            else {
                day = "TUE"
                TUE.isSelected = !TUE.isSelected
                Allday.isSelected = false
                MON.isSelected = false
                WED.isSelected = false
                THU.isSelected = false
                FRI.isSelected = false
                SAT.isSelected = false
                SUN.isSelected = false
                updaterecycler()
            }

        }
        WED.setOnClickListener {
            buttoncolorset()
            WED.setBackgroundColor(Color.WHITE)
            WED.setTextColor(Color.BLACK)

            if (day == "WED"){

            }
            else {
                day = "WED"
                WED.isSelected = !WED.isSelected
                Allday.isSelected = false
                MON.isSelected = false
                TUE.isSelected = false
                THU.isSelected = false
                FRI.isSelected = false
                SAT.isSelected = false
                SUN.isSelected = false
                updaterecycler()
            }

        }
        THU.setOnClickListener {
            buttoncolorset()
            THU.setBackgroundColor(Color.WHITE)
            THU.setTextColor(Color.BLACK)
            if (day == "THU"){

            }
            else {
                day = "THU"
                THU.isSelected = !THU.isSelected
                Allday.isSelected = false
                MON.isSelected = false
                TUE.isSelected = false
                WED.isSelected = false
                FRI.isSelected = false
                SAT.isSelected = false
                SUN.isSelected = false
                updaterecycler()
            }

        }
        FRI.setOnClickListener {
            buttoncolorset()
            FRI.setBackgroundColor(Color.WHITE)
            FRI.setTextColor(Color.BLACK)

            if (day == "FRI"){

            }
            else {
                day = "FRI"
                FRI.isSelected = !FRI.isSelected
                Allday.isSelected = false
                MON.isSelected = false
                TUE.isSelected = false
                THU.isSelected = false
                WED.isSelected = false
                SAT.isSelected = false
                SUN.isSelected = false
                updaterecycler()
            }

        }
        SAT.setOnClickListener {
            buttoncolorset()
            SAT.setBackgroundColor(Color.WHITE)
            SAT.setTextColor(Color.BLACK)
            if (day == "SAT"){

            }
            else {
                day = "SAT"
                SAT.isSelected = !SAT.isSelected
                Allday.isSelected = false
                MON.isSelected = false
                TUE.isSelected = false
                THU.isSelected = false
                FRI.isSelected = false
                WED.isSelected = false
                SUN.isSelected = false
                updaterecycler()
            }

        }
        SUN.setOnClickListener {
            buttoncolorset()
            SUN.setBackgroundColor(Color.WHITE)
            SUN.setTextColor(Color.BLACK)

            if (day == "SUN"){

            }
            else {
                day = "SUN"
                SUN.isSelected = !SUN.isSelected
                Allday.isSelected = false
                MON.isSelected = false
                TUE.isSelected = false
                THU.isSelected = false
                FRI.isSelected = false
                WED.isSelected = false
                SAT.isSelected = false
                updaterecycler()

            }
        }

    }
    fun buttoncolorset(){
        val Allday = findViewById<Button>(R.id.ALLDAY)
        val MON = findViewById<Button>(R.id.MON)
        val TUE = findViewById<Button>(R.id.TUE)
        val WED = findViewById<Button>(R.id.WED)
        val THU = findViewById<Button>(R.id.THU)
        val FRI = findViewById<Button>(R.id.FRI)
        val SAT = findViewById<Button>(R.id.SAT)
        val SUN = findViewById<Button>(R.id.SUN)

        Allday.setBackgroundColor(Color.BLACK)
        Allday.setTextColor(Color.WHITE)
        MON.setBackgroundColor(Color.BLACK)
        MON.setTextColor(Color.WHITE)
        TUE.setBackgroundColor(Color.BLACK)
        TUE.setTextColor(Color.WHITE)
        WED.setBackgroundColor(Color.BLACK)
        WED.setTextColor(Color.WHITE)
        THU.setBackgroundColor(Color.BLACK)
        THU.setTextColor(Color.WHITE)
        FRI.setBackgroundColor(Color.BLACK)
        FRI.setTextColor(Color.WHITE)
        SAT.setBackgroundColor(Color.BLACK)
        SAT.setTextColor(Color.WHITE)
        SUN.setBackgroundColor(Color.BLACK)
        SUN.setTextColor(Color.WHITE)

    }
    fun buttoncatcolorset(){
        val Allcategory = findViewById<Button>(R.id.ALLcat)
        val food = findViewById<Button>(R.id.Food)
        val bev = findViewById<Button>(R.id.Beverages)
        val act = findViewById<Button>(R.id.Activitys)

        Allcategory.setBackgroundColor(Color.BLACK)
        Allcategory.setTextColor(Color.WHITE)
        food.setBackgroundColor(Color.BLACK)
        food.setTextColor(Color.WHITE)
        bev.setBackgroundColor(Color.BLACK)
        bev.setTextColor(Color.WHITE)
        act.setBackgroundColor(Color.BLACK)
        act.setTextColor(Color.WHITE)


    }



    private fun getUserdata() {

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        val docRef = db.collection("Deals").whereEqualTo("resid",currentuser)
        docRef.get()
            .addOnSuccessListener { documents ->

                for (document in documents.documents) {
                    val myObject =
                        document.toObject(Pendingapproval::class.java)

                    data.add(myObject!!)
                    Log.d("datasize", data.size.toString())
//                        val adapter = CustomAdapter(data, userlat, userlong)
//                        newRecyclerView.adapter = adapter
                    Log.i("uidkey", myObject.uid)
                    companyname =  myObject.RestaurantName
                    address = myObject.Address
                    yelp = myObject.Yelp
                    phone = myObject.PhoneNumber
                    website = myObject.CompanyURL
                    lat = myObject.latitude
                    long = myObject.longitude

                    val adapter = clientcollectionviewadapter(data)
                    newRecyclerView.adapter = adapter
                }
                headerandbottom()
            }

            .addOnFailureListener { exception ->
                Log.d("test", "get failed with ", exception)
            }



    }

    fun updaterecycler(){
        filtereddata.clear()

        if (day == "" && dealtype == "") {
            for (deal in data) {
                filtereddata.add(deal)
            }
        }
        if (day != "" && dealtype == "") {
            for (deal in data) {
                if (deal.DayofDeal.contains(day)) {
                    filtereddata.add(deal)
                }
            }
        }
        if (day != "" && dealtype != "") {
            for (deal in data) {
                if (deal.DayofDeal.contains(day)&& deal.category == dealtype) {
                    filtereddata.add(deal)
                }
            }
        }
        if (day == "" && dealtype != "") {
            for (deal in data) {
                if (deal.category == dealtype) {
                    filtereddata.add(deal)
                }
            }
        }

        val adapter = clientcollectionviewadapter(filtereddata)
        newRecyclerView.adapter = adapter
    }


    private fun headerandbottom() {

        val companytitle = findViewById<TextView>(R.id.companytitle)
        companytitle.setText(companyname)

        val Companyphone = findViewById<ImageView>(R.id.phone)
        Companyphone.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:$"+phone)
//            startActivity(intent)

            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)} else {
                Toast.makeText(
                    applicationContext,
                    "Enable call permissons first. Go into your phones setting select Deal Reveal and enable call permissons and reload the app.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        val Companymap = findViewById<ImageView>(R.id.map)

        Companymap.setOnClickListener {
            intent = Intent(
                android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + userlat + "," + userlong + "&daddr=" + lat + "," + long)
            );
            startActivity(intent)

        }

        val Companyyelp = findViewById<ImageView>(R.id.Yelp)

        Companyyelp.setOnClickListener {
            intent = Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(yelp))
            startActivity(intent)

        }

        val Companywebsite = findViewById<ImageView>(R.id.website)

        Companywebsite.setOnClickListener {
            intent = Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(website))
            startActivity(intent)
        }

        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)
        leftIcon.isVisible = false

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Your Deals")
            intent.putExtra("desc","*The business page shows all deals a business has posted on DealReveal. \n\n *You can filter a companies deals by day and category.  \n\n *You can click the buttons below the company name to see their number, website, map, facebook and yelp page. ")
            startActivity(intent)

        }
        title.setText("")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.YourDeals

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.YourDeals -> {
                    true
                }
                R.id.UnderReview-> {
                    val intent = Intent(this, PendingapprovalActivity::class.java)
                    startActivity(intent);

                    true

                }
                R.id.NewDeal -> {
                    val intent = Intent(this, InitalpostnewdealActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.DealAnalytics -> {
                    val intent = Intent(this, AnalyticsAllDealActivity::class.java)
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