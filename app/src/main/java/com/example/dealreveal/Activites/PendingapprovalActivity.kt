package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.admins.ApprovedealsActivity
import com.example.dealreveal.Activites.client.ClientsettingsActivity
import com.example.dealreveal.Activites.client.InitalpostnewdealActivity
import com.example.dealreveal.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*

class PendingapprovalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class RejectedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class PendingapprovalActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    lateinit var auth1: FirebaseAuth
    private lateinit var newRecyclerView: RecyclerView

    val admincode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendingapproval)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        headerandbottom()
        auth1 = FirebaseAuth.getInstance()

        getUserdata()
        val Seepending = findViewById<Button>(R.id.button36)
        Seepending.setOnClickListener {
            getUserdata()
        }
        val Seerejected = findViewById<Button>(R.id.button35)
        Seerejected.setOnClickListener {
            getrejecteddata()
        }
        }

    private fun getUserdata() {
        lateinit var newArrayList: ArrayList<Pendingapproval>
        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        val query = db.collection("ReviewMeals1").document(currentuser).collection(currentuser)
        val options = FirestoreRecyclerOptions.Builder<Pendingapproval>().setQuery(query, Pendingapproval::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<Pendingapproval, PendingapprovalViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingapprovalViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
                return PendingapprovalViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: PendingapprovalViewHolder, position: Int, model: Pendingapproval) {
                val titleImage : ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                val tvheading : TextView = holder.itemView.findViewById(R.id.tvheading)
                tvheading.text = model.Title
                Glide.with(this@PendingapprovalActivity)
                    .load(model.MealImageUrl.toString())
                    .into(titleImage)
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


                    val intent = Intent(this@PendingapprovalActivity, DealRevealActivity::class.java)
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
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

    }
    private fun getrejecteddata() {
        lateinit var newArrayList: ArrayList<RejectedDealsTemplate>
        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        val query = db.collection("RejectedDeals1").document(currentuser).collection(currentuser)
        val options = FirestoreRecyclerOptions.Builder<RejectedDealsTemplate>().setQuery(query, RejectedDealsTemplate::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<RejectedDealsTemplate, RejectedViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RejectedViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
                return RejectedViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: RejectedViewHolder, position: Int, model: RejectedDealsTemplate) {
                val titleImage : ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                val tvheading : TextView = holder.itemView.findViewById(R.id.tvheading)
                tvheading.text = model.Title
                Glide.with(this@PendingapprovalActivity)
                    .load(model.MealImageUrl.toString())
                    .into(titleImage)
                holder.itemView.setOnClickListener {
                    Log.i("test", "im a god")

                    var Address = model.Address
                    var CompanyURL  = model.CompanyURL
                    var DayofDeal = model.DayofDeal
                    var EndTime = model.EndTime
                    var Facebook = model.Facebook
                    var MealImageUrl = model.MealImageUrl
                    var PhoneNumber = model.PhoneNumber
                    var RestaurantName = model.RestaurantName
                    var StartTime = model.StartTime
                    var Title = model.Title
                    var Yelp = model.Yelp
                    var category = model.category
                    var date = model.DateRejected
                    var description = model.description
                    var latitude = model.latitude
                    var longitude = model.longitude
                    var price = model.price
                    var resid = model.resid
                    var uid = model.uid
                    var reason = model.Reason


                    val intent = Intent(this@PendingapprovalActivity, DealRevealActivity::class.java)
                    intent.putExtra("Address",Address)
                    intent.putExtra("CompanyURL",CompanyURL)
                    intent.putExtra("DayofDeal",DayofDeal)
                    intent.putExtra("EndTime",EndTime)
                    intent.putExtra("Facebook",Facebook)
                    intent.putExtra("MealImageUrl",MealImageUrl)
                    intent.putExtra("PhoneNumber",PhoneNumber)
                    intent.putExtra("RestaurantName",RestaurantName)
                    intent.putExtra("StartTime",StartTime)
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
                    intent.putExtra("reason",reason)
                    intent.putExtra("admincheck","rejecteddeal")


                    startActivity(intent)

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
        title.setText("New Deal Status")


        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.UnderReview

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.YourDeals -> {
                    val intent = Intent(this, DealCollectionViewActivity::class.java)
                    startActivity(intent);
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