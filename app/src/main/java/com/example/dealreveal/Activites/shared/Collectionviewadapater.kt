package com.example.dealreveal.Activites

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.R
import com.google.android.material.imageview.ShapeableImageView
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class collectionviewadapter(private val newslist : ArrayList<Pendingapproval>) :
RecyclerView.Adapter<collectionviewadapter.MyViewHolder>(), Filterable {

    var countryFilterList = ArrayList<Pendingapproval>()

    init {
        countryFilterList = newslist
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gridlayoutdeals,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = newslist[position]
        val url = URL(currentItem.MealImageUrl)
        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        holder.titleImage.setImageBitmap(bmp)
     //   holder.tvheading.text = currentItem.Title

//        val countryHolder = holder as CountryHolder
//        countryHolder.viewBinding.selectCountryText.text = countryFilterList[position]

    }

    override fun getItemCount(): Int {

        return countryFilterList.size

    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val titleImage : ShapeableImageView = itemView.findViewById(R.id.icon_image_view)
    //    val tvheading : TextView = itemView.findViewById(R.id.title_text_view)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = newslist
                } else {
                    val resultList = ArrayList<Pendingapproval>()
                    for (row in newslist) {
                        if (row.Title.contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<Pendingapproval>
                notifyDataSetChanged()
            }

        }
    }





}