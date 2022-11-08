package com.example.dealreveal.Activites.users

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import java.util.*

class UserSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)
        headerformat()
        textandbuttonsetup()

    }
    fun textandbuttonsetup(){
        val editTextTextPersonName = findViewById<TextView>(R.id.editTextTextPersonName)
        val email = findViewById<TextView>(R.id.editTextTextEmailAddress)
        val datePicker = findViewById<DatePicker>(R.id.datePickersignup)

        val button = findViewById<Button>(R.id.button5)
        button.setOnClickListener{
            val emailPattern = """[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"""
            var senduserName = editTextTextPersonName.text.toString()
            var sendemail = email.text.toString()
            var sendbirthdate = ""

            val today = Calendar.getInstance()
            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            ) { view, year, month, day ->
                val month = month + 1
                val msg = "$day/$month/$year"
                sendbirthdate = msg
                println("fart"+sendbirthdate)
            }


            if (sendemail.matches(emailPattern.toRegex())) {
                println("email is okay")
            } else {
                Toast.makeText(applicationContext, "Invalid email address",
                    Toast.LENGTH_SHORT).show()
                println("email is wrong")
                return@setOnClickListener
            }
            if (senduserName.isEmpty()) {
                println("Name is empty.")
                Toast.makeText(applicationContext, "Name is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            if (sendemail.isEmpty()) {
                println("Email is empty.")
                Toast.makeText(applicationContext, "Email is empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            val intent = Intent(this, UsersignupsecondActivity::class.java)
            intent.putExtra("userName",senduserName)
            intent.putExtra("email",sendemail)
            intent.putExtra("birthdate",sendbirthdate)
            startActivity(intent)
        }
    }
    fun headerformat(){
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener{
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Sign Up New User")
            intent.putExtra("desc","* You must enter the following fields to create your account...\n\n    1) Your name \n\n    2) Your Email\n\n    3) A password \n\n    4) your date of Birth. \n\n * Once you have entered all the fields you can press 'Sign Up' \n\n * If you already have a account you can tap sign in.")
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