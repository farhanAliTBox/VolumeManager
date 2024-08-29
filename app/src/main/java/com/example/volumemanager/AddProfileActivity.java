package com.example.volumemanager;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class AddProfileActivity extends AppCompatActivity {

    private EditText profileNameEditText;
    private SeekBar volumeSeekBar;
    private CheckBox mondayCheckBox;
    private TimePicker startTimePicker, endTimePicker;
    private Button addProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        profileNameEditText = findViewById(R.id.edit_profile_name);
        volumeSeekBar = findViewById(R.id.seekbar_volume);
        mondayCheckBox = findViewById(R.id.checkbox_monday);
        startTimePicker = findViewById(R.id.timepicker_start);
        endTimePicker = findViewById(R.id.timepicker_end);
        addProfileButton = findViewById(R.id.btn_add_profile);

        // Set the TimePickers to 24-hour view
        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startTimePicker.setHour(0);  // Set default values if needed
            startTimePicker.setMinute(0);
            endTimePicker.setHour(0);
            endTimePicker.setMinute(0);
        }

        addProfileButton.setOnClickListener(view -> {
            // Add profile to database or list
            finish();
        });
    }
}
