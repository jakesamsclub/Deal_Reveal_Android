package com.example.dealreveal.Activites.users

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.util.*

/**
 * Created by devdeeds.com on 5/12/17.
 */

class NotificationUtils {
    var cal = Calendar.getInstance(Locale.getDefault())


    fun setNotification(timeInMilliSeconds: Long, activity: Activity,Title: String,Desc: String,StartTime: Int,Day: String,ID:String) {

        //------------  alarm settings start  -----------------//


            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java) // AlarmReceiver1 = broadcast receiver

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)
            alarmIntent.putExtra("title", Title)
            alarmIntent.putExtra("desc", Desc)
            alarmIntent.putExtra("Starttimenumber", StartTime)
            alarmIntent.putExtra("Day", Day)
            alarmIntent.putExtra("ID", ID)



            var startmin = StartTime.toString()
            var startminSTR = startmin.takeLast(2)
            var starthour = StartTime.toString()
            var starthourSTR = starthour.dropLast(2)

            Log.d("start minute1 ", startminSTR)
            Log.d("start hours1 ", starthourSTR)
            Log.d("uid", ID)
            Log.d("day", Day)

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



            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        Toast.makeText(activity,cal.getTimeInMillis().toString(), Toast.LENGTH_LONG).show()
        Log.d("TAG", cal.getTimeInMillis().toString())

        }

        //------------ end of alarm settings  -----------------//

}


