package com.example.dealreveal.Activites.shared

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.admins.AdminhomeActivity
import com.example.dealreveal.Activites.client.ClientCollectionDealActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DealfeedbacktActivity : AppCompatActivity() {
    var dealtitletext = ""
    var Businessnametext = ""
    var Avatartext = ""
    var dealpricetext = ""
    var resid1 = ""
    var uid = ""
    var reportkey = ""
    var Date = ""
    var DealRating = 0
    var Dealfeedback = ""
    var ReviewID = ""
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    var today = Calendar.getInstance(Locale.getDefault())
    var cal = Calendar.getInstance(Locale.getDefault())
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH).toInt() + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val monthyear = (month.toString() + "-" + year.toString())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dealfeedbackt)
        headerandbottom()
        reportkeycheck()

    }

    private fun reportkeycheck() {

        dealtitletext = intent.getStringExtra("dealtitletext").toString()
        Dealfeedback = intent.getStringExtra("DealFeedback").toString()
        Businessnametext = intent.getStringExtra("Businessnametext").toString()
        Avatartext = intent.getStringExtra("Avatartext").toString()
        dealpricetext = intent.getStringExtra("dealpricetext").toString()
        resid1 = intent.getStringExtra("resid").toString()
        uid = intent.getStringExtra("uid").toString()
        reportkey = intent.getStringExtra("reportkey").toString()
        Date = intent.getStringExtra("Date").toString()
        DealRating = intent.getIntExtra("DealRating",0)
        ReviewID = intent.getStringExtra("ReviewID").toString()

        if (reportkey == "reportkey"){
            reportkeybuttonsetup()
        }
        if (reportkey == "liarreview"){
            liarreviewbuttonsetup()
        }
        if (reportkey == "reviewdeal"){
            reviewdealbuttonsetup()
        }
        if (reportkey == "clientreview"){
            clientreviewbuttonsetup()
        }
        if (reportkey == "new buz"){
            newbuzsetup()
        }
    }

    private fun reportkeybuttonsetup(){
        var view = this.getWindow().getDecorView();
//            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light))

        val rBar = findViewById<RatingBar>(R.id.rBar)
        rBar.isVisible = false

        val ratedeal = findViewById<TextView>(R.id.ratedeal)
        ratedeal.isVisible = false

        val dealfeedback = findViewById<TextView>(R.id.dealfeedback)
        dealfeedback.setHint("Please let the DealReveal team know why this deal is a false posting.")


        val Businessname = findViewById<TextView>(R.id.Businessname)
        Businessname.setText(Businessnametext)

        val Avatar = findViewById<ImageView>(R.id.dealimage)
        Glide.with(this@DealfeedbacktActivity)
            .load(Avatartext)
            .into(Avatar)

        val Dealtitle = findViewById<TextView>(R.id.Dealtitle)
        Dealtitle.setText(dealtitletext)

        val dealprice = findViewById<TextView>(R.id.dealprice)
        dealprice.setText("Price: $"+dealpricetext)

        val submitfeedbackbutton = findViewById<Button>(R.id.submitfeedbackbutton)
        submitfeedbackbutton.setText("Submit Report")
        submitfeedbackbutton.setOnClickListener(){

            submitliarreport()
            }
        val rightIcon = findViewById<ImageView>(R.id.right_icon)

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Deal Liar")
            intent.putExtra("desc","* Here you can tell a deal reveal admin on why this deal is inaccurate. \n\n * A deal reveal admin will then look into this deal to see if it has to be removed. ")
            startActivity(intent)
        }

    }

    private fun newbuzsetup(){
        val rBar = findViewById<RatingBar>(R.id.rBar)
        rBar.isVisible = false

        val ratedeal = findViewById<TextView>(R.id.ratedeal)
        ratedeal.isVisible = false

        val dealfeedback = findViewById<TextView>(R.id.dealfeedback)
        dealfeedback.setHint("Please let Deal Reveal know who you want on this app. We will go kick in there door and get them on here.")

        val Businessname = findViewById<TextView>(R.id.Businessname)
        Businessname.isVisible = false

        val Avatar = findViewById<ImageView>(R.id.dealimage)
        Avatar.isVisible = true
        Avatar.setImageDrawable(getResources().getDrawable(R.drawable.dealreveallogo))

        val Dealtitle = findViewById<TextView>(R.id.Dealtitle)
        Dealtitle.setText("New Business Recommendation")

        val dealprice = findViewById<TextView>(R.id.dealprice)
        dealprice.isVisible = false

        val submitfeedbackbutton = findViewById<Button>(R.id.submitfeedbackbutton)
        submitfeedbackbutton.setText("Submit Report")
        submitfeedbackbutton.setOnClickListener(){
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH).toInt() + 1
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            var id = UUID.randomUUID().toString()
            val monthyear = (month.toString() + "-" + year.toString())

            val newidea = hashMapOf(
                "Date" to monthyear,
                "Reqest" to dealfeedback.text.toString(),
                "ReviewID" to id,
            )


            db.collection("NewBusinessRequests").document(monthyear).collection(monthyear).document(id).set(newidea).addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written!"
                )
                Toast.makeText(this, "We will try our best to get them setup on Deal Reveal :)", Toast.LENGTH_LONG).show()
                finish()
            }
                .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
        }
        val rightIcon = findViewById<ImageView>(R.id.right_icon)

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Business Recommendation")
            intent.putExtra("desc","* Here you can tell a deal reveal admin on what business we should reach out to next to get signed up. \n\n * A deal reveal admin will then look into this business to see if we can get them on the application. ")
            startActivity(intent)
        }

    }

    private fun liarreviewbuttonsetup(){
        var view = this.getWindow().getDecorView();
//            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light))

        val rBar = findViewById<RatingBar>(R.id.rBar)
        rBar.isVisible = false

        val ratedeal = findViewById<TextView>(R.id.ratedeal)
        ratedeal.isVisible = false

        val dealfeedback = findViewById<TextView>(R.id.dealfeedback)
        dealfeedback.setText(Dealfeedback)


        val Businessname = findViewById<TextView>(R.id.Businessname)
        Businessname.setText(Businessnametext)

        val Avatar = findViewById<ImageView>(R.id.dealimage)
        Glide.with(this@DealfeedbacktActivity)
            .load(Avatartext)
            .into(Avatar)

        val Dealtitle = findViewById<TextView>(R.id.Dealtitle)
        Dealtitle.setText(dealtitletext)

        val dealprice = findViewById<TextView>(R.id.dealprice)
        dealprice.setText("Price: $"+dealpricetext)

        val submitfeedbackbutton = findViewById<Button>(R.id.submitfeedbackbutton)
        submitfeedbackbutton.setText("Delete Liar Report")
        submitfeedbackbutton.setOnClickListener(){
            deleteliarreport()
        }
    }

    private fun reviewdealbuttonsetup() {
        var view = this.getWindow().getDecorView();
//            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light))

        val ratedeal = findViewById<TextView>(R.id.ratedeal)

        val rBar = findViewById<RatingBar>(R.id.rBar)
        if (rBar != null) {
            val button = findViewById<Button>(R.id.submitfeedbackbutton)
            button?.setOnClickListener {
                val msg = rBar.rating.toString()
                Log.d("TAG", msg)
            }
        }

        val Businessname = findViewById<TextView>(R.id.Businessname)
        Businessname.setText(Businessnametext)

        val Avatar = findViewById<ImageView>(R.id.dealimage)
        Glide.with(this@DealfeedbacktActivity)
            .load(Avatartext)
            .into(Avatar)

        val Dealtitle = findViewById<TextView>(R.id.Dealtitle)
        Dealtitle.setText(dealtitletext)

        val dealprice = findViewById<TextView>(R.id.dealprice)
        dealprice.setText("Price: $" + dealpricetext)

        val dealfeedback = findViewById<TextView>(R.id.dealfeedback)
        dealfeedback.setHint("Please let the company know what you think of this deal :)")

        val submitfeedbackbutton = findViewById<Button>(R.id.submitfeedbackbutton)
        submitfeedbackbutton.setText("Submit Deal Review")
        submitfeedbackbutton.setOnClickListener() {
            submitdealfeedback()

        }
        val rightIcon = findViewById<ImageView>(R.id.right_icon)

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page", "Deal Review")
            intent.putExtra(
                "desc",
                "* Here you can tell the business of what you think of this deal. \n\n * They will be able to see this feedback and apply the feedback to this deal or future deals. "
            )
            startActivity(intent)
        }
    }

    private fun clientreviewbuttonsetup() {
        var view = this.getWindow().getDecorView();
//            view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light))

        val ratedeal = findViewById<TextView>(R.id.ratedeal)
        ratedeal.setText("Deal Rating")

        val rBar = findViewById<RatingBar>(R.id.rBar)
        rBar.rating = DealRating.toFloat()
        rBar.setIsIndicator(true)

        val Businessname = findViewById<TextView>(R.id.Businessname)
        Businessname.setText(Businessnametext)

        val Avatar = findViewById<ImageView>(R.id.dealimage)
        Glide.with(this@DealfeedbacktActivity)
            .load(Avatartext)
            .into(Avatar)

        val Dealtitle = findViewById<TextView>(R.id.Dealtitle)
        Dealtitle.setText(dealtitletext)

        val dealprice = findViewById<TextView>(R.id.dealprice)
        dealprice.setText("Price: $"+dealpricetext)

        val dealfeedback = findViewById<TextView>(R.id.dealfeedback)
        dealfeedback.setText(Dealfeedback)
        dealfeedback.setEnabled(false)

        val submitfeedbackbutton = findViewById<Button>(R.id.submitfeedbackbutton)
        submitfeedbackbutton.setText("Delete Feedback")
        submitfeedbackbutton.setOnClickListener(){
            deletedealfeedback()
        }
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page", "Feedback Review")
            intent.putExtra(
                "desc",
                "* Here you can see the review that the normal user left. \n\n * Hopefully they were nice, this feedback may help you think of what new deals you want to post next. "
            )
            startActivity(intent)
        }
    }

    private fun submitliarreport() {
        val dealfeedback = findViewById<TextView>(R.id.dealfeedback)
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid
        var id = UUID.randomUUID().toString()

        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        val possible = hashMapOf(
            "Businessname" to Businessnametext,
            "Date" to currentDate,
            "DealFeedback" to dealfeedback.text.toString(),
            "DealRating" to 0,
            "Dealname" to dealtitletext,
            "Dealuid" to uid,
            "Image" to Avatartext,
            "Price" to dealpricetext,
            "ReviewID" to id,
            "resid1" to resid1,

            )

        db.collection("Liarsreports").document(id).set(possible)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
//        startActivity(Intent(applicationContext, Startscreen::class.java))
        finish()
        Toast.makeText(this, "Review submitted to the business", Toast.LENGTH_LONG).show()

    }

    private fun deleteliarreport() {

        db.collection("Liarsreports").document(ReviewID).delete()
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
        startActivity(Intent(applicationContext, AdminhomeActivity::class.java))
        finish()
    }

    private fun submitdealfeedback(){

        val rBar = findViewById<RatingBar>(R.id.rBar)
        val dealfeedback = findViewById<TextView>(R.id.dealfeedback)
        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid
        var id = UUID.randomUUID().toString()

        val currentDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

        val possible = hashMapOf(
            "Businessname" to Businessnametext,
            "Date" to currentDate,
            "DealFeedback" to dealfeedback.text.toString(),
            "DealRating" to rBar.rating,
            "Dealname" to dealtitletext,
            "Dealuid" to uid,
            "Image" to Avatartext,
            "Price" to dealpricetext,
            "ReviewID" to id,
            "resid1" to resid1,

            )

        db.collection("DealFeedback").document(resid1).collection(resid1).document(id).set(possible)
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot successfully written!"
                )
                Toast.makeText(
                    applicationContext,
                    "Thank you for the Feedback!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }
//        startActivity(Intent(applicationContext, Startscreen::class.java))

        db.collection("Dealanalytics").document(resid1).collection(monthyear).document(uid).update("Feedback", FieldValue.increment(1))
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot written ")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)

                if (e != null) {
                    print("we should make a document")

                    val dictfix = hashMapOf(
                        "UID" to uid,
                        "Avatar" to Avatartext,
                        "Title" to dealtitletext,
                    )
                    db.collection("Dealanalytics").document(resid1).collection(monthyear)
                        .document(uid).set(dictfix)
                    db.collection("Dealanalytics").document(resid1).collection(monthyear)
                        .document(uid).update("Feedback", FieldValue.increment(1))
                }
                finish()
            }
    }

    private fun deletedealfeedback(){

        db.collection("DealFeedback").document(resid1).collection(resid1).document(ReviewID).delete()
            .addOnSuccessListener {
                Log.d(
                    "NumberGenerated",
                    "DocumentSnapshot deleted"
                )
            }
//            .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }

        startActivity(Intent(applicationContext, ClientCollectionDealActivity::class.java))

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
        title.setText("")
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}