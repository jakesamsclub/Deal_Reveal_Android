package com.example.dealreveal.Activites.users

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
import com.example.dealreveal.Activites.UserSavedDealsActivity
import com.example.dealreveal.Activites.shared.DealfeedbacktActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Startscreen
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

        }
        val recommend = findViewById<Button>(R.id.recommendfriend)
        recommend.setOnClickListener {

        }
        val RecommendBuz = findViewById<Button>(R.id.RecommendBuz)
        RecommendBuz.setOnClickListener {
            val intent = Intent(this, DealfeedbacktActivity::class.java)
            intent.putExtra("reportkey","new buz")

            startActivity(intent)
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

        leftIcon.setVisibility(View.INVISIBLE)
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


