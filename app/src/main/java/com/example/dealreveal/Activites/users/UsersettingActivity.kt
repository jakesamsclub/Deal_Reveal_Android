package com.example.dealreveal.Activites.users

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.UserSavedDealsActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Startscreen
import com.example.dealreveal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class UsersettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usersetting)
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        buttonsetup()
        headerandbottom()
    }

    private fun buttonsetup(){

        val Locationservices = findViewById<Button>(R.id.Locationservices)
        Locationservices.setOnClickListener {

        }
        val recommend = findViewById<Button>(R.id.recommendfriend)
        recommend.setOnClickListener {

        }
        val Removealldealreminder = findViewById<Button>(R.id.Removealldealreminder)
        Removealldealreminder.setOnClickListener {

        }
        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext, Startscreen::class.java))
            finish()
        }
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
        title.setText("")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.Settings

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.SavedDeals -> {
                    val intent = Intent(this, UserSavedDealsActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.RandomDeals -> {

                    val intent = Intent(this, DealswipeActivity::class.java)
                    startActivity(intent);

                    true

                }
                R.id.DealReveal -> {
                    val intent = Intent(this, DealRevealfilterActivity::class.java)
                    startActivity(intent);

                    true
                }
                R.id.BusinesssReveal -> {
                    val intent = Intent(this, BusinessrevealwithmapActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.Settings -> {

                    true
                }
                else -> false
            }
        }
    }
}


