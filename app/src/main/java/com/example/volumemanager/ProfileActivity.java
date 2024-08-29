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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = getIntent().getStringExtra("profile_name");

        activateProfileSwitch = findViewById(R.id.switch_activate_profile);
        deleteProfileButton = findViewById(R.id.btn_delete_profile);
        updateProfileButton = findViewById(R.id.btn_update_profile);

        activateProfileSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Activate or deactivate profile
        });

        deleteProfileButton.setOnClickListener(view -> {
            // Delete profile logic
            finish();
        });

        updateProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
            intent.putExtra("profile_name", profileName);
            startActivity(intent);
        });
    }
}
