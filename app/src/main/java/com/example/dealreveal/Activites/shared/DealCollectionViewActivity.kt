package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.admins.ApprovedealsActivity
import com.example.dealreveal.Activites.client.ClientsettingsActivity
import com.example.dealreveal.Activites.client.InitalpostnewdealActivity
import com.example.dealreveal.Activites.shared.DealRevealActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*
import kotlinx.android.synthetic.main.activity_deal_collection_view.*
import kotlinx.android.synthetic.main.dayfilters.*



class DealcollectionViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)

class DealCollectionViewActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    private lateinit var newRecyclerView: RecyclerView
    private var gridlayoutManager: GridLayoutManager? = null
    private lateinit var newArrayList: ArrayList<Pendingapproval>
    private lateinit var querylist: CollectionReference
    var AllDeals = "yes"
    var dealtype = ""
    val daytype: ArrayList<String> = ArrayList()
    var AllDays = "yes"
    val admincode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal_collection_view)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

        newRecyclerView = findViewById(R.id.recyclerview1)
        gridlayoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        newRecyclerView?.layoutManager = gridlayoutManager
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()

        getUserdata()
        headerandbottom()
        daybuttonsetup()
        Catsetup()

    }

    private fun Catsetup() {
        val Allcategory = findViewById<Button>(R.id.ALLcat)
        val food = findViewById<Button>(R.id.Food)
        val bev = findViewById<Button>(R.id.Beverages)
        val act = findViewById<Button>(R.id.Activitys)
        Allcategory.isSelected = !Allcategory.isSelected

        Allcategory.setOnClickListener {
             AllDeals = "yes"
            Allcategory.isSelected = !Allcategory.isSelected
            food.isSelected = false
            bev.isSelected = false
            act.isSelected = false
            updaterecycler()

            }
        food.setOnClickListener {
            dealtype = "Food"
            AllDeals = "no"
            food.isSelected = !food.isSelected
            Allcategory.isSelected = false
            bev.isSelected = false
            act.isSelected = false
            updaterecycler()
        }

        bev.setOnClickListener {
            dealtype = "Beverage"
            AllDeals = "no"
            bev.isSelected = !bev.isSelected
            Allcategory.isSelected = false
            food.isSelected = false
            act.isSelected = false
            updaterecycler()
        }
        act.setOnClickListener {
            dealtype = "Entertainment"
            AllDeals = "no"
            act.isSelected = !act.isSelected
            Allcategory.isSelected = false
            bev.isSelected = false
            food.isSelected = false
            updaterecycler()
        }
    }

    private fun daybuttonsetup() {
        val Allday = findViewById<Button>(R.id.ALLDAY)
        val MON = findViewById<Button>(R.id.MON)
        val TUE = findViewById<Button>(R.id.TUE)
        val WED = findViewById<Button>(R.id.WED)
        val THU = findViewById<Button>(R.id.THU)
        val FRI = findViewById<Button>(R.id.FRI)
        val SAT = findViewById<Button>(R.id.SAT)
        val SUN = findViewById<Button>(R.id.SUN)
        Allday.isSelected = !Allday.isSelected

        Allday.setOnClickListener {

            AllDays = "yes"
            daytype.clear()
            Allday.isSelected = !Allday.isSelected
            MON.isSelected = false
            TUE.isSelected = false
            WED.isSelected = false
            THU.isSelected = false
            FRI.isSelected = false
            SAT.isSelected = false
            SUN.isSelected = false
            updaterecycler()

        }
        MON.setOnClickListener {
            AllDays = "no"
            daytype.clear()
            daytype.add("MON")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            MON.isSelected = !MON.isSelected
            Allday.isSelected = false
            TUE.isSelected = false
            WED.isSelected = false
            THU.isSelected = false
            FRI.isSelected = false
            SAT.isSelected = false
            SUN.isSelected = false
            updaterecycler()


        }
        TUE.setOnClickListener {
            AllDays = "no"
            daytype.clear()
            daytype.add("TUE")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            TUE.isSelected = !TUE.isSelected
            Allday.isSelected = false
            MON.isSelected = false
            WED.isSelected = false
            THU.isSelected = false
            FRI.isSelected = false
            SAT.isSelected = false
            SUN.isSelected = false
            updaterecycler()

        }
        WED.setOnClickListener {
            AllDays = "no"
            daytype.clear()
            daytype.add("WED")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            WED.isSelected = !WED.isSelected
            Allday.isSelected = false
            MON.isSelected = false
            TUE.isSelected = false
            THU.isSelected = false
            FRI.isSelected = false
            SAT.isSelected = false
            SUN.isSelected = false
            updaterecycler()

        }
        THU.setOnClickListener {
            AllDays = "no"
            daytype.clear()
            daytype.add("THU")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            THU.isSelected = !THU.isSelected
            Allday.isSelected = false
            MON.isSelected = false
            TUE.isSelected = false
            WED.isSelected = false
            FRI.isSelected = false
            SAT.isSelected = false
            SUN.isSelected = false
            updaterecycler()

        }
        FRI.setOnClickListener {
            AllDays = "no"
            daytype.clear()
            daytype.add("FRI")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            FRI.isSelected = !FRI.isSelected
            Allday.isSelected = false
            MON.isSelected = false
            TUE.isSelected = false
            THU.isSelected = false
            WED.isSelected = false
            SAT.isSelected = false
            SUN.isSelected = false
            updaterecycler()

        }
        SAT.setOnClickListener {
            AllDays = "no"
            daytype.clear()
            daytype.add("SAT")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            SAT.isSelected = !SAT.isSelected
            Allday.isSelected = false
            MON.isSelected = false
            TUE.isSelected = false
            THU.isSelected = false
            FRI.isSelected = false
            WED.isSelected = false
            SUN.isSelected = false
            updaterecycler()

        }
        SUN.setOnClickListener {
            AllDays = "no"
            daytype.clear()
            daytype.add("SUN")
            daytype.add("MON,TUE,WED,THU,FRI")
            daytype.add("MON,TUE,WED,THU,FRI,SAT,SUN")
            SUN.isSelected = !SUN.isSelected
            Allday.isSelected = false
            MON.isSelected = false
            TUE.isSelected = false
            THU.isSelected = false
            FRI.isSelected = false
            WED.isSelected = false
            SAT.isSelected = false
            updaterecycler()

        }

        }



    private fun getUserdata() {

        val db = FirebaseFirestore.getInstance()
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid

        val query = db.collection("ClientDeals1").document(currentuser).collection(currentuser)
        querylist = query
        val options = FirestoreRecyclerOptions.Builder<Pendingapproval>()
            .setQuery(querylist, Pendingapproval::class.java)
            .setLifecycleOwner(this).build()

        Log.d(
            "NumberGenerated1",
            query.whereEqualTo("Title","Free").toString()
        )


        val adapter1 =
            object : FirestoreRecyclerAdapter<Pendingapproval, DealcollectionViewholder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): DealcollectionViewholder {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.gridlayoutdeals, parent, false)
                    return DealcollectionViewholder(itemView)
                }

                override fun onBindViewHolder(
                    holder: DealcollectionViewholder,
                    position: Int,
                    model: Pendingapproval
                ) {
                    val companytitle = findViewById<TextView>(R.id.companytitle)
                    companytitle.setText(model.RestaurantName)
                    val titleImage: ImageView =
                        holder.itemView.findViewById(R.id.icon_image_view)
                    val tvheading: TextView = holder.itemView.findViewById(R.id.title_text_view)
                    tvheading.text = model.Title
                    Glide.with(this@DealCollectionViewActivity)
                        .load(model.MealImageUrl.toString())
                        .into(titleImage)
                    holder.itemView.setOnClickListener {
                        Log.i("test", "im a god")

                        var Address = model.Address
                        var CompanyURL = model.CompanyURL
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


                        val intent =
                            Intent(this@DealCollectionViewActivity, DealRevealActivity::class.java)
                        intent.putExtra("Address", Address)
                        intent.putExtra("CompanyURL", CompanyURL)
                        intent.putExtra("DayofDeal", DayofDeal)
                        intent.putExtra("EndTime", EndTime)
                        intent.putExtra("EndTimeNumber", EndTimeNumber)
                        intent.putExtra("Facebook", Facebook)
                        intent.putExtra("MealImageUrl", MealImageUrl)
                        intent.putExtra("PhoneNumber", PhoneNumber)
                        intent.putExtra("RestaurantName", RestaurantName)
                        intent.putExtra("StartTime", StartTime)
                        intent.putExtra("StartTimeNumber", StartTimeNumber)
                        intent.putExtra("Title", Title)
                        intent.putExtra("Yelp", Yelp)
                        intent.putExtra("category", category)
                        intent.putExtra("date", date)
                        intent.putExtra("description", description)
                        intent.putExtra("latitude", latitude)
                        intent.putExtra("longitude", longitude)
                        intent.putExtra("price", price)
                        intent.putExtra("resid", resid)
                        intent.putExtra("uid", uid)
                        intent.putExtra("admincheck", "pendingdeal")


                        startActivity(intent)

                    }

                }


            }

        recyclerview1.adapter = adapter1
        recyclerview1.layoutManager = gridlayoutManager

    }

    fun updaterecycler(){
        Log.d("AllDeals", AllDeals)
        Log.d("AllDays", AllDays)
        Log.d("dealtype", dealtype.toString())

        if(AllDeals == "yes" && AllDays == "yes"){
            updatenofilter()
        }
        if(AllDeals == "no" && AllDays == "yes"){
            updatedealfilter()
            Log.d("dealtype", "fuck")
        }
        if(AllDeals == "yes" && AllDays == "no"){
            updateDayfilter()
        }
        if(AllDeals == "no" && AllDays == "no"){
            updatedayanddealfiler()
        }
    }

    fun updateDayfilter(){
        val options = FirestoreRecyclerOptions.Builder<Pendingapproval>()
            .setQuery(querylist.whereIn("DayofDeal", daytype), Pendingapproval::class.java)
            .setLifecycleOwner(this).build()

        val adapter1 =
            object : FirestoreRecyclerAdapter<Pendingapproval, DealcollectionViewholder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): DealcollectionViewholder {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.gridlayoutdeals, parent, false)
                    return DealcollectionViewholder(itemView)
                }

                override fun onBindViewHolder(
                    holder: DealcollectionViewholder,
                    position: Int,
                    model: Pendingapproval
                ) {
                    val titleImage: ImageView =
                        holder.itemView.findViewById(R.id.icon_image_view)
                    val tvheading: TextView = holder.itemView.findViewById(R.id.title_text_view)
                    tvheading.text = model.Title
                    Glide.with(this@DealCollectionViewActivity)
                        .load(model.MealImageUrl.toString())
                        .into(titleImage)
                    holder.itemView.setOnClickListener {
                        Log.i("test", "im a god")

                        var Address = model.Address
                        var CompanyURL = model.CompanyURL
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


                        val intent =
                            Intent(this@DealCollectionViewActivity, DealRevealActivity::class.java)
                        intent.putExtra("Address", Address)
                        intent.putExtra("CompanyURL", CompanyURL)
                        intent.putExtra("DayofDeal", DayofDeal)
                        intent.putExtra("EndTime", EndTime)
                        intent.putExtra("EndTimeNumber", EndTimeNumber)
                        intent.putExtra("Facebook", Facebook)
                        intent.putExtra("MealImageUrl", MealImageUrl)
                        intent.putExtra("PhoneNumber", PhoneNumber)
                        intent.putExtra("RestaurantName", RestaurantName)
                        intent.putExtra("StartTime", StartTime)
                        intent.putExtra("StartTimeNumber", StartTimeNumber)
                        intent.putExtra("Title", Title)
                        intent.putExtra("Yelp", Yelp)
                        intent.putExtra("category", category)
                        intent.putExtra("date", date)
                        intent.putExtra("description", description)
                        intent.putExtra("latitude", latitude)
                        intent.putExtra("longitude", longitude)
                        intent.putExtra("price", price)
                        intent.putExtra("resid", resid)
                        intent.putExtra("uid", uid)
                        intent.putExtra("admincheck", "pendingdeal")


                        startActivity(intent)

                    }

                }


            }

        recyclerview1.adapter = adapter1
        recyclerview1.layoutManager = gridlayoutManager
    }

    fun updatedayanddealfiler(){
        val options = FirestoreRecyclerOptions.Builder<Pendingapproval>()
            .setQuery(querylist.whereIn("DayofDeal", daytype).whereEqualTo("category",dealtype), Pendingapproval::class.java)
            .setLifecycleOwner(this).build()

        val adapter1 =
            object : FirestoreRecyclerAdapter<Pendingapproval, DealcollectionViewholder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): DealcollectionViewholder {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.gridlayoutdeals, parent, false)
                    return DealcollectionViewholder(itemView)
                }

                override fun onBindViewHolder(
                    holder: DealcollectionViewholder,
                    position: Int,
                    model: Pendingapproval
                ) {
                    val titleImage: ImageView =
                        holder.itemView.findViewById(R.id.icon_image_view)
                    val tvheading: TextView = holder.itemView.findViewById(R.id.title_text_view)
                    tvheading.text = model.Title
                    Glide.with(this@DealCollectionViewActivity)
                        .load(model.MealImageUrl.toString())
                        .into(titleImage)
                    holder.itemView.setOnClickListener {
                        Log.i("test", "im a god")

                        var Address = model.Address
                        var CompanyURL = model.CompanyURL
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


                        val intent =
                            Intent(this@DealCollectionViewActivity, DealRevealActivity::class.java)
                        intent.putExtra("Address", Address)
                        intent.putExtra("CompanyURL", CompanyURL)
                        intent.putExtra("DayofDeal", DayofDeal)
                        intent.putExtra("EndTime", EndTime)
                        intent.putExtra("EndTimeNumber", EndTimeNumber)
                        intent.putExtra("Facebook", Facebook)
                        intent.putExtra("MealImageUrl", MealImageUrl)
                        intent.putExtra("PhoneNumber", PhoneNumber)
                        intent.putExtra("RestaurantName", RestaurantName)
                        intent.putExtra("StartTime", StartTime)
                        intent.putExtra("StartTimeNumber", StartTimeNumber)
                        intent.putExtra("Title", Title)
                        intent.putExtra("Yelp", Yelp)
                        intent.putExtra("category", category)
                        intent.putExtra("date", date)
                        intent.putExtra("description", description)
                        intent.putExtra("latitude", latitude)
                        intent.putExtra("longitude", longitude)
                        intent.putExtra("price", price)
                        intent.putExtra("resid", resid)
                        intent.putExtra("uid", uid)
                        intent.putExtra("admincheck", "pendingdeal")


                        startActivity(intent)

                    }

                }


            }

        recyclerview1.adapter = adapter1
        recyclerview1.layoutManager = gridlayoutManager
    }

    fun updatedealfilter(){
        val options = FirestoreRecyclerOptions.Builder<Pendingapproval>()
            .setQuery(querylist.whereEqualTo("category",dealtype), Pendingapproval::class.java)
            .setLifecycleOwner(this).build()

        val adapter1 =
            object : FirestoreRecyclerAdapter<Pendingapproval, DealcollectionViewholder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): DealcollectionViewholder {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.gridlayoutdeals, parent, false)
                    return DealcollectionViewholder(itemView)
                }

                override fun onBindViewHolder(
                    holder: DealcollectionViewholder,
                    position: Int,
                    model: Pendingapproval
                ) {
                    val titleImage: ImageView =
                        holder.itemView.findViewById(R.id.icon_image_view)
                    val tvheading: TextView = holder.itemView.findViewById(R.id.title_text_view)
                    tvheading.text = model.Title
                    Glide.with(this@DealCollectionViewActivity)
                        .load(model.MealImageUrl.toString())
                        .into(titleImage)
                    holder.itemView.setOnClickListener {
                        Log.i("test", "im a god")

                        var Address = model.Address
                        var CompanyURL = model.CompanyURL
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


                        val intent =
                            Intent(this@DealCollectionViewActivity, DealRevealActivity::class.java)
                        intent.putExtra("Address", Address)
                        intent.putExtra("CompanyURL", CompanyURL)
                        intent.putExtra("DayofDeal", DayofDeal)
                        intent.putExtra("EndTime", EndTime)
                        intent.putExtra("EndTimeNumber", EndTimeNumber)
                        intent.putExtra("Facebook", Facebook)
                        intent.putExtra("MealImageUrl", MealImageUrl)
                        intent.putExtra("PhoneNumber", PhoneNumber)
                        intent.putExtra("RestaurantName", RestaurantName)
                        intent.putExtra("StartTime", StartTime)
                        intent.putExtra("StartTimeNumber", StartTimeNumber)
                        intent.putExtra("Title", Title)
                        intent.putExtra("Yelp", Yelp)
                        intent.putExtra("category", category)
                        intent.putExtra("date", date)
                        intent.putExtra("description", description)
                        intent.putExtra("latitude", latitude)
                        intent.putExtra("longitude", longitude)
                        intent.putExtra("price", price)
                        intent.putExtra("resid", resid)
                        intent.putExtra("uid", uid)
                        intent.putExtra("admincheck", "pendingdeal")


                        startActivity(intent)

                    }

                }


            }

        recyclerview1.adapter = adapter1
        recyclerview1.layoutManager = gridlayoutManager
    }

    fun updatenofilter(){
        val options = FirestoreRecyclerOptions.Builder<Pendingapproval>()
            .setQuery(querylist, Pendingapproval::class.java)
            .setLifecycleOwner(this).build()

        val adapter1 =
            object : FirestoreRecyclerAdapter<Pendingapproval, DealcollectionViewholder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): DealcollectionViewholder {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.gridlayoutdeals, parent, false)
                    return DealcollectionViewholder(itemView)
                }

                override fun onBindViewHolder(
                    holder: DealcollectionViewholder,
                    position: Int,
                    model: Pendingapproval
                ) {
                    val titleImage: ImageView =
                        holder.itemView.findViewById(R.id.icon_image_view)
                    val tvheading: TextView = holder.itemView.findViewById(R.id.title_text_view)
                    tvheading.text = model.Title
                    Glide.with(this@DealCollectionViewActivity)
                        .load(model.MealImageUrl.toString())
                        .into(titleImage)
                    holder.itemView.setOnClickListener {
                        Log.i("test", "im a god")

                        var Address = model.Address
                        var CompanyURL = model.CompanyURL
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


                        val intent =
                            Intent(this@DealCollectionViewActivity, DealRevealActivity::class.java)
                        intent.putExtra("Address", Address)
                        intent.putExtra("CompanyURL", CompanyURL)
                        intent.putExtra("DayofDeal", DayofDeal)
                        intent.putExtra("EndTime", EndTime)
                        intent.putExtra("EndTimeNumber", EndTimeNumber)
                        intent.putExtra("Facebook", Facebook)
                        intent.putExtra("MealImageUrl", MealImageUrl)
                        intent.putExtra("PhoneNumber", PhoneNumber)
                        intent.putExtra("RestaurantName", RestaurantName)
                        intent.putExtra("StartTime", StartTime)
                        intent.putExtra("StartTimeNumber", StartTimeNumber)
                        intent.putExtra("Title", Title)
                        intent.putExtra("Yelp", Yelp)
                        intent.putExtra("category", category)
                        intent.putExtra("date", date)
                        intent.putExtra("description", description)
                        intent.putExtra("latitude", latitude)
                        intent.putExtra("longitude", longitude)
                        intent.putExtra("price", price)
                        intent.putExtra("resid", resid)
                        intent.putExtra("uid", uid)
                        intent.putExtra("admincheck", "pendingdeal")


                        startActivity(intent)

                    }

                }


            }

        recyclerview1.adapter = adapter1
        recyclerview1.layoutManager = gridlayoutManager
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
        title.setText("Your Deals")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.YourDeals

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.YourDeals -> {
                    val intent = Intent(this, DealCollectionViewActivity::class.java)
//                    startActivity(intent);
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
