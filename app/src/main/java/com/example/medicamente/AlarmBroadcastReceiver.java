package com.example.medicamente;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.medicamente.ui.MainActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "my chanel id";
    public static final String NOTIFICATION_TITLE = "get your pills";
    public static final String NOTIF_CONTENT = "BLA BLA BLA\nBLA BLA BLA\nBLA BLA BLA";
    public static final int NOTIF_ID = 667;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

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
