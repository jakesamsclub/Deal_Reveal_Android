package com.example.dealreveal.Activites.users

import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.Activites.shared.userlat
import com.example.dealreveal.Activites.shared.userlong
import com.example.dealreveal.R
import com.google.android.material.imageview.ShapeableImageView

//
class Swipeadapter(context: Context, resourceId: Int, deals: List<Pendingapproval>): ArrayAdapter<Pendingapproval>(context, resourceId,deals) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val deal = getItem(position)
        val convertView = LayoutInflater.from(parent.context)
            .inflate(R.layout.swipeitem, parent, false)

        val Title: TextView = convertView.findViewById(R.id.titleswipe)
        val Price: TextView = convertView.findViewById(R.id.price)
        val distance: TextView = convertView.findViewById(R.id.distanceswipe)

        Title.text = deal!!.Title

        //priceset
        Price.text = deal.RestaurantName

        val titleImage: ShapeableImageView = convertView.findViewById(R.id.title_imageswipe)
        Glide.with(convertView.getContext())
           .load(deal.MealImageUrl.toString())
            .into(titleImage)

        //calculate distance
        val loc1 = Location("")
        loc1.latitude = deal.latitude.toDouble()
        loc1.longitude = deal.longitude.toDouble()

        val loc2 = Location("")
        loc2.latitude = userlat.toDouble()
        loc2.longitude = userlong.toDouble()


        val distanceInMeters = loc1.distanceTo(loc2)
        val distanceInMiles = distanceInMeters/1609.34
        val rounded = String.format("%.2f", distanceInMiles)
        distance.text = rounded + " Mi away"

        convertView.setOnClickListener {
            Log.i("test", "im a god")
            var Address = deal.Address
            var CompanyURL = deal.CompanyURL
            var DayofDeal = deal.DayofDeal
            var EndTime = deal.EndTime
            var EndTimeNumber = deal.EndTimeNumber
            var Facebook = deal.Facebook
            var Insta = deal.Insta
            var MealImageUrl = deal.MealImageUrl
            var PhoneNumber = deal.PhoneNumber
            var RestaurantName = deal.RestaurantName
            var StartTime = deal.StartTime
            var StartTimeNumber = deal.StartTimeNumber
            var Title = deal.Title
            var Yelp = deal.Yelp
            var category = deal.category
            var date = deal.date
            var description = deal.description
            var latitude = deal.latitude
            var longitude = deal.longitude
            var price = deal.price
            var resid = deal.resid
            var uid = deal.uid


            val intent = Intent(convertView.context, DealRevealUserActivity::class.java)
            intent.putExtra("Address", Address)
            intent.putExtra("CompanyURL", CompanyURL)
            intent.putExtra("DayofDeal", DayofDeal)
            intent.putExtra("EndTime", EndTime)
            intent.putExtra("EndTimeNumber", EndTimeNumber)
            intent.putExtra("Facebook", Facebook)
            intent.putExtra("Insta", Insta)
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


            convertView.getContext().startActivity(intent)
        }

        return convertView
    }
}

