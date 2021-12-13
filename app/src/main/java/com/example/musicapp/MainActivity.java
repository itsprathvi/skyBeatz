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
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Window window;
    BottomNavigationView bottomNavigationView;
    NavController navcontroller;
    GridView GV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GV = findViewById(R.id.gridView);

        ArrayList<cardModel> cardModelArrayList = new ArrayList<cardModel>();
        cardModelArrayList.add(new cardModel("Arjun Janya", R.drawable.youtube,"Jeena Jeena","Manikya"));
        cardModelArrayList.add(new cardModel("Ravi Basrur", R.drawable.youtube,"Jooke", "KGF"));
        cardModelArrayList.add(new cardModel("Hari Krishna", R.drawable.youtube, "Jaani","Yuvarathna"));
        cardModelArrayList.add(new cardModel("Yograj", R.drawable.youtube,"Karadi","Paramathma"));
        cardModelArrayList.add(new cardModel("SPB", R.drawable.youtube, "Namasthe","Ranna"));
        cardModelArrayList.add(new cardModel("Vasuki", R.drawable.youtube,"Kagadada","Kirik Party"));
        cardModelArrayList.add(new cardModel("Yograj", R.drawable.youtube,"Karadi","Paramathma"));
        cardModelArrayList.add(new cardModel("SPB", R.drawable.youtube, "Namasthe","Ranna"));
        cardModelArrayList.add(new cardModel("Vasuki", R.drawable.youtube,"Kagadada","Kirik Party"));

        cardAdapter adapter = new cardAdapter(this, cardModelArrayList);
        GV.setAdapter(adapter);


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







//    ListView listview;
//    String lists[] = { "Liked Songs" , "Favorites","My Albums"};





//        listview = (ListView) findViewById(R.id.listview);
//        ArrayAdapter<String> arr;
//        arr = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, lists);
//        listview.setAdapter(arr);