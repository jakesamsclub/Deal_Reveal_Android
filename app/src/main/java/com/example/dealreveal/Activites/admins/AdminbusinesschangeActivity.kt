package com.example.dealreveal.Activites.admins

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
import com.example.dealreveal.Activites.AdminApproveClients
import com.example.dealreveal.Activites.NewClientsViewHolder
import com.example.dealreveal.Activites.auth1
import com.example.dealreveal.Activites.client.Businessupdate1Activity
import com.example.dealreveal.Activites.shared.Businesschangeclass
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*


class AdminbusinesschangeActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Businesschangeclass>
    lateinit var imageID : Array<Int>
    lateinit var heading : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminbusinesschange)
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        headerandbottom()
        auth1 = FirebaseAuth.getInstance()


        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()
        getUserdata()
    }

        private fun getUserdata() {
            val db = FirebaseFirestore.getInstance()

            val query = db.collection("Businesschangerequest1")
            val config = PagedList.Config.Builder().setEnablePlaceholders(false)
                .setPrefetchDistance(1)
                .setPageSize(1)
                .build()

            val options = FirestorePagingOptions.Builder<Businesschangeclass>().setQuery(query,config, Businesschangeclass::class.java)
                .setLifecycleOwner(this).build()
            val adapter = object: FirestorePagingAdapter<Businesschangeclass, NewClientsViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewClientsViewHolder {
                    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
                    return NewClientsViewHolder(itemView)
                }

                override fun onBindViewHolder(holder: NewClientsViewHolder, position: Int, model: Businesschangeclass) {
                    val titleImage : ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                    val tvheading : TextView = holder.itemView.findViewById(R.id.tvheading)
                    tvheading.text = model.business
                    Glide.with(this@AdminbusinesschangeActivity)
                        .load(model.avatar.toString())
                        .into(titleImage)

                    holder.itemView.setOnClickListener {

                        val intent = Intent(this@AdminbusinesschangeActivity, Businessupdate1Activity::class.java)

                        Log.i("test", "im a god1")
                        var Address = model.Address
                        var Changed = model.Changed
                        var CompanyURL = model.CompanyURL
                        var Facebook = model.Facebook
                        var PhoneNumber = model.PhoneNumber
                        var Pointofcontact = model.Pointofcontact
                        var Yelp = model.Yelp
                        var avatar = model.avatar
                        var business = model.business
                        var date = model.date
                        var latitude = model.latitude
                        var longitude = model.longitude
                        var resid = model.resid


                        intent.putExtra("Address",Address)
                        intent.putExtra("Avatar",avatar)
                        intent.putExtra("date",date)
                        intent.putExtra("CompanyURL",CompanyURL)
                        intent.putExtra("Facebook",Facebook)
                        intent.putExtra("Phonenumber",PhoneNumber)
                        intent.putExtra("RestaurantName",business)
                        intent.putExtra("Yelp",Yelp)
                        intent.putExtra("latitude",latitude)
                        intent.putExtra("longitude",longitude)
                        intent.putExtra("resid",resid)
                        intent.putExtra("POC",Pointofcontact)
                        intent.putExtra("Changed",Changed)
                        intent.putExtra("adminaccess","adminaccess")

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
            title.setText("Business Changes")

            val bottomNavigationView: BottomNavigationView
            bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
            bottomNavigationView.selectedItemId = R.id.update
            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.client -> {
                        val intent = Intent(this, AdminApproveClients::class.java)
                        startActivity(intent);
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

                        true
                    }
                    else -> false
                }
            }
        }


    }