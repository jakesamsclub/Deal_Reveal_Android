package com.example.dealreveal.Activites

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.client.DealRevealActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.R
import com.google.android.material.imageview.ShapeableImageView

class collectionviewadapter(private val mList: List<Pendingapproval>) : RecyclerView.Adapter<collectionviewadapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dealcellformat, parent, false)

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


        holder.distance.text = ItemsViewModel.Title

        //priceset

        holder.Price.text = ""

        holder.itemView.setOnClickListener {
            Log.i("test", "im a god")


            var Address = ItemsViewModel.Address
            var CompanyURL = ItemsViewModel.CompanyURL
            var DayofDeal = ItemsViewModel.DayofDeal
            var EndTime = ItemsViewModel.EndTime
            var EndTimeNumber = ItemsViewModel.EndTimeNumber
            var Facebook = ItemsViewModel.Facebook
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
            var Insta = ItemsViewModel.Insta


            val intent = Intent(holder.itemView.context, DealRevealActivity::class.java)
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
            intent.putExtra("Insta", Insta)
            intent.putExtra("admincheck", "Review")


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