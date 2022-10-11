package com.example.dealreveal.Activites.shared.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import com.example.dealreveal.Activites.shared.util.Constants
import com.example.dealreveal.Activites.users.DealRevealUserActivity
import com.example.dealreveal.Activites.users.NotificationService
import com.example.dealreveal.R

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var mNotification: Notification

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXACT_EXACT_ALARM_TIME, 0L)
        val alarmuid = intent.getIntExtra("uid",0)
        val Dealtitle = intent.getStringExtra("Title")
        when (intent.action) {

            Dealtitle -> {
              //  setRepetitiveAlarm(AlarmService(context),alarmuid, Dealtitle.toString())
               // buildNotification(context, Dealtitle.toString()+" Is Now Live!", "Tap here to see why you set this reminder <3")

                var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notifyIntent = Intent(context, DealRevealUserActivity::class.java)

                notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                notifyIntent.putExtra("dealuid","poop")

                val pendingIntent = PendingIntent.getActivity(context, alarmuid, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                    mNotification = Notification.Builder(context, NotificationService.CHANNEL_ID)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.question)
                        .setAutoCancel(true)
                        .setContentTitle(Dealtitle.toString() +" is now live!")
                        .setStyle(
                            Notification.BigTextStyle()
                            .bigText("Tap here to see why you set this reminder <3"))
                        .setContentText("Tap here to see why you set this reminder <3").build()
                } else {

                    mNotification = Notification.Builder(context)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.question)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentTitle(Dealtitle.toString())
                        .setStyle(
                            Notification.BigTextStyle()
                            .bigText("Tap here to see why you set this reminder <3"))
                        .setSound(uri)
                        .setContentText("Tap here to see why you set this reminder <3").build()

                }


                notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                // mNotificationId is a unique int for each notification that you must define
                notificationManager.notify(alarmuid, mNotification)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
//    private fun setRepetitiveAlarm(alarmService: AlarmService,uid:Int,Title:String) {
//        val cal = Calendar.getInstance().apply {
//            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
//
//        }
//        alarmService.setReptetitveAlarm(cal.timeInMillis,uid,Title)
//    }

    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()

}