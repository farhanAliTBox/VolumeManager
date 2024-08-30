package com.example.volumemanager;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText profileNameEditText;
    private SeekBar volumeSeekBar;
    private CheckBox mondayCheckBox;
    private TimePicker startTimePicker, endTimePicker;
    private Button updateProfileButton;
    private DatabaseHelper databaseHelper;
    private Profile profile;

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

        databaseHelper = new DatabaseHelper(this);
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

        // Load the profile data into the fields
        String profileName = getIntent().getStringExtra("profile_name");
        profile = databaseHelper.getProfileByName(profileName);
        if (profile != null) {
            profileNameEditText.setText(profile.getName());
            volumeSeekBar.setProgress(profile.getVolume());
            // Here you should load the days and times from the profile into the UI elements
        }

        updateProfileButton.setOnClickListener(view -> {
            String profileName2 = profileNameEditText.getText().toString();
            int volume = volumeSeekBar.getProgress();
            String startTime = startTimePicker.getHour() + ":" + startTimePicker.getMinute();
            String endTime = endTimePicker.getHour() + ":" + endTimePicker.getMinute();

            Profile updatedProfile = new Profile();
            updatedProfile.setId(profile.getId());
            updatedProfile.setName(profileName2);
            updatedProfile.setVolume(volume);
            updatedProfile.setStartTime(startTime);
            updatedProfile.setEndTime(endTime);
            updatedProfile.setDays(profile.getDays()); // Assuming days remain the same

            databaseHelper.updateProfile(updatedProfile);

            // Update alarms
            AlarmHelper.setProfileAlarm(this, updatedProfile);

            Toast.makeText(UpdateProfileActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}
