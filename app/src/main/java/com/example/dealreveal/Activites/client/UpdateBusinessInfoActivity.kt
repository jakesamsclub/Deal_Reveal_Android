package com.example.dealreveal.Activites.client

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R

class UpdateBusinessInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_business_info)
        headerandbottom()
        buttonsetup()
    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            val intent = Intent(this, ClientsettingsActivity::class.java)
            startActivity(intent)
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("")
    }

    private fun buttonsetup(){
        val infotext = findViewById<TextView>(R.id.updatetextinfo)


        val next = findViewById<Button>(R.id.nextbusinessupdate)
        next.setText("Continue")
        next.setOnClickListener {
            val intent = Intent(this, Businessupdate1Activity::class.java)
            startActivity(intent);
        }


        }
    }
