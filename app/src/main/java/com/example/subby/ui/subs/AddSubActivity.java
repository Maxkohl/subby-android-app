package com.example.subby.ui.subs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.subby.DatePickerFragment;
import com.example.subby.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddSubActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final int SUB_REQUEST = 1;
    private static final String TAG = "AddSubActivity";
    private EditText mSubName;
    private EditText mSubPrice;
    private EditText mSubNote;

    private long mSubDueDate;
    private String mSubColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        mSubName = findViewById(R.id.addSubName);
        mSubPrice = findViewById(R.id.addSubPrice);
        mSubNote = findViewById(R.id.addSubNote);

        Spinner spinner = findViewById(R.id.addSubColor);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_labels_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }


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
        replyIntent.putExtra("color", mSubColor);
        replyIntent.putExtra("dueDate", mSubDueDate);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.datePicker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String year_string = Integer.toString(year);
        int correctedMonth = month + 1;
        String month_string = Integer.toString(correctedMonth);
        String day_string = Integer.toString(day);
        String date_message =  month_string + "/" + day_string + "/" + year_string;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date stringFormatted = format.parse(year_string + "-" + month_string + "-" + day_string);
            mSubDueDate = stringFormatted.getTime() / 1000;
        } catch (ParseException e) {
            Log.d(TAG, getString(R.string.data_parsing_exception_message));
            e.printStackTrace();
        }

        Toast.makeText(this, date_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        mSubColor = spinnerLabel;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}