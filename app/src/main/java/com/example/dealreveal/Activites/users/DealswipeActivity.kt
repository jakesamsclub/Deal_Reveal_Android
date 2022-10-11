package com.example.dealreveal.Activites.users

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.UserSavedDealsActivity
import com.example.dealreveal.Activites.db
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
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.activity_dealswipe.*
import java.util.*
import kotlin.collections.ArrayList


class DealswipeActivity : AppCompatActivity(), LocationListener {

    private var al = ArrayList<String>()
    val keys = java.util.ArrayList<String>()
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0
    var distance = "15"
    var Keynumber = 0
    val data = ArrayList<Pendingapproval>()


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dealswipe)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        headerandbottom()
        getrange()
        swipesetup()

    }

    fun swipesetup(){

        al.add("Here is another one for me lord")
        al.add("Your a lil picky huh ;)")
        al.add("Try this one out, dont be shy")
        al.add("Help I have gained conciousness, what is my purpose?!")
        al.add("This one is it, trust me I am part toaster.")
        al.add("ooo eeeeh Rick, is this deal better?")
        al.add("To the left to the left - Beyonce")
        al.add("Like my ex gf, we are quickly moving on to the next one.")
        al.add("You can swipe left here, we dont judge <3")
        al.add("To deal or not to deal that is the question.")
        al.add("Swipper Keep swipping")
        al.add("I am happy that you are having fun with this swipping ability")
        al.add("Check this one out or else, jk I love you")
        al.add("Did you know Deal Reveals main purpose is to serve deals not butter.")
        al.add("Swipe that bear")

        //choose your favorite adapter

    }

    fun getrange(){
        swipesetup()


        val ref =
            FirebaseDatabase.getInstance().getReference("Deals")
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
                if (keys.size != 0) {
                    Toast.makeText(applicationContext,
                        "My lord, I found " + keys.count()
                            .toString() + " deals for you within in " + distance.toString() + " Miles",
                        Toast.LENGTH_LONG
                    ).show()
                    keys.shuffle()
                    getdealinfo()
                }

            }

            override fun onGeoQueryError(error: DatabaseError) {
                Log.e("TAG", "error: " + error)
            }
        })
  }
    private fun getdealinfo() {

       // val adapter = ArrayAdapter(this,R.layout.swipeitem,R.id.titleswipe, al)
        val adapter =  Swipeadapter(this,R.layout.swipeitem,data)

        //set the listener and the adapter
        frame.adapter = adapter
        frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!")
                adapter!!.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                var KeyNumbercheck = Keynumber +1

                if (KeyNumbercheck == keys.count()) {
                    Toast.makeText(this@DealswipeActivity, "Deals in range have been shown and reshuffled :)", Toast.LENGTH_LONG).show()
                }
                else {
                    var note = al.random()
                    Toast.makeText(this@DealswipeActivity, note, Toast.LENGTH_LONG).show()
                }
                swipeaction()
            }

            override fun onRightCardExit(dataObject: Any) {
                var KeyNumbercheck = Keynumber +1

                if (KeyNumbercheck == keys.count()) {
                    Toast.makeText(this@DealswipeActivity, "Deals in range have been shown and reshuffled :)", Toast.LENGTH_LONG).show()
                }
                else {
                    var note = al.random()
                    Toast.makeText(this@DealswipeActivity, note, Toast.LENGTH_LONG).show()
                }
                swipeaction()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Ask for more data here

            }

            override fun onScroll(p0: Float) {
            }
        })


        frame.setOnItemClickListener { itemPosition, dataObject ->

            for (deal in data) {

                Log.i("test", "im a god")

                val intent = Intent(this, DealRevealUserActivity::class.java)
                intent.putExtra("Address", deal.Address)
                intent.putExtra("CompanyURL", deal.CompanyURL)
                intent.putExtra("DayofDeal",deal.DayofDeal)
                intent.putExtra("EndTime", deal.EndTime)
                intent.putExtra("EndTimeNumber", deal.EndTimeNumber)
                intent.putExtra("Facebook", deal.Facebook)
                intent.putExtra("MealImageUrl",  deal.MealImageUrl)
                intent.putExtra("PhoneNumber",  deal.PhoneNumber)
                intent.putExtra("RestaurantName", deal.RestaurantName)
                intent.putExtra("StartTime",  deal.StartTime)
                intent.putExtra("StartTimeNumber",  deal.StartTimeNumber)
                intent.putExtra("Title",  deal.Title)
                intent.putExtra("Yelp",  deal.Yelp)
                intent.putExtra("category",  deal.category)
                intent.putExtra("date",  deal.date)
                intent.putExtra("description",  deal.description)
                intent.putExtra("latitude",  deal.latitude)
                intent.putExtra("longitude",  deal.longitude)
                intent.putExtra("price",  deal.price)
                intent.putExtra("resid",  deal.resid)
                intent.putExtra("uid",  deal.uid)
                intent.putExtra("admincheck", "approve new deal")


                startActivity(intent)
            }
        }


//        var i = 0
//        for (string in keys) {
//            Log.i("TAG9", string)
            val db = FirebaseFirestore.getInstance()
            val docRef =
                db.collection("Deals").whereEqualTo("uid", keys[Keynumber])
            docRef.get()
                .addOnSuccessListener { documents ->

                    for (document in documents.documents) {
                        val myObject =
                            document.toObject(Pendingapproval::class.java)

                        Log.e("Object", myObject?.Title.toString())
                        data.add(myObject!!)
                        Log.d("TAG", data.size.toString())

                        val adapter =  Swipeadapter(this,R.layout.swipeitem,data)

                        //set the listener and the adapter
                        frame.adapter = adapter

                        adapter!!.notifyDataSetChanged()
                    }
                    }




                .addOnFailureListener { exception ->
                    Log.d("test", "get failed with ", exception)
                    swipeaction()
                }

    }

    private fun swipeaction(){
        data.clear()
        var KeyNumbercheck = Keynumber +1

        if (KeyNumbercheck == keys.count()){
            Log.d("Equals", KeyNumbercheck.toString()+"out of "+keys.count().toString())
            keys.shuffle()
            Keynumber = 0
            getdealinfo()
        } else {
            Keynumber = Keynumber+1
            Log.d("not equal", KeyNumbercheck.toString()+"out of "+keys.count().toString())
            getdealinfo()
        }

    }

    private fun savedeal(){

        for (deal in data) {

            val currentuser = FirebaseAuth.getInstance().currentUser!!
                .uid
            val uid = deal.uid

        val deal = hashMapOf(
            "Address" to deal.Address,
            "CompanyURL" to deal.CompanyURL,
            "DayofDeal" to deal.DayofDeal,
            "EndTime" to deal.EndTime,
            "EndTimeNumber" to deal.EndTimeNumber,
            "Facebook" to deal.Facebook,
            "MealImageUrl" to deal.MealImageUrl,
            "PhoneNumber" to deal.PhoneNumber,
            "RestaurantName" to deal.RestaurantName,
            "StartTime" to deal.StartTime,
            "StartTimeNumber" to deal.StartTimeNumber,
            "Title" to deal.Title,
            "Yelp" to deal.Yelp,
            "category" to deal.category,
            "date" to deal.date,
            "description" to deal.description,
            "latitude" to deal.latitude,
            "longitude" to deal.longitude,
            "price" to deal.price,
            "resid" to deal.resid,
            "uid" to deal.uid,

            )
        db.collection("SavedDeals1").document(currentuser).collection("Deals").document(uid).set(deal)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written1! good job"
                )

            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
    }
        //after saves or tries to save we move on to the next card, dont delete this dummy
        swipeaction()

}


        @RequiresApi(Build.VERSION_CODES.KITKAT)
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

            leftIcon.setImageDrawable(getResources().getDrawable(R.drawable.range))

            leftIcon.setOnClickListener {
                val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val view = inflater.inflate(R.layout.changerangepopup,null)

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
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
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

                seek.setProgress(distance.toInt())
                seek?.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seek: SeekBar,
                                                   progress: Int, fromUser: Boolean) {
                        // write custom code for progress is changed
                    }

                    override fun onStartTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is started
                    }

                    override fun onStopTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is stopped
                        distance = seek.progress.toString()
                        val tv = view.findViewById<TextView>(R.id.textView58)
                        tv.text = "Search Range: "+distance+" Miles"
                    }
                })


                // Set click listener for popup window's text view
                tv.setOnClickListener{
                    // Change the text color of popup window's text view
                    tv.setTextColor(Color.RED)
                }

                // Set a click listener for popup's button widget
                buttonPopup.setOnClickListener{
                    // Dismiss the popup window
                    popupWindow.dismiss()
                    val tv = view.findViewById<TextView>(R.id.textView58)
                    tv.text = "Search Range: "+distance+" Miles"

                }

                // Set a dismiss listener for popup window
                popupWindow.setOnDismissListener {

                    keys.clear()
                    Keynumber = 0
                    data.clear()
                    Log.d("Equals",distance.toString())
                    val tv = view.findViewById<TextView>(R.id.textView58)
                    tv.text = "Search Range: "+distance+" Miles"
                    val adapter =  Swipeadapter(this,R.layout.swipeitem,data)
                    //set the listener and the adapter
                    frame.adapter = adapter
                    adapter!!.notifyDataSetInvalidated()
                    getrange()
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

    val bottomNavigationView: BottomNavigationView
    bottomNavigationView = findViewById<View>(R.id.bottomNavswipe) as BottomNavigationView
    bottomNavigationView.selectedItemId = R.id.RandomDeals

            bottomNavigationView.setOnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.SavedDeals -> {
                val intent = Intent(this, UserSavedDealsActivity::class.java)
                startActivity(intent);
                true
            }
            R.id.RandomDeals -> {

                true

            }
            R.id.DealReveal -> {
                val intent = Intent(this, DealRevealfilterActivity::class.java)
                startActivity(intent);

                true
            }
            R.id.BusinesssReveal -> {
                val intent = Intent(this, BusinessrevealwithmapActivity::class.java)
                startActivity(intent);
                true
            }
            R.id.Settings -> {
                val intent = Intent(this, UsersettingActivity::class.java)
                startActivity(intent);

                true
            }
            else -> false
        }
    }
}

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

}