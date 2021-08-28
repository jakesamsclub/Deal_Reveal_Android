package com.example.dealreveal.Activites

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dealreveal.Activites.client.NewClients
import com.example.dealreveal.R
import com.google.android.material.imageview.ShapeableImageView
import java.net.URL


class MyAdapter(private val newslist : ArrayList<NewClients>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_client,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = newslist[position]
        val url = URL(currentItem.MealImageUrl)
        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        holder.titleImage.setImageBitmap(bmp)
        holder.tvheading.text = currentItem.Clientname
    }

    override fun getItemCount(): Int {

        return newslist.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvheading : TextView = itemView.findViewById(R.id.tvheading)
        }

    }
