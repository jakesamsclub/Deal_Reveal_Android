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
import com.example.dealreveal.Activites.SavedViewHolder
import com.example.dealreveal.Activites.client.*
import com.example.dealreveal.Activites.shared.DealfeedbacktActivity
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

class LiarreportActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var newRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liarreport)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        headerandbottom()
        getUserdata()
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
        title.setText("Liar Report")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.liar
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

    private fun getUserdata() {
        lateinit var newArrayList: ArrayList<liar_feedback_class>
        newRecyclerView = findViewById(R.id.AllDealrecycler)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid


        val query = db.collection("Liarsreports1")
        val config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPrefetchDistance(1)
            .setPageSize(1)
            .build()

        val options = FirestorePagingOptions.Builder<liar_feedback_class>().setQuery(query,config, liar_feedback_class::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestorePagingAdapter<liar_feedback_class, SavedViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
                return SavedViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: SavedViewHolder, position: Int, model: liar_feedback_class) {
                val titleImage : ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                val tvheading : TextView = holder.itemView.findViewById(R.id.tvheading)
                val date : TextView = holder.itemView.findViewById(R.id.distance)
                val title : TextView = holder.itemView.findViewById(R.id.date)

                tvheading.text = ""
                title.text = model.Dealname
                date.text = model.Businessname

                Glide.with(this@LiarreportActivity)
                    .load(model.Image)
                    .into(titleImage)

                //calculate distance

                //holder setup

                holder.itemView.setOnClickListener {
                    Log.i("test", "im a god")
                    
                    val intent = Intent(this@LiarreportActivity, DealfeedbacktActivity::class.java)

                    intent.putExtra("dealtitletext",model.Dealname)
                    intent.putExtra("Businessnametext",model.Businessname)
                    intent.putExtra("Avatartext",model.Image)
                    intent.putExtra("dealpricetext",model.Price)
                    intent.putExtra("resid",model.resid1)
                    intent.putExtra("ReviewID",model.ReviewID)
                    intent.putExtra("Date",model.Date)
                    intent.putExtra("uid",model.Dealuid)
                    intent.putExtra("DealFeedback",model.DealFeedback)
                    intent.putExtra("reportkey","liarreview")

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
}