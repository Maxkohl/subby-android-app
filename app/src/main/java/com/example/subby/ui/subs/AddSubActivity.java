package com.example.subby.ui.subs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.subby.R;

public class AddSubActivity extends AppCompatActivity {

    public static final int SUB_REQUEST = 1;
    private EditText mSubName;
    private EditText mSubPrice;
    private EditText mSubNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        mSubName = findViewById(R.id.addSubName);
        mSubPrice = findViewById(R.id.addSubPrice);
        mSubNote = findViewById(R.id.addSubNote);

    }

    public void returnSubscription(View view) {
        String subName = mSubName.getText().toString();
        String subPrice = mSubPrice.getText().toString();
        String subNote = mSubNote.getText().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra("name", subName);
        replyIntent.putExtra("price", subPrice);
        replyIntent.putExtra("note", subNote);
        replyIntent.putExtra("isSubscribed", true);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}