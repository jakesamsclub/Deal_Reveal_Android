package com.example.dealreveal.Activites.users

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.dealreveal.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.businessmapinfowindowadapter, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvSnippet = view.findViewById<TextView>(R.id.snippet)

        tvTitle.text = marker.title
        if (marker.snippet == "You can move this icon and update search") {
            tvSnippet.text = "Hold down the blue pin to move the search center location \nTap the info button to update results."
        }
        if (marker.snippet != "You can move this icon and update search") {
            tvSnippet.text = "Click the grey info button to see this companys deals"
        }

        tvTitle.setOnClickListener{
            Log.i("keypass", "Im god")
        }

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}