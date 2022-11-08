package com.example.dealreveal.Activites.shared


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.client.BusinessSignup1Activity
import com.example.dealreveal.Activites.users.UserSignUpActivity
import com.example.dealreveal.R


class HelpReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_reminder)

        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)
        val arrow = findViewById<ImageView>(R.id.imageView3)

        val intent = intent
        val HelpID = intent.getStringExtra("HELPID")

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener{
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Introduction")
            intent.putExtra("desc","* Here in the Deal Reveal User Guide, you can read information that easily explains what can be done on ever page found in Deal Reveal. \n\n * If you are ever unsure of what too do or how to use something on the given page, just click the help icon to bring you to the user guide. \n\n * If the user guide does not answer your current question, feel free to reach out to support by using the email found at the bottom of this page. :)")
            startActivity(intent)
        }
        title.setText("")

        val rotate = RotateAnimation(
            0F,
            10000F,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 100000
        rotate.interpolator = LinearInterpolator()

        val image = findViewById<View>(R.id.help) as ImageView

//        image.startAnimation(rotate)

        val scaleDown1 = ObjectAnimator.ofPropertyValuesHolder(
            arrow,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        )
        scaleDown1.duration = 310

        scaleDown1.repeatCount = ObjectAnimator.INFINITE
        scaleDown1.repeatMode = ObjectAnimator.REVERSE

        scaleDown1.start()


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (HelpID == "user"){
                val intent = Intent(this, UserSignUpActivity::class.java)
                startActivity(intent)
            }
            if (HelpID == "Business"){
                val intent = Intent(this, BusinessSignup1Activity::class.java)
                startActivity(intent)
            }
        }

        Log.i("NumberGenerated", HelpID.toString())





}

}
