package fr.paris.kalliyan_julien.petco

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.core.content.ContextCompat.startActivity
import androidx.datastore.preferences.protobuf.NullValue
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.paris.kalliyan_julien.petco.data.ActivitesPlanifiees
import fr.paris.kalliyan_julien.petco.data.BD
import java.util.Date


fun hash(title: String, message: String, animal: String, time : Long) : Int{
    return (title.hashCode()+animal.hashCode()+message.hashCode()+time.hashCode()).hashCode()
}

fun scheduleNotification(title: String, message: String, animal: String, time : Long, context: Context) : PendingIntent? {
    val notifId= hash(title,message,animal,time)

    val intent = Intent(context, NotifManager::class.java)
    intent.putExtra("animal", animal)
    intent.putExtra("title", title)
    intent.putExtra("message", message)
    intent.putExtra("notifId", notifId)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        notifId,
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
        showAlert(time, title, message,context)
        return pendingIntent
    } else {
        alarmResquestDialog(context)
        return null
    }
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

private fun alarmResquestDialog(context: Context) {
    MaterialAlertDialogBuilder(context)
        .setMessage("Vous devez activer les notifications pour pouvoir utiliser cette fonctionnalité.")
        .setPositiveButton("Ok") { _,_ ->
            context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
        .setCancelable(true)
        .show()
}

fun deleteNotif(activite: String,animal: String,activitesPlanifiees: ActivitesPlanifiees,context: Context){
    val intent = PendingIntent.getBroadcast(
        context,
        hash(activite,activitesPlanifiees.note,animal,activitesPlanifiees.date),
        Intent(),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )


    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(intent)
}