package com.a02204704.caloriecounter2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.a02204704.caloriecounter2.fragments.DailyCalorieFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(R.id.fragment_container, DailyCalorieFragment.class, null)
                    .commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        NavigationView navigationView = findViewById(R.id.navView);

        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.open();
        });

        navigationView.setNavigationItemSelectedListener(MenuItem -> {
            MenuItem.setChecked(true);

            if (MenuItem.getItemId() == R.id.Daily_Calories) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.fragment_container, DailyCalorieFragment.class, null)
                        .commit();
            }
//
//            if (MenuItem.getItemId() == R.id.Upcoming_Events) {
//                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
//                        .replace(R.id.fragment_container, UpcomingEventsFragment.class, null)
//                        .commit();
//            }
//
//            if (MenuItem.getItemId() == R.id.User_Profile) {
//                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
//                        .replace(R.id.fragment_container, UserProfileFragment.class, null)
//                        .commit();
//            }

            return true;
        });
    }
}