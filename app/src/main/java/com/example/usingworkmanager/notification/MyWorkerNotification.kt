package com.example.usingworkmanager.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.usingworkmanager.MainActivity
import com.example.usingworkmanager.R

class MyWorkerNotification(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        createNotification()
        return Result.success()
    }

    fun createNotification() {
        val builder: NotificationCompat.Builder
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channelID"
            var channel: NotificationChannel? =
                notificationManager.getNotificationChannel(channelId)
            if (channel == null) {
                channel =
                    NotificationChannel(
                        channelId,
                        "channelName",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                channel.description = "channelDescription"
                notificationManager.createNotificationChannel(channel)
            }

            builder = NotificationCompat.Builder(applicationContext, channelId)
                .setContentTitle("Worker Manager Notification")
                .setContentText("Worker Manager example with Kotlin")
                .setSmallIcon(R.drawable.ic_person)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        } else {
            builder = NotificationCompat.Builder(applicationContext)
                .setContentTitle("Worker Manager Notification")
                .setContentText("Worker Manager example with Kotlin")
                .setSmallIcon(R.drawable.ic_person)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
        }

        notificationManager.notify(1, builder.build())
    }
}

