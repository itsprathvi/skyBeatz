package com.example.skybeatz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.prefs.AbstractPreferences;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAin Activity";
    Window window;
    BottomNavigationView bottomNavigationView;

    // gv
    ExpandableHeightGridView GV;
    ArrayList<cardModel> cardModelArrayList = new ArrayList<cardModel>();
    private RequestQueue mQueue;
    Button submit;
    EditText searchBar;
    Boolean searchingArtist = true;
    LinearLayout artistDiv;
    TextView artistName;
    ImageView artistImg;
    Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // main activity
        artistDiv = findViewById(R.id.artist_div);
        GV = findViewById(R.id.gridView);
        mQueue = Volley.newRequestQueue(this);
        searchBar = findViewById(R.id.search_bar);
        submit = findViewById(R.id.search_button);
        artist = new Artist();
        artistName = findViewById(R.id.artist_name);
        artistImg = findViewById(R.id.artist_img);

        if(!searchingArtist){
            artistDiv.setSystemUiVisibility(View.GONE);
        }

        getRecomendedSongs();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(searchingArtist){
                    artistDiv.setSystemUiVisibility(View.VISIBLE);
                    getArtists(searchBar.getText().toString());
                }else {
                    artistDiv.setSystemUiVisibility(View.GONE);
                    getSongs(searchBar.getText().toString());
                }
                Log.e(TAG, "onClick: "+searchBar.getText().toString() );
            }
        });

        cardAdapter adapter = new cardAdapter(this, cardModelArrayList);
        GV.setAdapter(adapter);


        ///NAV BARS
        bottomNavigationView =  findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home3);

//        bottomNavigationView.setOnNavigationItemSelectedListener();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home3:
                        return  true;
                    case R.id.profile2:
                        startActivity(new Intent(getApplicationContext(), profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.premium2:
                        startActivity(new Intent(getApplicationContext(), premium.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //Hide Mobile Router Bar
        View overlay  = findViewById(R.id.front);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        //


        // full screens
        // Navigation bar Color Change
        if(Build.VERSION.SDK_INT>=21){
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        //action bar hiding
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    private void getArtists(String artists) {
        cardModelArrayList.clear();
        String URL = "https://spotify-artist-api.herokuapp.com/artist/"+ artists;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                // on data receive
                response -> {
                    try {
                        JSONObject Tracks = response.getJSONObject("artists");
                        JSONArray items = Tracks.getJSONArray("items");
                        JSONObject item = items.getJSONObject(0);
                        artist.setId(item.getString("id"));
                        artist.setName(item.getString("name"));
                        artist.setProfile(item.getJSONArray("images").getJSONObject(0).getString("url"));

                        artistName.setText(artist.getName());
                        Picasso.get().load(artist.getProfile()).into(artistImg);
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

        URL = "https://spotify-artist-api.herokuapp.com/artist/"+artist.getId()+"/top-tracks";

        request = new JsonObjectRequest(Request.Method.GET, URL, null,
                // on data receive
                response -> {
                    try {
                        JSONArray Tracks = response.getJSONArray("tracks");
                        for (int i = 0; i < Tracks.length(); i++) {
                            JSONObject item =Tracks.getJSONObject(i);
                            String name = item.getString("name").toString();
                            String prev_url = item.getString("preview_url").toString();
                            String artist = item.getJSONArray("artists").getJSONObject(0).getString("name").toString();
                            String artist_id = item.getJSONArray("artists").getJSONObject(0).getString("id").toString();
                            String img_url = item.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url").toString();
                            cardModelArrayList.add(new cardModel(artist, img_url,name, artist_id));

                            cardAdapter adapter = new cardAdapter(this, cardModelArrayList);
                            GV.setAdapter(adapter);


                            Temp.getInstance().setSongName(artists);
                            Temp.getInstance().setCardAdptr(adapter);
                        }
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the searched song name
//        outState.putString("song_name", searchBar.getText().toString());
//        outState.putParcelableArrayList("cardModelArrayList", (ArrayList<? extends Parcelable>) cardModelArrayList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        savedInstanceState.getString("songname");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String songName = Temp.getInstance().getSongName();
//        cardModelArrayList = Temp.getInstance().getArrayList();
//        Log.e(TAG, "onResume: " + cardModelArrayList.toString());
        cardAdapter adapter = Temp.getInstance().getCardAdptr();
//        cardAdapter adapter = new cardAdapter(this, cardModelArrayList);
        GV.setAdapter(adapter);
        searchBar.setText(songName);

        //research the song;
    }

    private void getSongs(String songName) {
        cardModelArrayList.clear();
        String URL = "https://spotify-artist-api.herokuapp.com/track/" + songName;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                // on data receive
                response -> {
                    try {
                        JSONObject Tracks = response.getJSONObject("tracks");
                        JSONArray items = Tracks.getJSONArray("items");
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


                        Temp.getInstance().setSongName(songName);
//                        Temp.getInstance().setArrayList((ArrayList<cardModel>) cardModelArrayList.clone());
                        Temp.getInstance().setCardAdptr(adapter);
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
    private void getRecomendedSongs() {
        String URL = "https://spotify-artist-api.herokuapp.com/top-tracks/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                // on data receive
                response -> {
                    try {
                        JSONObject Tracks = response.getJSONObject("tracks");
                        JSONArray items = Tracks.getJSONArray("items");
                        for (int i = 0; i < 16; i++) {
                            JSONObject item = items.getJSONObject(i).getJSONObject("track");
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


                        Temp.getInstance().setSongName("");
//                        Temp.getInstance().setArrayList((ArrayList<cardModel>) cardModelArrayList.clone());
                        Temp.getInstance().setCardAdptr(adapter);
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