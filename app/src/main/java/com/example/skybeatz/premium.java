package com.example.skybeatz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class premium extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        bottomNavigationView =  findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.premium2);

//        bottomNavigationView.setOnNavigationItemSelectedListener();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home3:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.profile2:
                        startActivity(new Intent(getApplicationContext(), profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.premium2:
                        return true;
                }
                return false;
            }
        });


        //
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_FULLSCREEN);

        // full screens
        // Navigation bar Color Change
        window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
    }
}