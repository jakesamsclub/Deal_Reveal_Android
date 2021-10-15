package com.example.dealreveal.Activites.users

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.client.Client
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.R
import com.example.dealreveal.databinding.ActivityBuisnessrevealBinding
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryEventListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class Buisnessreveal : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityBuisnessrevealBinding
    val keys = ArrayList<String>()
    val data = ArrayList<Client>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBuisnessrevealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getrange()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val user = LatLng(userlat.toDouble(), userlong.toDouble())
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.addMarker(MarkerOptions().position(user).title("You"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(user))

        for (Client in data) {
            Log.i("uidkey", Client.Clientaddy)
            val newclient = LatLng(Client.Restlat.toDouble(), Client.Restlong.toDouble())
            mMap.addMarker(MarkerOptions().position(newclient).title(Client.Clientname))
        }
    }
    fun getrange(){
        val ref =
            FirebaseDatabase.getInstance().getReference("GeoClients")
        val geoFire = GeoFire(ref)

        val geoQuery = geoFire.queryAtLocation(GeoLocation(userlat.toDouble(), userlong.toDouble()),
            100.0
        )

        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                Log.i("keypass", String.format("Provider %s is within your search range [%f,%f]", key, location.latitude, location.longitude))
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

                    getdeals()
            }

            override fun onGeoQueryError(error: DatabaseError) {
                Log.e("TAG", "error: " + error)
            }
        })
    }

    private fun getdeals() {

        Log.i("TAG","general ran")

//        newRecyclerView = findViewById(R.id.recyclerviewfilter)
//        newRecyclerView.layoutManager = LinearLayoutManager(this)
//        newRecyclerView.setHasFixedSize(true)

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        var i = 0
        for (string in keys) {
            Log.i("uidkey", string)// or your logic to catch the "B"

            val docRef = db.collection("ClientsAccounts").whereEqualTo("resid",string)
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
                        mMap.addMarker(MarkerOptions().position(newclient).title(myObject.Clientname))
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

    }
}