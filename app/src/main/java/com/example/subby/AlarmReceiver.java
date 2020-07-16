package com.example.subby;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.subby.ui.subs.SubDetailsActivity;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";



    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        deliverNotification(context);

    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, SubDetailsActivity.class);
        String subname = subName.getText().toString();
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID,
                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                PRIMARY_CHANNEL_ID).setSmallIcon(R.drawable.ic_baseline_money_24)
                .setContentTitle("Subscription Alert").setContentText("Your " + subname + " payment" +
                        " is due!").setContentIntent(contentPendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true).setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
