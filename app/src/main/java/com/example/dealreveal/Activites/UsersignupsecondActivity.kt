package com.example.dealreveal.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit


class UsersignupsecondActivity : AppCompatActivity() {

    lateinit var auth1: FirebaseAuth
    lateinit var storedVerificationId1:String
    lateinit var resendToken1: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks1: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    val db= FirebaseFirestore.getInstance()
    var BDAY = ""
    var email = ""
    var username = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usersignupsecond)


        auth1=FirebaseAuth.getInstance()

        var currentUser = auth1.currentUser
        if(currentUser != null) {
            startActivity(Intent(applicationContext, Startscreen::class.java))
            finish()
        }
        val lookupnumber = findViewById<Button>(R.id.button7)
        lookupnumber.setOnClickListener{
            login()
        }

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


        val intent = intent
        username = intent.getStringExtra("userName").toString()
        email = intent.getStringExtra("email").toString()
        BDAY = intent.getStringExtra("birthdate").toString()


        // Callback function for Phone Auth
        callbacks1 = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                startActivity(Intent(applicationContext, Startscreen::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
                Log.i("NumberGenerated", "failed")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId1 = verificationId
                resendToken1 = token

            }
        }
        val Verifypnumber = findViewById<Button>(R.id.button8)
        val otpGiven = findViewById<EditText>(R.id.editTextNumber)

        lookupnumber.setOnClickListener{
            login()
        }
        Verifypnumber.setOnClickListener{
            var otp=otpGiven.text.toString().trim()
            if(!otp.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId1.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth1.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val mobileNumber=findViewById<EditText>(R.id.editTextPhone)
                    var usernumber=mobileNumber.text.toString().trim()

                    val currentuser = FirebaseAuth.getInstance().currentUser!!
                        .uid
                    val user = hashMapOf(
                        "BDAY" to BDAY,
                        "Phone" to usernumber,
                        "email" to email,
                        "uid" to currentuser.toString(),
                        "username" to username

                    )

                    db.collection("user1").document(currentuser.toString())
                        .set(user)
                        .addOnSuccessListener { Log.d("NumberGenerated", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("NumberGenerated", "Error writing document", e) }

                    startActivity(Intent(applicationContext, Startscreen::class.java))
                    finish()

// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun login() {
        val mobileNumber=findViewById<EditText>(R.id.editTextPhone)
        var number=mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+1"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: Any) {
        val options = PhoneAuthOptions.newBuilder(auth1)
            .setPhoneNumber(number as String) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks1) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}