package com.example.dealreveal.Activites.client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.PendingapprovalActivity
import com.example.dealreveal.Activites.SavedViewHolder
import com.example.dealreveal.Activites.shared.*
import com.example.dealreveal.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*
import kotlinx.android.synthetic.main.analyticsalldealfilters.*
import java.util.*

class AnalyticsAllDealActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var newRecyclerView: RecyclerView
    var month = ""
    var year = ""
    var filterdatesearch = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        setContentView(R.layout.activity_analytics_all_deal)
        headerandbottom()
        spinnersetup()
    }

    private fun setyearandmonthfilter() {
        val Monthspinner: Spinner = findViewById(R.id.monthdrop)

        if (Monthspinner.selectedItem == "January"){
            month = "1"
        }
        if (Monthspinner.selectedItem == "February"){
            month = "2"
        }
        if (Monthspinner.selectedItem == "March"){
            month = "3"
        }
        if (Monthspinner.selectedItem == "April"){
            month = "4"
        }
        if (Monthspinner.selectedItem == "May"){
            month = "5"
        }
        if (Monthspinner.selectedItem == "June"){
            month = "6"
        }
        if (Monthspinner.selectedItem == "July"){
            month = "7"
        }
        if (Monthspinner.selectedItem == "August"){
            month = "8"
        }
        if (Monthspinner.selectedItem == "September"){
            month = "9"
        }
        if (Monthspinner.selectedItem == "October"){
            month = "10"
        }
        if (Monthspinner.selectedItem == "November"){
            month = "11"
        }
        if (Monthspinner.selectedItem == "December"){
            month = "12"
        }


        val Yearspinner: Spinner = findViewById(R.id.yeardrop)
        if (Yearspinner.selectedItem == "2021"){
            year = "2021"
        }
        if (Yearspinner.selectedItem == "2022"){
            year = "2022"
        }
        if (Yearspinner.selectedItem == "2023"){
            year = "2023"
        }
        if (Yearspinner.selectedItem == "2024"){
            year = "2024"
        }
        filterdatesearch = month+"-"+year
        Log.d("setyearandmonthfilter", "setyearandmonthfilter:" + filterdatesearch)
        getUserdata()
    }

    fun spinnersetup() {
        var cal = Calendar.getInstance();
        var montharray = cal.get(Calendar.MONTH) ;
        var year = cal.get(Calendar.YEAR);

        Log.d("TAG", "spinnersetup: month"+month+ " year" + year)


        val FilterUpdate = findViewById<Button>(R.id.filteranal)
        FilterUpdate.setOnClickListener(){
            setyearandmonthfilter()
        }

        val Monthspinner: Spinner = findViewById(R.id.monthdrop)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.Month,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            Monthspinner.adapter = adapter
        }
        Monthspinner.setSelection(montharray)

// Create an ArrayAdapter using the string array and a default spinner layout
        val Yearspinner: Spinner = findViewById(R.id.yeardrop)
        ArrayAdapter.createFromResource(
            this,
            R.array.Year,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            Yearspinner.adapter = adapter
        }
        if (year == 2022){
            Yearspinner.setSelection(0)
        }
        if (year == 2023){
            Yearspinner.setSelection(1)
        }
        if (year == 2024){
            Yearspinner.setSelection(2)
        }
        if (year == 2025){
            Yearspinner.setSelection(3)
        }
        setyearandmonthfilter()

    }

        private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)
            leftIcon.isVisible = false

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("Deal Analytics")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.DealAnalytics

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.YourDeals -> {
                    val intent = Intent(this, ClientCollectionDealActivity::class.java)
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
                    val intent = Intent(this, AnalyticsAllDealActivity::class.java)

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
    private fun getUserdata() {
        lateinit var newArrayList: ArrayList<DealAnalyticFormat>
        newRecyclerView = findViewById(R.id.AllDealrecycler)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid


        val query = db.collection("Dealanalytics").document(currentuser).collection(filterdatesearch)
        val options = FirestoreRecyclerOptions.Builder<DealAnalyticFormat>().setQuery(query, DealAnalyticFormat::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<DealAnalyticFormat, SavedViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
                return SavedViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: SavedViewHolder, position: Int, model: DealAnalyticFormat) {
                val titleImage : ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                val tvheading : TextView = holder.itemView.findViewById(R.id.tvheading)
                val date : TextView = holder.itemView.findViewById(R.id.distance)
                val title : TextView = holder.itemView.findViewById(R.id.date)

                tvheading.text = ""
                title.text = model.Title
                date.text = "Anayltics for " + filterdatesearch

                Glide.with(this@AnalyticsAllDealActivity)
                    .load(model.Avatar)
                    .into(titleImage)

                //calculate distance

                //holder setup

                holder.itemView.setOnClickListener {
                    Log.i("test", "im a god")


                    val intent = Intent(this@AnalyticsAllDealActivity, singledealanalyticsinfoActivity::class.java)
                    intent.putExtra("Address",model.Address)
                    intent.putExtra("Alarm",model.Alarm)
                    intent.putExtra("Avatar",model.Avatar)
                    intent.putExtra("Facebook",model.Facebook)
                    intent.putExtra("Feedback",model.Feedback)
                    intent.putExtra("Phone_Number",model.Phone_Number)
                    intent.putExtra("Save_Count",model.Save_Count)
                    intent.putExtra("Screenshot",model.Screenshot)
                    intent.putExtra("Title",model.Title)
                    intent.putExtra("UID",model.UID)
                    intent.putExtra("View_Count_3_To_20_Miles",model.View_Count_3_To_20_Miles)
                    intent.putExtra("View_Count_Less_Than_3_Miles",model.View_Count_Less_Than_3_Miles)
                    intent.putExtra("View_Count_More_Than_20_Miles",model.View_Count_More_Than_20_Miles)
                    intent.putExtra("Website",model.Website)
                    intent.putExtra("Yelp",model.Yelp)
                    intent.putExtra("filterdatesearch",filterdatesearch)

                    startActivity(intent)

                }

            }


        }
        newRecyclerView.adapter = adapter
        newRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}