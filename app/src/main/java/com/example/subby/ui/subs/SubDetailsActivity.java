package com.example.subby.ui.subs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subby.MainActivity;
import com.example.subby.R;

public class SubDetailsActivity extends AppCompatActivity {

    private TextView subName;
    private TextView subPrice;
    private TextView subNote;
    private RelativeLayout headerLayout;
    private SubsViewModel subsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

        subName = findViewById(R.id.subNameDetails);
        subPrice = findViewById(R.id.subPriceDetails);
        subNote = findViewById(R.id.subNoteDetails);
        headerLayout = findViewById(R.id.header);

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
}