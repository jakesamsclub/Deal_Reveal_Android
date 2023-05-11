package com.example.dealreveal.Activites

import android.content.Intent
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.Activites.users.DealRevealUserActivity
import com.example.dealreveal.R
import com.google.android.material.imageview.ShapeableImageView

class CustomAdapter(private val mList: List<Pendingapproval>, private val lat: String, private val long: String) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_client, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class

        val titleImage: ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
        Glide.with(holder.itemView.getContext())
            .load(ItemsViewModel.MealImageUrl.toString())
            .into(titleImage)

        // sets the Title to the textview from our itemHolder class
        holder.Title.text = ItemsViewModel.Title

        //calculate distance
        val loc1 = Location("")
        loc1.latitude = ItemsViewModel.latitude.toDouble()
        loc1.longitude = ItemsViewModel.longitude.toDouble()

        val loc2 = Location("")
        loc2.latitude = lat.toDouble()
        loc2.longitude = long.toDouble()
        Log.d("user location", lat+"+"+long)

        val distanceInMeters = loc1.distanceTo(loc2)
        val distanceInMiles = distanceInMeters/1609.34
        val rounded = String.format("%.2f", distanceInMiles)
        holder.distance.text = rounded + " Mi away"


        //priceset
        holder.Price.text = ItemsViewModel.RestaurantName

        holder.itemView.setOnClickListener {
            Log.i("test", "im a god")
            var Address = ItemsViewModel.Address
            var CompanyURL = ItemsViewModel.CompanyURL
            var DayofDeal = ItemsViewModel.DayofDeal
            var EndTime = ItemsViewModel.EndTime
            var EndTimeNumber = ItemsViewModel.EndTimeNumber
            var Facebook = ItemsViewModel.Facebook
            var Insta = ItemsViewModel.Insta
            var MealImageUrl = ItemsViewModel.MealImageUrl
            var PhoneNumber = ItemsViewModel.PhoneNumber
            var RestaurantName = ItemsViewModel.RestaurantName
            var StartTime = ItemsViewModel.StartTime
            var StartTimeNumber = ItemsViewModel.StartTimeNumber
            var Title = ItemsViewModel.Title
            var Yelp = ItemsViewModel.Yelp
            var category = ItemsViewModel.category
            var date = ItemsViewModel.date
            var description = ItemsViewModel.description
            var latitude = ItemsViewModel.latitude
            var longitude = ItemsViewModel.longitude
            var price = ItemsViewModel.price
            var resid = ItemsViewModel.resid
            var uid = ItemsViewModel.uid


            val intent = Intent(holder.itemView.context, DealRevealUserActivity::class.java)
            intent.putExtra("Address", Address)
            intent.putExtra("CompanyURL", CompanyURL)
            intent.putExtra("DayofDeal", DayofDeal)
            intent.putExtra("EndTime", EndTime)
            intent.putExtra("EndTimeNumber", EndTimeNumber)
            intent.putExtra("Facebook", Facebook)
            intent.putExtra("Insta",Insta)
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
            intent.putExtra("admincheck", "approve new deal")


            holder.itemView.getContext().startActivity(intent)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val Title: TextView = itemView.findViewById(R.id.tvheading)
        val Price: TextView = itemView.findViewById(R.id.date)
        val distance: TextView = itemView.findViewById(R.id.distance)
    }
}

