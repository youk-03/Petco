package fr.paris.kalliyan_julien.petco

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.util.Calendar

const val channelID = "channel1"

class NotifManager : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent){
        val title= intent.getStringExtra("title")?:""
        val animal= intent.getStringExtra("animal")?:""
        val message= intent.getStringExtra("message")?:""

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(animal+": "+title)
            .setContentText(message)
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifId= intent.getIntExtra("notifId",0)
        manager.notify(notifId, notification)

        val repeat= intent.getIntExtra("repeat",0)
        val time= intent.getLongExtra("time",0)
        val calendar : Calendar= Calendar.getInstance()
        calendar.timeInMillis = time

        if(repeat==1){
            calendar.add(Calendar.DAY_OF_YEAR,1)
        }
        if(repeat==2){
            calendar.add(Calendar.WEEK_OF_YEAR,1)
        }
        if(repeat==3){
            calendar.add(Calendar.MONTH,1)
        }

        scheduleNotification(title,message,animal,calendar.timeInMillis,repeat,context)
    }
}