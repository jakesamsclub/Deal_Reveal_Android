package com.example.dealreveal.Activites

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.Activites.users.*
import com.example.dealreveal.R
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_saved_deals.*


class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

}

var usernotsignedin = false


class UserSavedDealsActivity : AppCompatActivity(), LocationListener {

    lateinit var auth: FirebaseAuth
    lateinit var newRecyclerView: RecyclerView
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    var currentuser = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_saved_deals)
        headerandbottom()
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

        window.decorView.post {
            usercheck()
        }


    }

    private fun usercheck(){

        if (usernotsignedin == false)
        {
            currentuser = FirebaseAuth.getInstance().currentUser!!
                .uid

            getLocationsafetycheck()
        }
        if (usernotsignedin == true) {
            Log.d("ff", "YEs")
            popup()
        }
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
    fun getLocationsafetycheck(){
        if (userlat == ""){
            getLocation()
        }
        if (userlat != ""){
            getUserdata()
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
        getUserdata()
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

//    private fun scrolled(){
//        newRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if(!recyclerView.canScrollVertically(1)) {
//                    Log.d("load more", "onScrolled: ")
//
//                    val query = db.collection("SavedDeals1").document(currentuser).collection("Deals").limit(5)
//                    getUserdata()
//                }
//            }
//        })
//    }



    private fun getUserdata() {
        newRecyclerView = findViewById(R.id.recyclerviewsaved)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)


        val db = FirebaseFirestore.getInstance()
        val query = db.collection("SavedDeals").document(currentuser).collection("Deals")

        val config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPrefetchDistance(1)
            .setPageSize(1)
            .build()

        val options = FirestorePagingOptions.Builder<Pendingapproval>().setQuery(query,config, Pendingapproval::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestorePagingAdapter<Pendingapproval, SavedViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
                return SavedViewHolder(itemView)
            }




            override fun onBindViewHolder(holder: SavedViewHolder, position: Int, model: Pendingapproval) {

                val titleImage : ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                val tvheading : TextView = holder.itemView.findViewById(R.id.tvheading)
                val distance : TextView = holder.itemView.findViewById(R.id.distance)
                val date : TextView = holder.itemView.findViewById(R.id.date)

                tvheading.text = model.Title
                date.text = "$" + model.price
                Glide.with(this@UserSavedDealsActivity)
                    .load(model.MealImageUrl.toString())
                    .into(titleImage)

                //calculate distance
                val loc1 = Location("")
                loc1.latitude = model.latitude.toDouble()
                loc1.longitude = model.longitude.toDouble()
                Log.d("TAG","DEAL Loaded" )

                val loc2 = Location("")
                loc2.latitude = userlat.toDouble()
                loc2.longitude = userlong.toDouble()
                Log.d("user location", userlat+"+"+userlong)

                val distanceInMeters = loc1.distanceTo(loc2)
                val distanceInMiles = distanceInMeters/1609.34
                val rounded = String.format("%.2f", distanceInMiles)
                distance.text = rounded + " Mi Away"

                //holder setup

                holder.itemView.setOnClickListener {
                    Log.i("test", "im a god")

                    var Address = model.Address
                    var CompanyURL  = model.CompanyURL
                    var DayofDeal = model.DayofDeal
                    var EndTime = model.EndTime
                    var EndTimeNumber = model.EndTimeNumber
                    var Facebook = model.Facebook
                    var MealImageUrl = model.MealImageUrl
                    var PhoneNumber = model.PhoneNumber
                    var RestaurantName = model.RestaurantName
                    var StartTime = model.StartTime
                    var StartTimeNumber = model.StartTimeNumber
                    var Title = model.Title
                    var Yelp = model.Yelp
                    var category = model.category
                    var date = model.date
                    var description = model.description
                    var latitude = model.latitude
                    var longitude = model.longitude
                    var price = model.price
                    var resid = model.resid
                    var uid = model.uid
                    var insta = model.Insta


                    val intent = Intent(this@UserSavedDealsActivity, DealRevealUserActivity::class.java)
                    intent.putExtra("Address",Address)
                    intent.putExtra("CompanyURL",CompanyURL)
                    intent.putExtra("DayofDeal",DayofDeal)
                    intent.putExtra("EndTime",EndTime)
                    intent.putExtra("EndTimeNumber",EndTimeNumber)
                    intent.putExtra("Facebook",Facebook)
                    intent.putExtra("Insta",insta)
                    intent.putExtra("MealImageUrl",MealImageUrl)
                    intent.putExtra("PhoneNumber",PhoneNumber)
                    intent.putExtra("RestaurantName",RestaurantName)
                    intent.putExtra("StartTime",StartTime)
                    intent.putExtra("StartTimeNumber",StartTimeNumber)
                    intent.putExtra("Title",Title)
                    intent.putExtra("Yelp",Yelp)
                    intent.putExtra("category",category)
                    intent.putExtra("date",date)
                    intent.putExtra("description",description)
                    intent.putExtra("latitude",latitude)
                    intent.putExtra("longitude",longitude)
                    intent.putExtra("price",price)
                    intent.putExtra("resid",resid)
                    intent.putExtra("uid",uid)
                    intent.putExtra("admincheck","pendingdeal")


                    startActivity(intent)

                }

            }

            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL -> {
                        Log.d("TAG","LOADING_INITIAL" )

                    }

                    LoadingState.LOADING_MORE -> {
                        Log.d("TAG","LOADING_MORE" )
                    }

                    LoadingState.LOADED -> {
                        Log.d("TAG","LOADED a total of " +newRecyclerView.adapter!!.itemCount.toString() )
                    }

                    LoadingState.ERROR -> {
                        Toast.makeText(
                            applicationContext,
                            "Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    LoadingState.FINISHED -> {
                        Log.d("TAG","FINISHED" )
                    }
                }
            }

        }
        newRecyclerView.adapter = adapter
        newRecyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

//        leftIcon.setVisibility(View.INVISIBLE)
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Saved Deals")
            intent.putExtra("desc","*In the saved deals tab, you can find all of your saved deals.  \n\n *If you open a saved deal you can tap the save icon to unsave a deal as well. \n\n *You can tap the alarm button on each saved deal if you want a alert sent to your phone when a deal is live \n\n *Press the arrow if you would like to open a saved deal.")
            startActivity(intent)

        }
        title.setText("Saved Deals")
        title.textAlignment = View.TEXT_ALIGNMENT_CENTER


        leftIcon.isVisible = false


        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.SavedDeals

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.SavedDeals -> {

                    true
                }
                R.id.RandomDeals -> {
                    val intent = Intent(this, DealswipeActivity::class.java)
                    startActivity(intent);

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
}