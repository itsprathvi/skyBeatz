package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Window window;
    BottomNavigationView bottomNavigationView;
    Button submit;
    EditText searchBar;
    NavController navcontroller;
    GridView GV;
    ArrayList<cardModel> cardModelArrayList = new ArrayList<cardModel>();
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GV = findViewById(R.id.gridView);
        mQueue = Volley.newRequestQueue(this);
        searchBar = findViewById(R.id.search_bar);
        submit = findViewById(R.id.search_button);
//        getSongs("jagadodharana");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSongs(searchBar.getText().toString());
                Log.e(TAG, "onClick: "+searchBar.getText().toString() );
            }
        });

//        cardModelArrayList.add(new cardModel("Arjun Janya", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576","Jeena Jeena","Manikya"));
//        cardModelArrayList.add(new cardModel("Ravi Basrur", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576","Jooke", "KGF"));
//        cardModelArrayList.add(new cardModel("Hari Krishna", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576", "Jaani","Yuvarathna"));
//        cardModelArrayList.add(new cardModel("Yograj", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576","Karadi","Paramathma"));
//        cardModelArrayList.add(new cardModel("SPB", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576", "Namasthe","Ranna"));
//        cardModelArrayList.add(new cardModel("Vasuki", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576","Kagadada","Kirik Party"));
//        cardModelArrayList.add(new cardModel("Yograj", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576","Karadi","Paramathma"));
//        cardModelArrayList.add(new cardModel("SPB", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576", "Namasthe","Ranna"));
//        cardModelArrayList.add(new cardModel("Vasuki", "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576","Kagadada","Kirik Party"));
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (cardModelArrayList != null && cardModelArrayList.size() > 0) {
//            cardAdapter adapter = new cardAdapter(this, cardModelArrayList);
//            GV.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
//    }

    private void getSongs(String songName) {
        String URL = "https://spotify-artist-api.herokuapp.com/track/" + songName;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                // on data receive
                response -> {
                    try {
                        JSONObject Tracks = response.getJSONObject("tracks");
                        JSONArray items = Tracks.getJSONArray("items");
                        cardModelArrayList.clear();
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            String name = item.getString("name").toString();
                            String prev_url = item.getString("preview_url").toString();
                            String artist = item.getJSONArray("artists").getJSONObject(0).getString("name").toString();
                            String artist_id = item.getJSONArray("artists").getJSONObject(0).getString("id").toString();
                            String img_url = item.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url").toString();
                            cardModelArrayList.add(new cardModel(artist, img_url,name, artist_id));
                        }
                        Log.e(TAG, "getSongs: "+ cardModelArrayList.toString());
                        cardAdapter adapter = new cardAdapter(this, cardModelArrayList);
                        GV.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: Error of calls");
                    }
                },
                // on error
                error -> {
                    Log.e(TAG, "onResponse: Error of api");
                    error.printStackTrace();
                });

        mQueue.add(request);
    }
}







//    ListView listview;
//    String lists[] = { "Liked Songs" , "Favorites","My Albums"};





//        listview = (ListView) findViewById(R.id.listview);
//        ArrayAdapter<String> arr;
//        arr = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, lists);
//        listview.setAdapter(arr);