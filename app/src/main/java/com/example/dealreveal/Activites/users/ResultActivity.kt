package com.example.dealreveal.Activites.users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        if (intent.getBooleanExtra("notification", false)) { //Just for confirmation
            var text = intent.getStringExtra("title")
            var msg = intent.getStringExtra("message")

        }
    }
}