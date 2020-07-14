package com.example.subby.ui.subs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.subby.R;

import org.w3c.dom.Text;

public class SubDetailsActivity extends AppCompatActivity {

    private TextView subName;
    private TextView subPrice;
    private TextView subNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

        subName = findViewById(R.id.subNameDetails);
        subPrice = findViewById(R.id.subPriceDetails);
        subNote = findViewById(R.id.subNoteDetails);


        Intent intent = getIntent();
        subName.setText(intent.getStringExtra("name"));
        subPrice.setText(intent.getStringExtra("price"));
        subNote.setText(intent.getStringExtra("notes"));
    }
}