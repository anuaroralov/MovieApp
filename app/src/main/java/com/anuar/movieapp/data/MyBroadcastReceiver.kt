package com.anuar.movieapp.data

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.anuar.movieapp.R


class MyBroadcastReceiver : BroadcastReceiver() {

    private lateinit var context: Context
    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        showNotification()
    }

    private fun showNotification() {
        if (checkPermission()) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("MoviaApp")
                .setContentText("кэш обновлен")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            createNotificationChannel()

            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
        else {
            //не знаю как через broadcastReceiver сделать request, а через активити костылями не хочу
            Toast.makeText(context, "Permission required to post notifications", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission(): Boolean {
        val permissionState =
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = ("channel_name")
            val descriptionText = ("channel_description")
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private var NOTIFICATION_ID = 123
    }
}
