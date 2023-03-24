package com.example.dealreveal.Activites.users

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Startscreen
import com.example.dealreveal.Activites.usernotsignedin
import com.example.dealreveal.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val Enterconfirmtext =findViewById<TextView>(R.id.textView46)
        val Verifypnumber = findViewById<Button>(R.id.button11)
        val otpGiven = findViewById<EditText>(R.id.editTextPhone3)
        Enterconfirmtext.isVisible = false
        otpGiven.isVisible = false
        Verifypnumber.isVisible = false
        val mobileNumber=findViewById<EditText>(R.id.editTextPhone2)

        mobileNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        auth=FirebaseAuth.getInstance()

        val lookupnumber = findViewById<Button>(R.id.button10)
        lookupnumber.setOnClickListener {
            val mobileNumber=findViewById<EditText>(R.id.editTextPhone2)
            var number=mobileNumber.text.toString().trim()
            Log.d("TA3G", number)
            val regex = Regex("[^A-Za-z0-9]")
            var cleanedupnumber =regex.replace(number, "")
            val docRef = db.collection("users").whereEqualTo("Phone",cleanedupnumber).get()
                .addOnSuccessListener { documents ->
                    if (documents.documents.size == 0){
                        Toast.makeText(applicationContext, "No account is associate with this number, please create a account.", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this, UserSignUpActivity::class.java)
                        startActivity(intent)
                    }else{
                        login()
                    }
                }
        }

        val nophone = findViewById<Button>(R.id.button37)
        nophone.setOnClickListener {
            usernotsignedin = true
            startActivity(Intent(applicationContext, DealRevealfilterActivity::class.java))
            finish()
        }

        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","User Login")
            intent.putExtra("desc","* Provided your cell number\n\n *Enter the one time code texted to your phone \n\n *If you do not have a account you can create one or you can see deals without signing in. The second option will limit the functionality in the app")
            startActivity(intent)
        }
        title.setText("")

        val signup = findViewById<Button>(R.id.buttonsignup)
        signup.setOnClickListener {
            val intent = Intent(this, UserSignUpActivity::class.java)

            startActivity(intent)
        }



        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                startActivity(Intent(applicationContext, Startscreen::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                Log.i("NumberGenerated", e.toString())
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Enterconfirmtext.isVisible = true
                otpGiven.isVisible = true
                Verifypnumber.isVisible = true

                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

            }
        }



    Verifypnumber.setOnClickListener{
        var otp=otpGiven.text.toString().trim()
        if(!otp.isEmpty()){
            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                storedVerificationId.toString(), otp)
            signInWithPhoneAuthCredential(credential)
        }else{
            Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
        }
    }

}




    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this@LoginActivity) { task ->
                if (task.isSuccessful) {

                    startActivity(Intent(applicationContext, DealRevealfilterActivity::class.java))
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
        val mobileNumber=findViewById<EditText>(R.id.editTextPhone2)
        var number=mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+1"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: Any) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number as String) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this@LoginActivity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}