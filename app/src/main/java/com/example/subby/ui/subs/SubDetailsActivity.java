package com.example.subby.ui.subs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.subby.MainActivity;
import com.example.subby.R;

public class SubDetailsActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    private TextView subName;
    private TextView subPrice;
    private TextView subNote;
    private RelativeLayout headerLayout;
    private SubsViewModel subsViewModel;
    private Switch notifySwitch;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

        subName = findViewById(R.id.subNameDetails);
        subPrice = findViewById(R.id.subPriceDetails);
        subNote = findViewById(R.id.subNoteDetails);
        headerLayout = findViewById(R.id.header);
        notifySwitch = findViewById(R.id.notify_switch);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        subsViewModel =
                ViewModelProviders.of(this).get(SubsViewModel.class);

        Intent intent = getIntent();
        subName.setText(intent.getStringExtra("name"));
        subPrice.setText(intent.getStringExtra("price"));
        subNote.setText(intent.getStringExtra("notes"));
        switch (intent.getStringExtra("color")) {
            case "Red":
                headerLayout.setBackgroundColor(getResources().getColor(R.color.Red));
                break;
            case "Green":
                headerLayout.setBackgroundColor(getResources().getColor(R.color.Green));
                break;
            case "Blue":
                headerLayout.setBackgroundColor(getResources().getColor(R.color.Blue));
                break;
            case "Purple":
                headerLayout.setBackgroundColor(getResources().getColor(R.color.Purple));
                break;
            case "Yellow":
                headerLayout.setBackgroundColor(getResources().getColor(R.color.Yellow));
                break;
            case "White":
                headerLayout.setBackgroundColor(getResources().getColor(R.color.White));
                subName.setTextColor(getResources().getColor(R.color.Black));
                subPrice.setTextColor(getResources().getColor(R.color.Black));
                break;
        }

        createNotificationChannel();

        notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;
                if (isChecked) {
                    deliverNotification(SubDetailsActivity.this);
                    toastMessage = "Notification On";
                } else {
                    //TODO Change this to cancel NOTIFICATION_ID that's set by ID from table
                    mNotificationManager.cancelAll();
                    toastMessage = "Notification Off";
                }
                Toast.makeText(SubDetailsActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteSubscription(View view) {
        final String subscriptionName = subName.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_sub_alert_title).setMessage("Are you sure you want to " +
                "delete your" +
                " " + subscriptionName + " subscriptions?");
        builder.setPositiveButton(R.string.ok_alert_title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                subsViewModel.deleteSubscription(subscriptionName);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(),
                        subscriptionName + " subscription deleted"
                        , Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.cancel_alert_title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Canceled delete subscription",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Subscription Notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies whenever payment of a monthly " +
                    "subscription is due");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
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