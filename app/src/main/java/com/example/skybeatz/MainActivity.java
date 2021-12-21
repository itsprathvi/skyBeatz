package com.example.skybeatz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    Bundle b;

    // gv
    ExpandableHeightGridView GV;
    ArrayList<cardModel> cardModelArrayList = new ArrayList<cardModel>();
    private RequestQueue mQueue;
    Button submit;
    EditText searchBar;
    Boolean searchingArtist = false;
    LinearLayout artistDiv;
    TextView artistName;
    ImageView artistImg;
    Artist artist;

    private ArraySaver saver;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

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

        // if not searching for artist hide the artistDiv
        if(!searchingArtist){
            artistDiv.setVisibility(View.GONE);
        }

        //1st get recomended songs
        getRecomendedSongs();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchingString = searchBar.getText().toString();
                if(!searchingString.isEmpty()) {

                    //if searching for an artist then it starts with "a-"
                    searchingArtist = searchingString.startsWith("a-");

                    //if searching for artist then show artistDiv
                    if (searchingArtist) {
                        artistDiv.setVisibility(View.VISIBLE);
                        getArtists(searchingString.replace("a-", ""));
                    } else {
                        artistDiv.setVisibility(View.GONE);
                        getSongs(searchingString);
                    }
                    Log.e(TAG, "onClick: " + searchBar.getText().toString());
                }
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //// bottom nav bar control
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///NAV BARS
        bottomNavigationView =  findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home3);


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

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////   Hide the default app nav bar and change the top color and remove bottom nav   //////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    @Override
    protected void onResume() {
        super.onResume();
        cardModelArrayList = Temp.getInstance().getArrayList();
        searchBar.setText(Temp.getInstance().getSongName());
        displaySongs(cardModelArrayList);
    }

    private void displaySongs(ArrayList<cardModel> cardModelArrayList) {
        Temp.getInstance().setSongName(searchBar.getText().toString());
        Temp.getInstance().setArrayList(cardModelArrayList);
        cardAdapter adapter = new cardAdapter(this, cardModelArrayList);
        GV.setAdapter(adapter);
        GV.setExpanded(true);

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "clicked" + position,Toast.LENGTH_LONG);
                Intent intent = new Intent(getApplicationContext(), song.class);
//                intent.putExtra("songObj", cardModelArrayList.get(position));
                intent.putExtra("songName", cardModelArrayList.get(position).getName());
                intent.putExtra("songImg", cardModelArrayList.get(position).getImg_url());
                intent.putExtra("artistName", cardModelArrayList.get(position).getArtistName());
                intent.putExtra("artistId", cardModelArrayList.get(position).getArtist_id());
                intent.putExtra("preview_url", cardModelArrayList.get(position).getPrev_url());
                startActivity(intent);
            }
        });
//        Toast.makeText(getApplicationContext(), "displayed",Toast.LENGTH_LONG).show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////// when ever the change of activity happens save the old state and use it when u come back ////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the searched song name
        Log.e(TAG, "called!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        outState.putParcelable("savedObject",saver);
        b = outState;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        saver = (ArraySaver) savedInstanceState.getParcelable("savedObject");
        cardModelArrayList = saver.getArrayList();
        String str = saver.getSearchString();
        displaySongs(cardModelArrayList);
        searchBar.setText(str);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////|                |///////////////////////////////////////////////
    ////////////////////////////////////////////////////|    API CALLS   |///////////////////////////////////////////////
    ////////////////////////////////////////////////////|                |///////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                            cardModelArrayList.add(new cardModel(name, prev_url, artist, artist_id, img_url));
                        }
//                        saver = new ArraySaver(songName, cardModelArrayList);
                        Log.e(TAG, "getSongs: "+ cardModelArrayList.toString());
                        displaySongs(cardModelArrayList);
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

    //gets song from API and displays
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
                            cardModelArrayList.add(new cardModel(name, prev_url, artist, artist_id, img_url));
                        }
                        saver = new ArraySaver("",cardModelArrayList);

                        Log.e(TAG, "recomendedSongs: "+ cardModelArrayList.toString());
                        displaySongs(cardModelArrayList);
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

    private void getArtists(String artists) {
        cardModelArrayList.clear();
        String URL = "https://spotify-artist-api.herokuapp.com/artist/"+ artists;

        //find artist id first
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                // on data receive
                response -> {
                    try {
                        JSONObject Tracks = response.getJSONObject("artists");
                        JSONArray items = Tracks.getJSONArray("items");
                        JSONObject item = items.getJSONObject(0);
                        artist.setId(item.getString("id"));
                        Log.e(TAG, "getArtists: GOT response:::" + item.getString("id") );
                        artist.setName(item.getString("name"));
                        artist.setProfile(item.getJSONArray("images").getJSONObject(0).getString("url"));

                        artistName.setText(artist.getName());
                        Picasso.get().load(artist.getProfile()).into(artistImg);

                        getArtists2(artists);
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

    private void getArtists2(String artists) {
        String URL = "https://spotify-artist-api.herokuapp.com/artist/"+artist.getId()+"/top-tracks";

        // from the artist id search for arist's songs
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                // on data receive
                response -> {
                    try {
//                        Log.e(TAG, "getArtists: GOT response:::"+response.toString() );
                        JSONArray Tracks = response.getJSONArray("tracks");
                        for (int i = 0; i < Tracks.length(); i++) {
                            JSONObject item =Tracks.getJSONObject(i);
                            String name = item.getString("name").toString();
                            String prev_url = item.getString("preview_url").toString();
                            String img_url;
                            try {
                                img_url = item.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url").toString();
                            }catch (Exception e){
                                img_url = "https://dominicans.in/wp-content/themes/dominicans2/framework/img/us-placeholder-square.jpg";
                            }
                            cardModelArrayList.add(new cardModel(name, prev_url, artist.getName(), artist.getId(), img_url));
                        }
                        saver = new ArraySaver("a-"+artists,cardModelArrayList);
                        displaySongs(cardModelArrayList);
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