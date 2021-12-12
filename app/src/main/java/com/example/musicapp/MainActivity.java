package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Window window;
    BottomNavigationView bottomNavigationView;
    NavController navcontroller;
    ListView listview;
    String lists[] = { "Liked Songs" , "Favorites","My Albums"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);
//        ArrayAdapter<String> arr;
//        arr = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, lists);
//        listview.setAdapter(arr);

        //Hide Mobile Router Bar
        View overlay  = findViewById(R.id.front);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        //

        // Navigation bar Color Change
        if(Build.VERSION.SDK_INT>=21){
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        //

        // Routed Fragments of Navbar
        navcontroller = Navigation.findNavController(this, R.id.fragmentContainerView);
        bottomNavigationView =  findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navcontroller);
        //

    }
}