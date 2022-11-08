package com.example.dealreveal.Activites.shared

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.R

class HelpOverviewActivity : AppCompatActivity() {

    var pagetitletext = ""
    var desctext = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_overview)

        val intent = intent
        pagetitletext = intent.getStringExtra("page").toString()
        desctext = intent.getStringExtra("desc").toString()

        textsetup()


    }

    private fun textsetup(){

        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        title.setText("User Guide")
        val Pagetitle = findViewById<TextView>(R.id.pagetitle)
        val desc = findViewById<TextView>(R.id.helptext)

        Pagetitle.setText(pagetitletext + " Help Page")
        desc.setText(desctext)
        desc.setFocusable(false);


    }

}