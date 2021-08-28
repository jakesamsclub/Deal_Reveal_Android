package com.example.dealreveal.Activites

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.admins.ApprovedealsActivity
import com.example.dealreveal.Activites.client.ClientsettingsActivity
import com.example.dealreveal.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_saved_deals.*


class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

lateinit var auth: FirebaseAuth
private lateinit var newRecyclerView: RecyclerView
private lateinit var locationManager: LocationManager
private val locationPermissionCode = 2

class UserSavedDealsActivity : AppCompatActivity(), LocationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_saved_deals)
        headerandbottom()
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        getUserdata()
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



    private fun getUserdata() {
        lateinit var newArrayList: ArrayList<Pendingapproval>
        newRecyclerView = findViewById(R.id.recyclerviewsaved)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        val query = db.collection("SavedDeals1").document(currentuser).collection("Deals")
        val options = FirestoreRecyclerOptions.Builder<Pendingapproval>().setQuery(query, Pendingapproval::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<Pendingapproval, SavedViewHolder>(options) {
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
                date.text = model.price
                Glide.with(this@UserSavedDealsActivity)
                    .load(model.MealImageUrl.toString())
                    .into(titleImage)

                //calculate distance
                val loc1 = Location("")
                loc1.latitude = model.latitude.toDouble()
                loc1.longitude = model.longitude.toDouble()

                val loc2 = Location("")
                loc2.latitude = userlat.toDouble()
                loc2.longitude = userlong.toDouble()
                Log.d("user location", userlat+"+"+userlong)

                val distanceInMeters = loc1.distanceTo(loc2)
                val distanceInMiles = distanceInMeters/1609.34
                val rounded = String.format("%.3f", distanceInMiles)
                distance.text = rounded + " Mi away"

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


                    val intent = Intent(this@UserSavedDealsActivity, DealRevealUserActivity::class.java)
                    intent.putExtra("Address",Address)
                    intent.putExtra("CompanyURL",CompanyURL)
                    intent.putExtra("DayofDeal",DayofDeal)
                    intent.putExtra("EndTime",EndTime)
                    intent.putExtra("EndTimeNumber",EndTimeNumber)
                    intent.putExtra("Facebook",Facebook)
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
            startActivity(intent)
        }
        title.setText("Saved Deals")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.SavedDeals

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.SavedDeals -> {

                    true
                }
                R.id.RandomDeals -> {
                    val intent = Intent(this, PendingapprovalActivity::class.java)
                    startActivity(intent);

                    true

                }
                R.id.DealReveal -> {

                    val intent = Intent(this, DealRevealfilterActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.BusinesssReveal -> {
                    val intent = Intent(this, ApprovedealsActivity::class.java)
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