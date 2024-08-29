package com.example.volumemanager;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText profileNameEditText;
    private SeekBar volumeSeekBar;
    private CheckBox mondayCheckBox;
    private TimePicker startTimePicker, endTimePicker;
    private Button updateProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile); // Reusing the same layout

        profileNameEditText = findViewById(R.id.edit_profile_name);
        volumeSeekBar = findViewById(R.id.seekbar_volume);
        mondayCheckBox = findViewById(R.id.checkbox_monday);

        updateProfileButton = findViewById(R.id.btn_add_profile);


        startTimePicker = findViewById(R.id.timepicker_start);
        endTimePicker = findViewById(R.id.timepicker_end);



        TimePicker startPicker = (TimePicker) findViewById(R.id.timepicker_start);
        startPicker.setIs24HourView(true);
        Calendar calendar1 = Calendar.getInstance();

        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int m1 = calendar1.get(Calendar.MINUTE);

        startPicker.setCurrentHour(h1);
        startPicker.setCurrentMinute(m1);

        TimePicker endPicker = (TimePicker) findViewById(R.id.timepicker_start);
        endPicker.setIs24HourView(true);
        Calendar calendar2 = Calendar.getInstance();

        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int m2 = calendar2.get(Calendar.MINUTE);

        endPicker.setCurrentHour(h2);
        endPicker.setCurrentMinute(m2);











        updateProfileButton.setText("Update Profile");

        // Set the TimePickers to 24-hour view
        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startTimePicker.setHour(0);  // Set default values if needed
            startTimePicker.setMinute(0);
            endTimePicker.setHour(0);
            endTimePicker.setMinute(0);
        }

        // Load the profile data into the fields (This is an example)
        String profileName = getIntent().getStringExtra("profile_name");
        profileNameEditText.setText(profileName);

        updateProfileButton.setOnClickListener(view -> {
            // Update profile logic
            finish();
        });
    }
}
