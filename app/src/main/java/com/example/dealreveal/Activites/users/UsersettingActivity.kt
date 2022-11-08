package com.example.dealreveal.Activites.users

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.dealreveal.Activites.UserSavedDealsActivity
import com.example.dealreveal.Activites.shared.DealfeedbacktActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Sharelink
import com.example.dealreveal.Activites.shared.Startscreen
import com.example.dealreveal.Activites.usernotsignedin
import com.example.dealreveal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class UsersettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usersetting)
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        buttonsetup()
        headerandbottom()
    }

    private fun buttonsetup(){


        val Deletebutton = findViewById<Button>(R.id.deleteaccount)

        if (usernotsignedin == false)
        {

        }
        if (usernotsignedin == true) {
            Deletebutton.isVisible = false
        }
        Deletebutton.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

            builder.setTitle("Confirm")
            builder.setMessage("Are you sure you want to delete this account? You will have to make a new one if you hit confirm.")

            builder.setPositiveButton(
                "YES",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                    dialog.dismiss()

                    val db = FirebaseFirestore.getInstance()
                    val currentuser = FirebaseAuth.getInstance().currentUser!!
                        .uid
                    val user = FirebaseAuth.getInstance().currentUser!!
                    db.collection("SavedDeals").document(currentuser)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("TAG", "DocumentSnapshot successfully deleted!")

                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error deleting document", e)

                        }
                    db.collection("users").document(currentuser)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("TAG", "DocumentSnapshot successfully deleted!")

                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error deleting document", e)

                        }

                    user.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "User account deleted.")
                            }
                        }
                    startActivity(Intent(this, Startscreen::class.java))
                    finish()
                    dialog.dismiss()
                    Toast.makeText(this, "Deleted like my ex's number, hope you come back...", Toast.LENGTH_LONG).show()
                })

            builder.setNegativeButton(
                "NO",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing
                    dialog.dismiss()
                })

            val alert: android.app.AlertDialog? = builder.create()
            alert!!.show()
        }

        val Locationservices = findViewById<Button>(R.id.Locationservices)
        Locationservices.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Location Services")
            intent.putExtra("desc","* Deal Reveal is a location based services app. \n\n * Your location is never saved, it is only used to query and find deals in your location. \n\n * Do you not want to provide your location? that sucks, delete the app. Why did you download a deals location finding app in the first place? hehehe")
            startActivity(intent)
        }
        val recommend = findViewById<Button>(R.id.recommendfriend)
        recommend.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            db.collection("ShareURL").document("Sharelink").get().addOnSuccessListener { documentSnapshot ->


                val ClientInfo = documentSnapshot.toObject(Sharelink::class.java)

                var ios = ClientInfo?.iosLink.toString()
                var andorid = ClientInfo?.andLink.toString()

                var sharingIntent = Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                var shareBody = "Download Deal Reveal to find all the best local deals near you. For IOS users go to " +ios +", Android users can go to "+ andorid+".";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recommend Deal Reveal");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"))

            }
        }
        val RecommendBuz = findViewById<Button>(R.id.RecommendBuz)
        RecommendBuz.setOnClickListener {
            if (usernotsignedin == false){
            val intent = Intent(this, DealfeedbacktActivity::class.java)
            intent.putExtra("reportkey","new buz")

            startActivity(intent)

            }
            if (usernotsignedin == true) {
                val inflater: LayoutInflater =
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                // Inflate a custom view using layout inflater
                val view = inflater.inflate(R.layout.changerangepopup, null)

                // Initialize a new instance of popup window
                val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                // Set an elevation for the popup window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.elevation = 10.0F
                }


                // If API level 23 or higher then execute the code
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Create a new slide animation for popup window enter transition
                    val slideIn = Slide()
                    slideIn.slideEdge = Gravity.TOP
                    popupWindow.enterTransition = slideIn

                    // Slide animation for popup window exit transition
                    val slideOut = Slide()
                    slideOut.slideEdge = Gravity.RIGHT
                    popupWindow.exitTransition = slideOut

                }

                // Get the widgets reference from custom view
                val tv = view.findViewById<TextView>(R.id.textView58)
                val buttonPopup = view.findViewById<Button>(R.id.button_popup)
                val seek = view.findViewById<SeekBar>(R.id.seekBar)
                val close = view.findViewById<TextView>(R.id.text_view)


                tv.text = "This function can only be used if you create a account."
                close.text = ""
                seek.isVisible = false


                // Set click listener for popup window's text view
                tv.setOnClickListener {
                    // Change the text color of popup window's text view
                    tv.setTextColor(Color.BLACK)
                }

                // Set a click listener for popup's button widget
                buttonPopup.setOnClickListener {
                    // Dismiss the popup window
                    popupWindow.dismiss()
                    val tv = view.findViewById<TextView>(R.id.textView58)
                    tv.text = "This function can only be used if you create a account."

                }

                // Set a dismiss listener for popup window
                popupWindow.setOnDismissListener {

                }


                // Finally, show the popup window on app
                TransitionManager.beginDelayedTransition(view as ViewGroup?)
                popupWindow.showAtLocation(
                    view as ViewGroup?, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
                )

            }

        }
        val logout = findViewById<Button>(R.id.logout)

        if (usernotsignedin == false)
        {
            logout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(applicationContext, Startscreen::class.java))
                finish()
            }
        }
        if (usernotsignedin == true) {
            logout.setText("Go To Home Page")
            logout.setOnClickListener {
                startActivity(Intent(applicationContext, Startscreen::class.java))
                finish()
            }
        }

    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setVisibility(View.INVISIBLE)
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Settings")
            intent.putExtra("desc","* On the settings page you can click the recommend button to send a friend a link so they can try Deal Reveal. \n\n * If you would like to logout, press the logout button.")
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


