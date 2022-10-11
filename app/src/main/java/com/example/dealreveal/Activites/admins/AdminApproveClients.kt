package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.admins.AdminbusinesschangeActivity
import com.example.dealreveal.Activites.admins.ApprovedealsActivity
import com.example.dealreveal.Activites.admins.LiarreportActivity
import com.example.dealreveal.Activites.admins.PossibleClient1Activity
import com.example.dealreveal.Activites.client.NewClients
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*


val db = FirebaseFirestore.getInstance()
lateinit var auth1: FirebaseAuth

class NewClientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class AdminApproveClients : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<NewClients>
    lateinit var imageID : Array<Int>
    lateinit var heading : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_approve_clients)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        headerandbottom()
        auth1 = FirebaseAuth.getInstance()
        val intent = intent

        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()
        getUserdata()

    }

    private fun getUserdata() {
        val db = FirebaseFirestore.getInstance()

        val query = db.collection("Possibleclient1")
        val config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPrefetchDistance(1)
            .setPageSize(1)
            .build()

        val options = FirestorePagingOptions.Builder<NewClients>().setQuery(query,config, NewClients::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestorePagingAdapter<NewClients, NewClientsViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewClientsViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
                return NewClientsViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: NewClientsViewHolder, position: Int, model: NewClients) {
                val titleImage : ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                val tvheading : TextView = holder.itemView.findViewById(R.id.tvheading)
                tvheading.text = model.Clientname
                Glide.with(this@AdminApproveClients)
                    .load(model.MealImageUrl.toString())
                    .into(titleImage)
                holder.itemView.setOnClickListener {
                    Log.i("test", "im a god1")
                    var Address = model.Address
                    var CUID = model.CUID
                    var Clientname = model.Clientname
                    var CompanyURL = model.CompanyURL
                    var Date = model.Date
                    var Facebook = model.Facebook
                    var MealImageUrl = model.MealImageUrl
                    var Password = model.Password
                    var PhoneNumber = model.PhoneNumber
                    var PointOfContact = model.PointOfContact
                    var Yelp = model.Yelp
                    var email = model.email
                    var setuptime = model.setuptime


                    val intent = Intent(this@AdminApproveClients, PossibleClient1Activity::class.java)
                    intent.putExtra("Address",Address)
                    intent.putExtra("CUID",CUID)
                    intent.putExtra("Clientname",Clientname)
                    intent.putExtra("CompanyURL",CompanyURL)
                    intent.putExtra("Date",Date)
                    intent.putExtra("Facebook",Facebook)
                    intent.putExtra("MealImageUrl",MealImageUrl)
                    intent.putExtra("Password",Password)
                    intent.putExtra("PhoneNumber",PhoneNumber)
                    intent.putExtra("PointOfContact",PointOfContact)
                    intent.putExtra("Yelp",Yelp)
                    intent.putExtra("email",email)
                    intent.putExtra("setuptime",setuptime)


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
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)


    }



    private fun headerandbottom(){
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
        title.setText("New Clients")


        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.client -> {

                    true
                }
                R.id.deal-> {
                    val intent = Intent(this, ApprovedealsActivity::class.java)
                    startActivity(intent);

                    true

                }
                R.id.liar -> {
                    val intent = Intent(this, LiarreportActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.update -> {
                    val intent = Intent(this, AdminbusinesschangeActivity::class.java)
                    startActivity(intent);
                    true
                }
                else -> false
            }
        }
    }


}
