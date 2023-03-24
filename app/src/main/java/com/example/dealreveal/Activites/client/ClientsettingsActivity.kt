package com.example.dealreveal.Activites.client

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.dealreveal.Activites.PendingapprovalActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.Activites.shared.Sharelink
import com.example.dealreveal.Activites.shared.Startscreen
import com.example.dealreveal.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*

class ClientsettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientsettings)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

        val updateBusinfo = findViewById<Button>(R.id.UpdateInfo)
        updateBusinfo.setOnClickListener {
            startActivity(Intent(applicationContext, UpdateBusinessInfoActivity::class.java))
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
        val card = findViewById<Button>(R.id.updatecard)
        card.setOnClickListener {
            startActivity(Intent(applicationContext, StripeCardCollectionActivity::class.java))
//            finish()
        }
        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext, Startscreen::class.java))
        }
        val Delete = findViewById<Button>(R.id.deleteaccount)
        Delete.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

            builder.setTitle("Confirm")
            builder.setMessage("Are you sure you want to delete this account? All of your postings will be deleted. You will have to make a new account with Deal Reveal if you hit confirm.")

            builder.setPositiveButton(
                "YES",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                    dialog.dismiss()

                    val db = FirebaseFirestore.getInstance()
                    val currentuser = FirebaseAuth.getInstance().currentUser!!
                        .uid
                    val user = FirebaseAuth.getInstance().currentUser!!
                    db.collection("ClientAccounts").document(currentuser)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("TAG", "DocumentSnapshot successfully deleted!")

                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error deleting document", e)

                        }
                    val docRef = db.collection("Deals").whereEqualTo("resid",currentuser).get()
                        .addOnSuccessListener { documents ->

                            for (document in documents.documents) {
                                val myObject =
                                    document.toObject(Pendingapproval::class.java)
                                db.collection("Deals").document(myObject!!.uid)
                                    .delete()
                                    .addOnSuccessListener {
                                        Log.d("TAG", "DocumentSnapshot successfully deleted!")

                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("TAG", "Error deleting document", e)

                                    }

                            }
                        }

                    user.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "User account deleted.")
                            }
                        }
                    startActivity(Intent(this, Startscreen::class.java))
                    dialog.dismiss()
                    Toast.makeText(this, "Deleted like my ex's number", Toast.LENGTH_SHORT).show()
                })

            builder.setNegativeButton(
                "NO",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing
                    dialog.dismiss()
                })

            val alert: android.app.AlertDialog? = builder.create()
            alert!!.show()
        }
        headerandbottom()
    }

    private fun headerandbottom() {
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)
        leftIcon.isVisible = false

//        leftIcon.setVisibility(View.INVISIBLE)
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Deal Analytics")
            intent.putExtra("desc","* On the settings page you can click the recommend button to send a friend a link so they can try Deal Reveal. \n\n * You can press the Update Card Info button to update the card you have on file with Deal Reveal. \n\n * If you would like to logout, press the logout button.")
            startActivity(intent)
        }
        title.setText("")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.Settings

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
                    true
                }
                else -> false
            }
        }
    }

}