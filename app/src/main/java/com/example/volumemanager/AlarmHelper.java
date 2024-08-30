package com.example.volumemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AlarmHelper {

    public static void setProfileAlarm(Context context, Profile profile) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent activateIntent = new Intent(context, ProfileAlarmReceiver.class);
        activateIntent.setAction("ACTIVATE_PROFILE");
        activateIntent.putExtra("profile_id", profile.getId());
        PendingIntent activatePendingIntent = PendingIntent.getBroadcast(context, profile.getId(), activateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar startTime = Calendar.getInstance();
        String[] startTimeParts = profile.getStartTime().split(":");
        startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeParts[0]));
        startTime.set(Calendar.MINUTE, Integer.parseInt(startTimeParts[1]));

        // Schedule activation alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), activatePendingIntent);

        Intent deactivateIntent = new Intent(context, ProfileAlarmReceiver.class);
        deactivateIntent.setAction("DEACTIVATE_PROFILE");
        deactivateIntent.putExtra("profile_id", profile.getId());
        PendingIntent deactivatePendingIntent = PendingIntent.getBroadcast(context, profile.getId() + 1, deactivateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar endTime = Calendar.getInstance();
        String[] endTimeParts = profile.getEndTime().split(":");
        endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeParts[0]));
        endTime.set(Calendar.MINUTE, Integer.parseInt(endTimeParts[1]));

        // Schedule deactivation alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime.getTimeInMillis(), deactivatePendingIntent);
    }
}
