package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class DealCollectionViewActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    private lateinit var newRecyclerView: RecyclerView
    private var gridlayoutManager: GridLayoutManager? = null
    private lateinit var newArrayList: ArrayList<Pendingapproval>
    private lateinit var querylist: CollectionReference
    var AllDeals = "yes"
    var dealtype = ""
    var day = ""
    val data = java.util.ArrayList<Pendingapproval>()
    val filtereddata = java.util.ArrayList<Pendingapproval>()
    var AllDays = "yes"
    val admincode = ""
    var resid = ""
    var Name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_collection_view)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

        newRecyclerView = findViewById(R.id.recyclerview1)
        gridlayoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        newRecyclerView?.layoutManager = gridlayoutManager
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        getpasseddata()
        headerandbottom()
        daybuttonsetup()
        Catsetup()

    }
    fun getpasseddata(){
        val intent = intent
        Name = intent.getStringExtra("Name").toString()
        resid = intent.getStringExtra("residtrimmed").toString()

        Log.d("resid",  resid)
        Log.d("Name",  Name)

        getUserdata()
    }

    private fun Catsetup() {
        val Allcategory = findViewById<Button>(R.id.ALLcat)
        val food = findViewById<Button>(R.id.Food)
        val bev = findViewById<Button>(R.id.Beverages)
        val act = findViewById<Button>(R.id.Activitys)
        Allcategory.isSelected = !Allcategory.isSelected

        Allcategory.setOnClickListener {
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

        Allday.setOnClickListener {
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



    private fun getUserdata() {

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        val docRef = db.collection("ClientDeals1").document(resid).collection(resid)
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

                    val adapter = collectionviewadapter(data)
                    newRecyclerView.adapter = adapter
                }
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

        val adapter = collectionviewadapter(filtereddata)
        newRecyclerView.adapter = adapter
    }


    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText(Name)

    }
}
