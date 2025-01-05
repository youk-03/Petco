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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.paris.kalliyan_julien.petco.data.ActivitesPlanifiees
import java.util.Calendar
import java.util.Date


fun hash(title: String, message: String, animal: String, time : Long, repeat: Int) : Int{
    return (title.hashCode()+animal.hashCode()+message.hashCode()+time.hashCode()+repeat).hashCode()
}

fun scheduleNotification(title: String, message: String, animal: String, time : Long, repeat: Int, context: Context) : PendingIntent? {
    val notifId= hash(title,message,animal,time,repeat)

    val intent = Intent(context, NotifManager::class.java)
    intent.putExtra("animal", animal)
    intent.putExtra("title", title)
    intent.putExtra("message", message)
    intent.putExtra("notifId", notifId)
    intent.putExtra("time",time)
    intent.putExtra("repeat",repeat)

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
        hash(activite,activitesPlanifiees.note,animal,activitesPlanifiees.date,activitesPlanifiees.repeat),
        Intent(),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )


    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(intent)
}

fun repeatNotif(title : String,message : String ,animal : String,time : Long,repeat : Int,context : Context){
    val calendar= Calendar.getInstance()
    calendar.timeInMillis= time
    if(repeat==1){
        calendar.add(Calendar.DAY_OF_YEAR,1)
    } else if (repeat==2){
        calendar.add(Calendar.WEEK_OF_YEAR,1)
    } else if (repeat==3){
        calendar.add(Calendar.MONTH,1)
    }
    val newTime = calendar.timeInMillis

    val notifId= hash(title,message,animal,newTime,repeat)

    val intent = Intent(context, NotifManager::class.java)
    intent.putExtra("animal", animal)
    intent.putExtra("title", title)
    intent.putExtra("message", message)
    intent.putExtra("notifId", notifId)
    intent.putExtra("time",newTime)
    intent.putExtra("repeat",repeat)

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
            newTime,
            pendingIntent
        )
    }
}