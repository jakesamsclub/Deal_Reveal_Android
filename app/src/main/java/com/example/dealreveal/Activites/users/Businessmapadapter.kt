package com.example.dealreveal.Activites.users

import android.content.Intent
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.DealCollectionViewActivity
import com.example.dealreveal.Activites.client.Client
import com.example.dealreveal.R
import com.google.android.material.imageview.ShapeableImageView

class Businessmapadapter(private val mList: List<Client>, private val lat: String, private val long: String) : RecyclerView.Adapter<Businessmapadapter.ViewHolder>() {

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
            .load(ItemsViewModel.avatar.toString())
            .into(titleImage)

        // sets the Title to the textview from our itemHolder class
        holder.Title.text = ""

        //calculate distance
        val loc1 = Location("")
        loc1.latitude = ItemsViewModel.Restlat.toDouble()
        loc1.longitude = ItemsViewModel.Restlong.toDouble()

        val loc2 = Location("")
        loc2.latitude = lat.toDouble()
        loc2.longitude = long.toDouble()
        Log.d("user location", lat+"+"+long)

        val distanceInMeters = loc1.distanceTo(loc2)
        val distanceInMiles = distanceInMeters/1609.34
        val rounded = String.format("%.2f", distanceInMiles)
        holder.distance.text = rounded + " Mi away"


        //priceset
        holder.Price.text = ItemsViewModel.Clientname

        holder.itemView.setOnClickListener {
            Log.i("test", "im a god")

            var Name = ItemsViewModel.Clientname
            var resid = ItemsViewModel.resid


            val intent = Intent(holder.itemView.context, DealCollectionViewActivity::class.java)
            intent.putExtra("Name", Name)
            intent.putExtra("residtrimmed", resid)


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

