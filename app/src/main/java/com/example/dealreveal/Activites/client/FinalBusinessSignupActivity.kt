package com.example.dealreveal.Activites.client

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Startscreen
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class FinalBusinessSignupActivity : AppCompatActivity() {
    var BusinessName = ""
    var Address = ""
    var Pointofcontact = ""
    var Phonenumber = ""
    var Websiteurl = ""
    var Yelpurl = ""
    var email = ""
    var password = ""
    var fb = ""
    var Insta = ""
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    lateinit var filepath : Uri
    lateinit var mTimePicker: TimePickerDialog
    val mcurrentTime = Calendar.getInstance()
    val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute = mcurrentTime.get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_business_signup)
        headerandbottom()
        buttonsetup()



        }

      fun  buttonsetup(){
          val intent = intent
          BusinessName = intent.getStringExtra("BusinessName").toString()
          Address = intent.getStringExtra("Address").toString()
          Pointofcontact = intent.getStringExtra("Pointofcontact").toString()
          Phonenumber = intent.getStringExtra("Phonenumber").toString()
          Websiteurl = intent.getStringExtra("Websiteurl").toString()
          Yelpurl = intent.getStringExtra("Yelpurl").toString()
          email = intent.getStringExtra("email").toString()
          password = intent.getStringExtra("password").toString()
          Insta = intent.getStringExtra("Insta").toString()
          fb = intent.getStringExtra("FB").toString()
          filepath = intent.getParcelableExtra<Uri>("avatar")!!

          auth1 = FirebaseAuth.getInstance()


          val nextbutton = findViewById<Button>(R.id.button20)
          nextbutton.setOnClickListener {

              if (filepath != null) {
                  var pd = ProgressDialog(this)
                  pd.setTitle("uploading")
                  var id = UUID.randomUUID().toString()

                  pd.show()

                  var imageRef =
                      FirebaseStorage.getInstance().reference.child("approvalMeal1").child(id)

                  imageRef.putFile(filepath).addOnSuccessListener { p0 ->
                      pd.dismiss()
                      Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_LONG).show()
                      imageRef.downloadUrl.addOnCompleteListener { task ->
                          if (task.isSuccessful) {
                              val downloadUri = task.result
                              Log.i("test", downloadUri.toString())
                              val url = downloadUri.toString()
                              sumbitentry(url)
                          }
                      }
                          .addOnFailureListener {
                              // Handle any errors
                          }
                  }

                      .addOnFailureListener { p0 ->
                          pd.dismiss()
                          Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()

                      }
                      .addOnProgressListener { p0 ->
                          var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                          pd.setMessage("uploading")

                      }
              }
        }

          val title = findViewById<TextView>(R.id.editTextTime2)


          mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
              override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                  var format =""
                  var fixedhour = 0
                  var fixedmin = ""
                  Log.d("dd", String.format(hourOfDay.toString()))
                  when {
                      hourOfDay == 0 -> {
                          fixedhour = hourOfDay + 12
                          format = "AM"
                      }
                      hourOfDay == 12 -> {
                          fixedhour = 12
                          format = "PM"
                      }
                      hourOfDay > 12 -> {
                          fixedhour = hourOfDay - 12
                          format = "PM"
                      }
                      hourOfDay < 12 -> {
                          fixedhour = hourOfDay
                          format = "AM"
                      }
                      else -> format = "AM"

                  }
                  if (minute < 10){
                      fixedmin = "0"+minute.toString()
                  }
                  if (minute >= 10){
                      fixedmin = minute.toString()
                  }
                  title.setText(fixedhour.toString() + ":" + fixedmin+" "+format)
                  var specifictime = (hourOfDay.toString()+fixedmin)
              }

          }, hour, minute, false)


          title.setOnClickListener({ v ->
              mTimePicker.show()
          })

          val nextstepinfo = findViewById<TextView>(R.id.textView15)
          nextstepinfo.setText("A Deal Reveal admin will reach out to "+ Phonenumber + ". The admin will ask to speak with the point of contact provided " + Pointofcontact + ". Deal Reveal will want to confirm that the information provided is accuracte and the actual business is opening the account. Please provide the best general time of day to call.")
    }


    private fun sumbitentry(url: String) {
        var id = UUID.randomUUID().toString()
        val setuptime = findViewById<TextView>(R.id.editTextTime2)
        var sendsetuptime = setuptime.text.toString()

        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        val possible = hashMapOf(
            "Address" to Address,
            "CUID" to id,
            "Clientname" to BusinessName,
            "CompanyURL" to Websiteurl,
            "Date" to currentDate,
            "Facebook" to "",
            "MealImageUrl" to url,
            "Password" to password,
            "PhoneNumber" to Phonenumber,
            "PointOfContact" to Pointofcontact,
            "Yelp" to Yelpurl,
            "email" to email,
            "Facebook" to fb,
            "Insta" to Insta,
            "Promocode" to "",
            "setuptime" to sendsetuptime,


            )

        db.collection("Possibleclient").document(id)
            .set(possible)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
        startActivity(Intent(applicationContext, Startscreen::class.java))
        finish()
    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("New Clients")
    }

    }
