package com.example.dealreveal.Activites.users

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import com.example.dealreveal.R
import java.util.*


class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000
    val text = "MONDAY"

    @SuppressLint("NewApi")
    private fun createChannel() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#e8334a")
//            notificationChannel.description = getString(R.string.notification_channel_description)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    companion object {

        const val CHANNEL_ID = "samples.notification.devdeeds.com.CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"
    }


    override fun onHandleIntent(intent: Intent?) {

        //Create Channel
        createChannel()


        var timestamp: Long = 0
        if (intent != null && intent.extras != null) {
            timestamp = intent.extras!!.getLong("timestamp")
        }


            val context = this.applicationContext
            var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifyIntent = Intent(this, DealRevealUserActivity::class.java)

//            val title = "Sample Notification"
            val title = intent!!.getStringExtra("title")
            val message = intent!!.getStringExtra("desc")

            notifyIntent.putExtra("title", title)
            notifyIntent.putExtra("message", message)
            notifyIntent.putExtra("notification", true)
            val StartTime = intent!!.getStringExtra("Starttimenumber")
            val Day = intent!!.getStringExtra("Day")
            val ID = intent!!.getStringExtra("ID")


            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            var cal = Calendar.getInstance(Locale.getDefault())

            var startmin = StartTime.toString()
            var startminSTR = startmin.takeLast(2)
            var starthour = StartTime.toString()
            var starthourSTR = starthour.dropLast(2)

            Log.d("start minute ", startminSTR)
            Log.d("start hours ", starthourSTR)
            Log.d("uid", ID!!)
            Log.d("day", Day!!)

            val MON = Day.contains("MON")
            val TUE = Day.contains("TUE")
            val WED = Day.contains("WED")
            val THU = Day.contains("THU")
            val FRI = Day.contains("FRI")
            val SAT = Day.contains("SAT")
            val SUN = Day.contains("SUN")

            if (MON) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            } else if (TUE) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
            } else if (WED) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
            } else if (THU) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
            } else if (FRI) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
            } else if (SAT) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            } else if (SUN) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                Log.d("Sunday", Day)
            }

            cal.setTimeInMillis(System.currentTimeMillis())
            cal.set(Calendar.HOUR_OF_DAY, 14);
            cal.set(Calendar.MINUTE,36);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)


            val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val res = this.resources
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                mNotification = Notification.Builder(this, CHANNEL_ID)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.question)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setContentTitle(title.toString())
                    .setStyle(Notification.BigTextStyle()
                        .bigText(message))
                    .setContentText(message).build()
            } else {

                mNotification = Notification.Builder(this)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.question)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle(title.toString())
                    .setStyle(Notification.BigTextStyle()
                        .bigText(message))
                    .setSound(uri)
                    .setContentText(message).build()

            }



            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // mNotificationId is a unique int for each notification that you must define
            notificationManager.notify(mNotificationId, mNotification)
        }


}