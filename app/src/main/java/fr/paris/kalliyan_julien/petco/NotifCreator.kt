package fr.paris.kalliyan_julien.petco

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import fr.paris.kalliyan_julien.petco.data.Animaux
import java.util.Date

fun scheduleNotification(title: String, message: String, animal: String, time : Long, context: Context) {

    val intent = Intent(context, NotifManager::class.java)
    intent.putExtra("animal", animal)
    intent.putExtra("title", title)
    intent.putExtra("message", message)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificationID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if(alarmManager.canScheduleExactAlarms()){
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }
    showAlert(time, title, message,context)
}

private fun showAlert(time: Long, title: String, message: String,context: Context) {
    val date = Date(time)
    val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)
    val timeFormat = android.text.format.DateFormat.getTimeFormat(context)

    AlertDialog.Builder(context)
        .setTitle("Activité Planifiée!")
        .setMessage(
            "Titre: " + title +
                    "\nMessage: " + message +
                    "\nA: " + dateFormat.format(date) + " " + timeFormat.format(date))
        .setPositiveButton("Okay"){_,_ ->}
        .show()
}

fun createNotificationChannel(context: Context) {
    val name = "Notif Activity Channel"
    val desc = "A channel used by Petco to remember you of your scheluded acticity"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelID, name, importance)
    channel.description = desc
    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}