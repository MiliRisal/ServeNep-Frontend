package com.example.servenep.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.servenep.UI.DashboardActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class MyFirebasemessingServices : FirebaseMessagingService() {
    private val ADMIN_CHANNEL_ID = "admin_channel"

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val intent = Intent(this, DashboardActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random().nextInt(3000)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,0,intent, PendingIntent.FLAG_ONE_SHOT
        )
        val largeIcon = BitmapFactory.decodeResource(
            resources,android.R.drawable.ic_delete
        )
        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this,ADMIN_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setLargeIcon(largeIcon)
            .setContentTitle(p0.data["title"])
            . setContentText(p0.data["message"])
            .setAutoCancel(true)
            .setSound(notificationSoundUri)
            .setContentIntent(pendingIntent)

        // set notification color to match your app color template//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            notificationBuilder.color = ContextCompat.getColor(applicationContext, android.R.color.background_dark)
        }

        notificationManager.notify(notificationID, notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager) {
        val adminChannelName ="New Notification"
        val adminChannelDescription = "Device to device notification"

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(
            ADMIN_CHANNEL_ID,
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager?.createNotificationChannel(adminChannel)
    }
}