package fr.paris.kalliyan_julien.petco

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val channelID = "channel1"
const val notificationID = 1

class NotifManager : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent){
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra("animal")+": "+intent.getStringExtra("title"))
            .setContentText(intent.getStringExtra("message"))
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}