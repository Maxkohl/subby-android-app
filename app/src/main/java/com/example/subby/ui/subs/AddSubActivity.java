package com.example.subby.ui.subs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
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
    private EditText mSubName;
    private EditText mSubPrice;
    private EditText mSubNote;

    private Date subDueDate;
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
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.datePicker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String year_string = Integer.toString(year);
        String month_string = Integer.toString(month);
        String day_string = Integer.toString(day);
        String date_message = "Monthly Due Date: " + day_string;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            subDueDate = format.parse(day_string + "-" + month_string + "-" + year_string);
        } catch (ParseException e) {
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