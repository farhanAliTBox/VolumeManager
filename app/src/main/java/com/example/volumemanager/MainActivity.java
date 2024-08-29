package com.example.volumemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView profileListView;
    private FloatingActionButton fabAddProfile;
    private ArrayAdapter<String> profileAdapter;
    private ArrayList<String> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileListView = findViewById(R.id.profile_list);
        fabAddProfile = findViewById(R.id.fab_add_profile);
        profiles = new ArrayList<>();

        profileAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, profiles);
        profileListView.setAdapter(profileAdapter);

        fabAddProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddProfileActivity.class);
            startActivity(intent);
        });

        profileListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("profile_name", profiles.get(position));
            startActivity(intent);
        });
    }
}
