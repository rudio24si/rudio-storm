package com.example.rudio_storm.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar
import kotlin.jvm.java

object ReminderHelper {

    /**
     * Set reminder berdasarkan jumlah menit dari sekarang.
     * Cocok untuk studi kasus reminder yang waktunya ditentukan dinamis oleh user,
     * misal: "ingatkan saya 30 menit lagi sewa kamar akan berakhir".
     */
    fun setReminderInMinutes(
        context: Context,
        minutesFromNow: Int,
        title: String,
        message: String,
        targetActivity: Class<*>,
        requestCode: Int = 0
    ) {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MINUTE, minutesFromNow)
        }

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
            putExtra("target_activity", targetActivity.name)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun setReminder(
        context: Context,
        hour: Int,
        minute: Int,
        title: String,
        message: String,
        targetActivity: Class<*>
    ) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
            putExtra("target_activity", targetActivity.name)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}