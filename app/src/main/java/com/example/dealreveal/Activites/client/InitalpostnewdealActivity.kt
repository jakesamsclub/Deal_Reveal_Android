package com.example.dealreveal.Activites.client

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.dealreveal.Activites.PendingapprovalActivity
import com.example.dealreveal.Activites.shared.ClientCollectionDealActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*


class InitalpostnewdealActivity : AppCompatActivity() {

    lateinit var filepath : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initalpostnewdeal)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        headerandbottom()
        val Photo = findViewById<ImageView>(R.id.imageView6)
        Photo.setOnClickListener {
            startFileChooder()
        }
        val nextbutton = findViewById<Button>(R.id.button32)
        nextbutton.setOnClickListener {

            if (!::filepath.isInitialized) {
                println("You must select a photo for this deal")
                Toast.makeText(applicationContext, "You must select a photo for this deal", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else {

                val intent = Intent(this, PostNewDealInfoActivity::class.java)

                intent.putExtra("avatar", filepath)

                startActivity(intent)
            }
        }



    }
    private fun startFileChooder() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose Picture"), 111)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null) {
            val imageViewv = findViewById<ImageView>(R.id.imageView6)
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageViewv.setImageBitmap(bitmap)
        }
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
        title.setText("Post a New Deal")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.NewDeal

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