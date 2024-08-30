package com.example.volumemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class ProfileAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        int profileId = intent.getIntExtra("profile_id", -1);

        if (profileId == -1) return;

        Profile profile = databaseHelper.getProfileById(profileId);
        if (profile == null) return;

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if ("ACTIVATE_PROFILE".equals(intent.getAction())) {
            // Set volume according to the profile
            audioManager.setStreamVolume(AudioManager.STREAM_RING, profile.getVolume(), 0);
            profile.setActive(true);
            databaseHelper.updateProfile(profile);
            Toast.makeText(context, "Profile activated: " + profile.getName(), Toast.LENGTH_SHORT).show();
        } else if ("DEACTIVATE_PROFILE".equals(intent.getAction())) {
            // Optionally, reset the volume or use a default value
            audioManager.setStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_SAME, 0);  // Reset to previous volume or default
            profile.setActive(false);
            databaseHelper.updateProfile(profile);
            Toast.makeText(context, "Profile deactivated: " + profile.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
