package com.example.volumemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "VolumeManager.db";
    private static final int DATABASE_VERSION = 2;  // Increment the version for the new column

    private static final String TABLE_PROFILES = "profiles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_VOLUME = "volume";
    private static final String COLUMN_DAYS = "days";
    private static final String COLUMN_START_TIME = "start_time";
    private static final String COLUMN_END_TIME = "end_time";
    private static final String COLUMN_ACTIVE = "active";  // New column

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PROFILES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_VOLUME + " INTEGER, " +
                COLUMN_DAYS + " TEXT, " +
                COLUMN_START_TIME + " TEXT, " +
                COLUMN_END_TIME + " TEXT, " +
                COLUMN_ACTIVE + " INTEGER" +  // New column
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_PROFILES + " ADD COLUMN " + COLUMN_ACTIVE + " INTEGER DEFAULT 0");
        }
    }

    public long addProfile(String name, int volume, String days, String startTime, String endTime, boolean active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_VOLUME, volume);
        values.put(COLUMN_DAYS, days);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);
        values.put(COLUMN_ACTIVE, active ? 1 : 0);

        long id = db.insert(TABLE_PROFILES, null, values);
        db.close();
        return id;
    }
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                profile.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                profile.setVolume(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VOLUME)));
                profile.setDays(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS)));
                profile.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_TIME)));
                profile.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_TIME)));
                profiles.add(profile);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return profiles;
    }


    public Profile getProfileByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILES, null, COLUMN_NAME + "=?", new String[]{name}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Profile profile = new Profile();
            profile.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            profile.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            profile.setVolume(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VOLUME)));
            profile.setDays(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS)));
            profile.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_TIME)));
            profile.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_TIME)));
            profile.setActive(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTIVE)) == 1);
            cursor.close();
            return profile;
        } else {
            return null;
        }
    }
    public Profile getProfileById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Profile profile = null;
        Cursor cursor = db.query(TABLE_PROFILES, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            profile = new Profile();
            profile.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            profile.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            profile.setVolume(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VOLUME)));
            profile.setDays(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS)));
            profile.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_TIME)));
            profile.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_TIME)));
            cursor.close();
        }
        db.close();
        return profile;
    }


    public int updateProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, profile.getName());
        values.put(COLUMN_VOLUME, profile.getVolume());
        values.put(COLUMN_DAYS, profile.getDays());
        values.put(COLUMN_START_TIME, profile.getStartTime());
        values.put(COLUMN_END_TIME, profile.getEndTime());
        values.put(COLUMN_ACTIVE, profile.isActive() ? 1 : 0);

        int rowsUpdated = db.update(TABLE_PROFILES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(profile.getId())});
        db.close();
        return rowsUpdated;
    }

    public int deleteProfile(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleted = db.delete(TABLE_PROFILES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return deleted;
    }
}
