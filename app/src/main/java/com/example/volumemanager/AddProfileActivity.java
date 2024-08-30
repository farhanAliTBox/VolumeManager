package com.example.volumemanager;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProfileActivity extends AppCompatActivity {

    private EditText profileNameEditText;
    private SeekBar volumeSeekBar;
    private CheckBox mondayCheckBox;
    private TimePicker startTimePicker, endTimePicker;
    private Button addProfileButton;
    private DatabaseHelper databaseHelper;

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

        databaseHelper = new DatabaseHelper(this);

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
            String profileName = profileNameEditText.getText().toString();
            int volume = volumeSeekBar.getProgress();
            String startTime = startTimePicker.getHour() + ":" + startTimePicker.getMinute();
            String endTime = endTimePicker.getHour() + ":" + endTimePicker.getMinute();

            // For simplicity, let's assume all days are selected.
            String days = "Mon,Tue,Wed,Thu,Fri,Sat,Sun";

            long id = databaseHelper.addProfile(profileName, volume, days, startTime, endTime, false);

            if (id > 0) {
                // Set alarms for the new profile
                Profile newProfile = databaseHelper.getProfileById((int) id);
                if (newProfile != null) {
                    AlarmHelper.setProfileAlarm(this, newProfile);
                }

                Toast.makeText(AddProfileActivity.this, "Profile added!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddProfileActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(AddProfileActivity.this, "Failed to add profile.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
