package com.example.volumemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Switch activateProfileSwitch;
    private Button deleteProfileButton, updateProfileButton;
    private String profileName;
    private DatabaseHelper databaseHelper;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = getIntent().getStringExtra("profile_name");
        databaseHelper = new DatabaseHelper(this);
        profile = databaseHelper.getProfileByName(profileName);

        activateProfileSwitch = findViewById(R.id.switch_activate_profile);
        deleteProfileButton = findViewById(R.id.btn_delete_profile);
        updateProfileButton = findViewById(R.id.btn_update_profile);

        if (profile != null) {
            activateProfileSwitch.setChecked(profile.isActive());

            activateProfileSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                profile.setActive(isChecked);
                databaseHelper.updateProfile(profile);
            });

            deleteProfileButton.setOnClickListener(view -> {
                databaseHelper.deleteProfile(profile.getId());
                finish();
            });

            updateProfileButton.setOnClickListener(view -> {
                Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                intent.putExtra("profile_name", profileName);
                startActivity(intent);
            });
        }
    }
}
