package com.example.dealreveal.Activites.admins

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R

class HodorActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hodor)

        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener{
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("")

        val hodortext = findViewById<EditText>(R.id.editTextTextPersonName4)
        val verifyhodor = findViewById<Button>(R.id.button22)


        verifyhodor.setOnClickListener{
            var hodortextstring = hodortext.text.toString()
            Log.i("test", hodortextstring)
            if(hodortextstring == "hodor1"){
                val intent = Intent(this, AdminloginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Bitch no", Toast.LENGTH_SHORT).show()
            }
        }


    }

    }