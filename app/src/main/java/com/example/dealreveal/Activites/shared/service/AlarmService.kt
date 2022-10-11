package com.example.dealreveal.Activites.shared.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dealreveal.Activites.shared.receiver.AlarmReceiver
import com.example.dealreveal.Activites.shared.util.Constants

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setExactAlarm(timeInMillis: Long,uid:Int){
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT_ALARM
                    putExtra(Constants.EXACT_EXACT_ALARM_TIME, timeInMillis)
                    putExtra("uid", uid)
                }
            ,uid)
        )

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setReptetitveAlarm(timeInMillis: Long,uid:Int,Title:String){
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Title
                    putExtra(Constants.EXACT_EXACT_ALARM_TIME, timeInMillis)
                    putExtra("uid", uid)
                    putExtra("Title", Title)
                }
                ,uid)
            )

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {

        alarmManager?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }else{
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }
    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent, UID : Int) = PendingIntent.getBroadcast(
        context,
        UID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


}