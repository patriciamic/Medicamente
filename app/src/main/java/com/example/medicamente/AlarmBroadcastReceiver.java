package com.example.medicamente;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.example.medicamente.ui.MainActivity;

import java.time.LocalTime;

import static com.example.medicamente.data.Constants.*;
import static com.example.medicamente.ui.BoalaActivity.MED_NAME;


public class AlarmBroadcastReceiver extends BroadcastReceiver {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        LocalTime time2 =  LocalTime.now();
        Log.e("TIME2:", time2+"");
//            Toast.makeText(context, "MERE", Toast.LENGTH_SHORT).show();
//        }

            Toast.makeText(context, "ALARM !!!", Toast.LENGTH_LONG).show();
            String medName = intent.getStringExtra(MED_NAME);
            Log.e("AAAAAAAAA", medName);
            Intent i = new Intent(context, AlarmActivity.class);
            Intent intentNotification = new Intent(context, MainActivity.class);
            //context.startActivity(new Intent(context, AlarmActivity.class));
            i.putExtra(MED_NAME, medName);

            context.startActivity(i);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentNotification, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setContentText(NOTIF_CONTENT)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(NOTIF_ID, mBuilder.build());

    }

}
