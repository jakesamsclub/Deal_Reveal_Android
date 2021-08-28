package com.example.dealreveal.Activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.admins.ApprovedealsActivity
import com.example.dealreveal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*

class PostNewDealInfoActivity : AppCompatActivity() {

    lateinit var filepath : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_new_deal_info)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)
        headerandbottom()

        val intent = intent
        filepath = intent.getParcelableExtra<Uri>("avatar")!!

        val Titletext = findViewById<TextView>(R.id.editTextTextPersonName19)
        val Pricetext = findViewById<TextView>(R.id.editTextTextPersonName21)
        val Categorytext = findViewById<TextView>(R.id.editTextTextPersonName20)
        val Dayofdealtext = findViewById<TextView>(R.id.editTextTextPersonName22)
        val Dealstarttimetext = findViewById<TextView>(R.id.editTextTextPersonName18)
        val Dealendtimetext = findViewById<TextView>(R.id.editTextTextPersonName17)
        val Descriptiontext = findViewById<TextView>(R.id.editTextTextMultiLine2)

        val nextbutton = findViewById<Button>(R.id.button33)
        nextbutton.setOnClickListener {

            val intent = Intent(this, DealRevealActivity::class.java)
            intent.putExtra("Title", Titletext.text.toString())
            intent.putExtra("Price", Pricetext.text.toString())
            intent.putExtra("Category", Categorytext.text.toString())
            intent.putExtra("Dayofdealtext", Dayofdealtext.text.toString())
            intent.putExtra("Dealstarttimetext", Dealstarttimetext.text.toString())
            intent.putExtra("Dealendtimetext", Dealendtimetext.text.toString())
            intent.putExtra("Description",Descriptiontext.text.toString())
            intent.putExtra("admincheck","")
            intent.putExtra("avatar",filepath)

            startActivity(intent)
        }

    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

//        leftIcon.setVisibility(View.INVISIBLE)
        leftIcon.setOnClickListener {
            finish()
        }
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
                    val intent = Intent(this, ApprovedealsActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.UnderReview-> {
                    val intent = Intent(this, ApprovedealsActivity::class.java)
                    startActivity(intent);

                    true

                }
                R.id.NewDeal -> {

                    true
                }
                R.id.DealAnalytics -> {
                    val intent = Intent(this, ApprovedealsActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.Settings -> {
                    val intent = Intent(this, ApprovedealsActivity::class.java)
                    startActivity(intent);
                    true
                }
                else -> false
            }
        }
    }
}