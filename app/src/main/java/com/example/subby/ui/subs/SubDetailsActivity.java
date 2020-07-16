package com.example.subby.ui.subs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.subby.AlarmReceiver;
import com.example.subby.MainActivity;
import com.example.subby.R;

public class SubDetailsActivity extends AppCompatActivity {

    private static int NOTIFICATION_ID;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    private TextView subName;
    private TextView subPrice;
    private TextView subNote;
    private RelativeLayout headerLayout;
    private SubsViewModel subsViewModel;
    private Switch notifySwitch;
    private NotificationManager mNotificationManager;
    private ScrollView detailsScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

        subName = findViewById(R.id.subNameDetails);
        subPrice = findViewById(R.id.subPriceDetails);
        subNote = findViewById(R.id.subNoteDetails);
        headerLayout = findViewById(R.id.header);
        detailsScrollView = findViewById(R.id.details_scrollview);

        notifySwitch = findViewById(R.id.notify_switch);

        subsViewModel =
                ViewModelProviders.of(this).get(SubsViewModel.class);

        Intent intent = getIntent();
        NOTIFICATION_ID = intent.getIntExtra("id", 0);
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

        final Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyIntent.putExtra("subName", subName.getText());
        notifyIntent.putExtra("id", NOTIFICATION_ID);

        boolean notifyOn = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        notifySwitch.setChecked(notifyOn);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;
                final PendingIntent notifyPendingIntent =
                        PendingIntent.getBroadcast(getApplicationContext(), NOTIFICATION_ID,
                        notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (isChecked) {
                    //TODO Change back to Not Realtime after testing
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime(), notifyPendingIntent);
                    toastMessage = "Notification On";
                } else {
                    mNotificationManager.cancel(NOTIFICATION_ID);
                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }
                    notifyPendingIntent.cancel();
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

}