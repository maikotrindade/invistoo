package com.jumbomob.invistoo.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.jumbomob.invistoo.R;

public class NotificationUtil {

    public static final int NOTIFICATION_ID = 1;

    public static boolean sendNotification(String title, String message,
                                           PendingIntent pendingIntent) {
        final Context context = InvistooApplication.getInstance().getApplicationContext();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title).setContentText(message).setTicker(message)
                .setColor(Color.RED)
                .setLights(Color.RED, 500, 500)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(Notification.PRIORITY_MAX);

        if (pendingIntent != null) {
            notificationBuilder.setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService
                (Context.NOTIFICATION_SERVICE);

        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = notificationBuilder.build();
        } else {
            notification = notificationBuilder.getNotification();
        }
        notificationManager.notify(NOTIFICATION_ID, notification);

        return true;
    }
}
