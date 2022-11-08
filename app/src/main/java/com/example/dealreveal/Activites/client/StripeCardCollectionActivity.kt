package com.example.dealreveal.Activites.client

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethod
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.view.CardInputWidget
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL


class StripeCardCollectionActivity() : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentLauncher: PaymentLauncher
    var clientpayment = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe_card_collection)
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51HFoUaCfhaTefPIQXgYg55SRCuC8qOHkEpvg5CogEuNkZMUCrtw4xamguUax9rUEKWyiWSAcNZeoje098AAosIyb00ee9AULCl"
        )
        paysetup()
    }

    fun paysetup() {

            val leftIcon = findViewById<ImageView>(R.id.left_icon)
            val rightIcon = findViewById<ImageView>(R.id.right_icon)
            val title = findViewById<TextView>(R.id.info)

        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            intent.putExtra("page","Update Business Payment Info")
            intent.putExtra("desc","* Here you can update the company card that you have on file.")
            startActivity(intent)
        }
            title.setText("")

        leftIcon.setOnClickListener {
            finish()
        }

        val currentuser = FirebaseAuth.getInstance().currentUser!!
            .uid
        val payButton: Button = findViewById(R.id.payButton)
        val TextDesc: TextView = findViewById(R.id.textprice)
        val weakActivity = WeakReference<Activity>(this)

        val docRef = db.collection("ClientsAccounts").document(currentuser)
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                val ClientInfo = documentSnapshot.toObject(Client::class.java)

                Log.d("client", "DocumentSnapshot data: ${ClientInfo?.Clientname}")
                clientpayment = ClientInfo!!.Cardonfile

                if (clientpayment == false){
                    TextDesc.setText("No card is currently on file for your account. Please add a card below. Any charges submitted to your account will always have a invoice sent via email to your email.")
                }

            }
            .addOnFailureListener { exception ->
                Log.d("test", "get failed with ", exception)
            }

        payButton.setOnClickListener {
            // Get the card details from the card widget
            val cardInputWidget =
                findViewById<CardInputWidget>(R.id.cardInputWidget)
            cardInputWidget.paymentMethodCreateParams?.let { params ->
                // Create a Stripe token from the card details
                val stripe = Stripe(
                    applicationContext,
                    PaymentConfiguration.getInstance(applicationContext).publishableKey
                )
                stripe.createPaymentMethod(
                    params,
                    callback = object : ApiResultCallback<PaymentMethod> {
                        override fun onError(e: Exception) {

                            Toast.makeText(
                                applicationContext,
                                "Error updating your payment info, please make sure details entered are accurate. Contact Deal Reveal support if needed.",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        @RequiresApi(Build.VERSION_CODES.N)
                        override fun onSuccess(token: PaymentMethod) {
                            if (Build.VERSION.SDK_INT > 9) {
                                val policy = ThreadPolicy.Builder().permitAll().build()
                                StrictMode.setThreadPolicy(policy)
                            }

                            Log.d("TAG", "onSuccess: "+token.id)
                            var url =
                                URL("https://us-east1-deal-reveal-61707.cloudfunctions.net/Dealrevealaddpayment/?paymentmethod=" + token!!.id.toString() +"&uid=cus_Mb68OhUCmRSBrx")
                            val connection = url.openConnection()

                            with(url.openConnection() as HttpURLConnection) {
                                requestMethod = "GET"  // optional default is GET

                                println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
                                db.collection("ClientsAccounts").document(currentuser)
                                    .update("Cardonfile", true)
                                inputStream.bufferedReader().use {
                                    it.lines().forEach { line ->
                                        println(line)
                                    }
                                }

                                if (responseCode == 200){
                                db.collection("ClientsAccounts").document(currentuser)
                                    .update("Cardonfile", true)
                                    .addOnSuccessListener {
                                        Log.d(
                                            "NumberGenerated",
                                            "DocumentSnapshot successfully written1! good job"
                                        )
                                        Toast.makeText(
                                            applicationContext,
                                            "Your payment info Has been updated!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                            }
                            }
                            }
                    }
                )
            }
        }

    }
}


//class BusinessPaymentViewController: UIViewController{
//    var paymentuid = ""
//    var cardfaildate = ""
//    var accountemail = ""
//    var cardsetup = false
//    lazy var cardTextField: STPPaymentCardTextField = {
//        let cardTextField = STPPaymentCardTextField()
//        return cardTextField
//    }()
//    lazy var payButton: UIButton = {
//        let button = UIButton(type: .custom)
//        button.layer.cornerRadius = 15
//        button.backgroundColor = .systemBlue
//                button.titleLabel?.font = UIFont.systemFont(ofSize: 22)
//        button.setTitle("Update Payment", for: .normal)
//        button.frame.size.height = 75
//        button.addTarget(self, action: #selector(pay), for: .touchUpInside)
//        return button
//    }()
//    @IBOutlet weak var approve: UIButton!
//    @IBOutlet weak var headertext: UILabel!
//    @IBOutlet weak var paymentalerttext: UILabel!
//    override func viewDidLoad() {
//        super.viewDidLoad()
//        buttonsetup()
//        updatepay()
//        view.backgroundColor = .white
//                let stackView = UIStackView(arrangedSubviews: [cardTextField, payButton])
//        stackView.axis = .vertical
//                stackView.spacing = 20
//        stackView.translatesAutoresizingMaskIntoConstraints = false
//        view.addSubview(stackView)
//        NSLayoutConstraint.activate([
//            stackView.leftAnchor.constraint(equalToSystemSpacingAfter: view.leftAnchor, multiplier: 2),
//        view.rightAnchor.constraint(equalToSystemSpacingAfter: stackView.rightAnchor, multiplier: 2),
//        stackView.topAnchor.constraint(equalToSystemSpacingBelow: paymentalerttext.bottomAnchor, multiplier: 5),
//        ])
//    }
//    func pullbusinessinfo(){
//        Firestore.firestore().collection("ClientsAccounts").document(Auth.auth().currentUser!.uid).getDocument() { [self] (document, error) in
//                if let document = document, document.exists {
//            self.cardsetup = (document.get("Cardonfile")) as? Bool ?? true
//            self.cardfaildate = (document.get("Cardpassdate")) as? String ?? ""
//            self.accountemail = (document.get("Clientemail")) as? String ?? ""
//            if self.cardsetup == false && self.cardfaildate == "" {
//                paymentalerttext.text = "No card is currently on file for your account. Please add a card below. Please note that any charges submited to your account will always have a invoice sent via email to \(self.accountemail)"
//            }
//            if cardsetup == false && self.cardfaildate != ""  {
//                paymentalerttext.text = "The card on file has failed. Payment failed on \(cardfaildate). Please update the card on file by adding in the needed info below. Please note that any charges submited to your account will always have a invoice sent via email to \(self.accountemail)"
//            } else if self.cardsetup == true {
//                self.paymentalerttext.text = "There is already a card on file. Deal Reveal has not identified any issues with this card, but feel free to add a new payment method below if needed."
//            }
//        }
//        }
//    }
//    func updatepay(){
//        if paymentuid != ""{
//            self.navigationItem.setHidesBackButton(true, animated: true)
//            paymentalerttext.isHidden = true
//        }
//        else {
//            headertext.text = "Update Payment"
//            print("Paymentuid for updating\(paymentuid)")
//            approve.isHidden = true
//            pullbusinessinfo()
//        }
//    }
//    @objc func pay() {
//        //this is updating payment the auth code was a bitch so i just made a if statement
//        if paymentuid == ""{
//            // Collect card details on the client
//            print("Paymentuid for updating\(paymentuid)")
//            if cardTextField.isValid {
//                let cardParams = cardTextField.cardParams
//                        let paymentMethodParams = STPPaymentMethodParams(card: cardParams, billingDetails: nil, metadata: nil)
//                STPAPIClient.shared.createPaymentMethod(with: paymentMethodParams) { [weak self] paymentMethod, error in
//                    guard let paymentMethod = paymentMethod else {
//                        // Display the error to the user
//                        ProgressHUD.showError("card info was not able to be saved")
//                        return
//                    }
//                    let paymentMethodId = paymentMethod.stripeId
//                            print(paymentMethodId)
//                    guard var url = URL(string: "https://us-east1-deal-reveal-61707.cloudfunctions.net/Dealrevealaddpayment/?paymentmethod=\(paymentMethodId)&uid=\(String(Auth.auth().currentUser!.uid))") else {
//                        print("https://us-east1-deal-reveal-61707.cloudfunctions.net/Dealrevealaddpayment/?paymentmethod=\(paymentMethodId)&uid=\(String(Auth.auth().currentUser!.uid))")
//                        return //be safe
//                    }
//                    AF.request(url)
//                        .responseJSON { response in
//                                print(response.description)
//                            if case .success(let value) = response.result {
//                                print(response)
//                                Firestore.firestore().collection("ClientsAccounts").document(String(Auth.auth().currentUser!.uid)).updateData(["Cardonfile":true])
//                                ProgressHUD.showAdded("success! Payment info was added")
//                            }
//                            if case .failure(let value) = response.result {
//                                print("Got the info")
//                                print(response)
//                                ProgressHUD.showError("failure :( Payment was NOT added")
//                            }
//                        }
//                }
//            }
//            else {
//                ProgressHUD.showError("add card info properly dummy")
//            }
//        }
//        // if this is brandnew setup
//        else{
//            // Collect card details on the client
//            print("Paymentuid for updating\(paymentuid)")
//            if cardTextField.isValid {
//                let cardParams = cardTextField.cardParams
//                        let paymentMethodParams = STPPaymentMethodParams(card: cardParams, billingDetails: nil, metadata: nil)
//                STPAPIClient.shared.createPaymentMethod(with: paymentMethodParams) { [weak self] paymentMethod, error in
//                    guard let paymentMethod = paymentMethod else {
//                        // Display the error to the user
//                        ProgressHUD.showError("card info was not able to be saved")
//                        return
//                    }
//                    let paymentMethodId = paymentMethod.stripeId
//                            print(paymentMethodId)
//                    guard var url = URL(string: "https://us-east1-deal-reveal-61707.cloudfunctions.net/Dealrevealaddpayment/?paymentmethod=\(paymentMethodId)&uid=\(self!.paymentuid)") else {
//                        print("https://us-east1-deal-reveal-61707.cloudfunctions.net/Dealrevealaddpayment/?paymentmethod=\(paymentMethodId)&uid=\(self!.paymentuid)")
//                        return //be safe
//                    }
//                    AF.request(url)
//                        .responseJSON { response in
//                                print(response.description)
//                            if case .success(let value) = response.result {
//                                print(response)
//                                Firestore.firestore().collection("ClientsAccounts").document(self!.paymentuid).updateData(["Cardonfile":true])
//                                ProgressHUD.showAdded("success! Payment info was added")
//                            }
//                            if case .failure(let value) = response.result {
//                                print("Got the info")
//                                print(response)
//                                ProgressHUD.showError("failure :( Payment was NOT added")
//                            }
//                        }
//                }
//            }
//            else {
//                ProgressHUD.showError("add card info properly dummy")
//            }
//        }
//    }
//    func buttonsetup(){
//        approve.setTitle("Leave Page" , for: UIControl.State.normal)
//        approve.backgroundColor = UIColor.black
//        approve.layer.cornerRadius = 25
//        approve.titleLabel?.font = UIFont(name: "AsapRoman-Bold", size: 25)
//        approve.clipsToBounds = true
//        approve.setTitleColor(.white, for: UIControl.State.normal)
//    }
//    @IBAction func approvetapped(_ sender: Any) {
//        var refreshAlert = UIAlertController(title: "Confirmation", message: "Are You sure you want to leave this page?", preferredStyle: UIAlertController.Style.alert)
//        refreshAlert.addAction(UIAlertAction(title: "Yes", style: .default, handler: { [self] (action: UIAlertAction!) in
//                self.performSegue(withIdentifier: "payment", sender: self)
//        }
//        )
//        )
//        refreshAlert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: { (action: UIAlertAction!) in
//            print("Handle Cancel Logic here")
//        }
//        )
//        )
//        present(refreshAlert, animated: true, completion: nil)
//    }
//}