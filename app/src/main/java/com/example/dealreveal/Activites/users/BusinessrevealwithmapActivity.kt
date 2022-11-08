package com.example.dealreveal.Activites.users

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dealreveal.Activites.DealCollectionViewActivity
import com.example.dealreveal.Activites.UserSavedDealsActivity
import com.example.dealreveal.Activites.client.Client
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.R
import com.example.dealreveal.databinding.ActivityBusinessrevealwithmapBinding
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryEventListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_deal_revealfilter.*


class BusinessrevealwithmapActivity : AppCompatActivity(), OnMapReadyCallback
   // , LocationListener
    {

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var mMap: GoogleMap
    var movedlong = 0.0
    var movedlat = 0.0
    private lateinit var binding: ActivityBusinessrevealwithmapBinding
    var hashMap : HashMap<Double, String>
            = HashMap<Double, String> ()
    val data = ArrayList<Client>()
    val filtereddata = ArrayList<Client>()
    var dealtype = "Food"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

        binding = ActivityBusinessrevealwithmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        filterbuttonsetup()
        headerandbottom()
//        getLocation()
        getrange()
    }
        fun buttoncolorset(){
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
     fun filterbuttonsetup(){
         val Allcategory = findViewById<Button>(R.id.ALLcat)
         val food = findViewById<Button>(R.id.Food)
         val bev = findViewById<Button>(R.id.Beverages)
         val act = findViewById<Button>(R.id.Activitys)

         Allcategory.setOnClickListener {
             buttoncolorset()
             Allcategory.setBackgroundColor(Color.WHITE)
             Allcategory.setTextColor(Color.BLACK)
             dealtype = ""
             Allcategory.isSelected = !Allcategory.isSelected
             food.isSelected = false
             bev.isSelected = false
             act.isSelected = false
             filtersClients()

         }
         food.setOnClickListener {
             buttoncolorset()
             food.setBackgroundColor(Color.WHITE)
             food.setTextColor(Color.BLACK)
             dealtype = "Food"
             food.isSelected = !food.isSelected
             Allcategory.isSelected = false
             bev.isSelected = false
             act.isSelected = false
             filtersClients()
         }

         bev.setOnClickListener {
             buttoncolorset()
             bev.setBackgroundColor(Color.WHITE)
             bev.setTextColor(Color.BLACK)
             dealtype = "Beverage"
             bev.isSelected = !bev.isSelected
             Allcategory.isSelected = false
             food.isSelected = false
             act.isSelected = false
             filtersClients()
         }
         act.setOnClickListener {
             buttoncolorset()
             act.setBackgroundColor(Color.WHITE)
             act.setTextColor(Color.BLACK)
             dealtype = "Entertainment"
             act.isSelected = !act.isSelected
             Allcategory.isSelected = false
             bev.isSelected = false
             food.isSelected = false
             filtersClients()
         }
         //set initial button
         Allcategory.isSelected = !Allcategory.isSelected
     }

//    private fun getLocation() {
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
//    }
//    override fun onLocationChanged(location: Location) {
//        userlong= location.longitude.toString()
//        userlat = location.latitude.toString()
//        getrange()
//    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
        val user = LatLng(userlat.toDouble(), userlong.toDouble())
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.addMarker(MarkerOptions().position(user).title("Search Center").snippet("You can move this icon and update search").draggable(true).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(user))
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 11.0f ) )


        mMap.addCircle(
            CircleOptions()
            .center( LatLng(userlat.toDouble(), userlong.toDouble())).radius(13000.0).strokeColor(Color.RED)
                .fillColor(0x220000FF)
                .strokeWidth(5F)
        )

        mMap.setInfoWindowAdapter(CustomInfoWindowForGoogleMap(this))

        mMap.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener() {marker ->



            if (marker.title != "Search Center"){
                var Name = marker.title
                var resid= marker.snippet
                var residtrimmed = marker.snippet!!.substring(resid!!.indexOf(":")+1)
                residtrimmed.trim()

                val intent = Intent(this, DealCollectionViewActivity ::class.java)
                intent.putExtra("Name", Name)
                intent.putExtra("resid", residtrimmed.trim())

                startActivity(intent)

            }
            if (marker.title == "Search Center"){
                hashMap.clear()
                movedlat = marker.position.latitude
                movedlong = marker.position.longitude
                getrangeformoveduser(movedlat,movedlong)
                mMap.clear()

                mMap.addCircle(
                    CircleOptions()
                        .center( LatLng(movedlat, movedlong)).radius(13000.0).strokeColor(Color.RED)
                        .fillColor(0x220000FF)
                        .strokeWidth(5F)
                )
                val usermoved = LatLng(movedlat, movedlong)
                mMap.addMarker(MarkerOptions().position(usermoved).title("Search Center").snippet("You can move this icon and update search").draggable(true).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
            }
            true


        })



        mMap.setOnMarkerDragListener(object : OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {
                // TODO Auto-generated method stub
                //Here your code
            }

            override fun onMarkerDragEnd(marker: Marker) {
                Log.i("tt", marker.position.toString())

                movedlat = marker.position.latitude
                movedlong = marker.position.longitude

            }

            override fun onMarkerDrag(marker: Marker) {
                // TODO Auto-generated method stub
            }
        })
    }



    fun getrangeformoveduser(Lat: Double = 0.0, Long: Double = 0.0){
        data.clear()
        val ref =
            FirebaseDatabase.getInstance().getReference("GeoClients")
        val geoFire = GeoFire(ref)

        val geoQuery = geoFire.queryAtLocation(
            GeoLocation(Lat, Long),
            16.0
        )

        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                Log.i("keypass", String.format("Provider %s is within your search range [%f,%f]", key, location.latitude, location.longitude))
                val loc1 = Location("")
                loc1.latitude = location.latitude
                loc1.longitude = location.longitude

                val loc2 = Location("")
                loc2.latitude = Lat
                loc2.longitude = Long

                val distanceInMeters = loc1.distanceTo(loc2)
                val distanceInMiles = distanceInMeters/1609.34
                hashMap.put(distanceInMiles , key)
            }

            override fun onKeyExited(key: String) {
                Log.i("TAG", String.format("Provider %s is no longer in the search area", key))
            }

            override fun onKeyMoved(key: String, location: GeoLocation) {
                Log.i("TAG", String.format("Provider %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude))
            }

            override fun onGeoQueryReady() {
                Log.i("TAG", "onGeoQueryReady")
                    getdeals()
                val Allcategory = findViewById<Button>(R.id.ALLcat)
                val food = findViewById<Button>(R.id.Food)
                val bev = findViewById<Button>(R.id.Beverages)
                val act = findViewById<Button>(R.id.Activitys)
                Allcategory.isSelected = !Allcategory.isSelected

            }

            override fun onGeoQueryError(error: DatabaseError) {
                Log.e("TAG", "error: " + error)
            }
        })
    }

    fun getrange(){
        val ref =
            FirebaseDatabase.getInstance().getReference("GeoClients")
        val geoFire = GeoFire(ref)

        val geoQuery = geoFire.queryAtLocation(
            GeoLocation(userlat.toDouble(), userlong.toDouble()),
            16.0
        )

        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                Log.i("keypass", String.format("Provider %s is within your search range [%f,%f]", key, location.latitude, location.longitude))
                val loc1 = Location("")
                loc1.latitude = location.latitude
                loc1.longitude = location.longitude

                val loc2 = Location("")
                loc2.latitude = userlat.toDouble()
                loc2.longitude = userlong.toDouble()

                val distanceInMeters = loc1.distanceTo(loc2)
                val distanceInMiles = distanceInMeters/1675.34
                hashMap.put(distanceInMiles , key)
            }

            override fun onKeyExited(key: String) {
                Log.i("TAG", String.format("Provider %s is no longer in the search area", key))
            }

            override fun onKeyMoved(key: String, location: GeoLocation) {
                Log.i("TAG", String.format("Provider %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude))
            }

            override fun onGeoQueryReady() {
                Log.i("TAG", "onGeoQueryReady")
                getdeals()
            }

            override fun onGeoQueryError(error: DatabaseError) {
                Log.e("TAG", "error: " + error)
            }
        })
    }


    private fun getdeals() {

        Log.i("TAG","general ran")

        newRecyclerView = findViewById(R.id.recyclerviewfilter)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        val db = FirebaseFirestore.getInstance()

        var i = 0
        val sorted = hashMap.toSortedMap()
        Log.i("uidkeymap", sorted.toString())
        for (key in sorted) {
            Log.i("uidkeymap", key.component2().toString())// or your logic to catch the "B"

            val docRef = db.collection("ClientsAccounts").whereEqualTo("resid",key.component2().toString())
            docRef.get()
                .addOnSuccessListener { documents ->

                    for (document in documents.documents) {
                        val myObject =
                            document.toObject(Client::class.java)

                        Log.e("Object", myObject?.Clientaddy.toString())
                        data.add(myObject!!)
                        Log.d("datasize", data.size.toString())
//                        val adapter = CustomAdapter(data, userlat, userlong)
//                        newRecyclerView.adapter = adapter
                        Log.i("uidkey", myObject.Clientaddy)
                        val newclient = LatLng(myObject.Restlat.toDouble(), myObject.Restlong.toDouble())
                        mMap.addMarker(MarkerOptions().position(newclient).title(myObject.Clientname).snippet("Click for more info.... \n ID:" +myObject.resid))
                        val adapter = Businessmapadapter(data, userlat, userlong)
                        newRecyclerView.adapter = adapter
                    }
                }


                .addOnFailureListener { exception ->
                    Log.d("test", "get failed with ", exception)
                }



            //check loop completion and update adapter screen
//            i++
//            if(i == (keys.size)){
//                val adapter = CustomAdapter(data,userlat,userlong)
//                newRecyclerView.adapter = adapter
//            }

        }
        //update feed even if no results were returned
        val adapter = Businessmapadapter(data, userlat, userlong)
        newRecyclerView.adapter = adapter
    }
    fun filtersClients(){

        filtereddata.clear()

        if (dealtype == "Food"){
            for (deal in data){
                if (deal.Food >0){
                    filtereddata.add(deal)
                }
            }
        }
        if (dealtype == "Beverage"){
            for (deal in data){
                if (deal.Beverage >0){
                    filtereddata.add(deal)
                }
            }
        }
        if (dealtype == "Entertainment"){
            for (deal in data){
                if (deal.Entertainment >0){
                    filtereddata.add(deal)
                }
            }
        }
        if (dealtype == ""){
            for (deal in data){
                    filtereddata.add(deal)
            }
        }
        val adapter = Businessmapadapter(filtereddata, userlat, userlong)
        newRecyclerView.adapter = adapter
    }
    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setVisibility(View.INVISIBLE)


        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Swipe Deals")
            intent.putExtra("desc","*The Business Reveal page allows you to see business within 10 miles of your location \n\n * You are able to move your icon around the map to search for businesses in specfic locations. \n\n * You can use the filter in the middle of the screen to find businesses with only certain deals types. \n\n * After the businesses near your location load, you can filter the results and search for a specific buisness by using the seachbar at the top of the screen. \n\n * If you click a business flag on the map you can then click the more info button to see all of that businesses deals.")
            startActivity(intent)
        }
        title.setText("Business Reveal")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.BusinesssReveal

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.SavedDeals -> {
                    val intent = Intent(this, UserSavedDealsActivity::class.java)
                    startActivity(intent);
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