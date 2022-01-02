package com.example.skybeatz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class song extends AppCompatActivity {
    TextView artist, song;
    ImageView song_img, back, play, pause, play_next, play_prev;
    public static MediaPlayer mediaPlayer;
    ArrayList<cardModel> cardModelArrayList = new ArrayList<cardModel>();
    BottomNavigationView bottomNavigationView;
    int position;
    Intent intent;

    public void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        artist = findViewById(R.id.artist_text);
        song = findViewById(R.id.songName);
        song_img = findViewById(R.id.song_img);
        back = findViewById(R.id.back_button);
        play = findViewById(R.id.play);
        play_next = findViewById(R.id.skip_next);
        play_prev = findViewById(R.id.skip_prev);
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");
        cardModelArrayList = (ArrayList) bundle.getParcelableArrayList("songs");
        loadSong();

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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
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

        // initializing media player
//        mediaPlayer = new MediaPlayer();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer =  new MediaPlayer();
        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        playAudio();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!= null) {
                    if (mediaPlayer.isPlaying()) {
                        play.setImageResource(R.drawable.ic_play);
                        mediaPlayer.pause();
                    } else {
                        play.setImageResource(R.drawable.ic_pause);
                        mediaPlayer.start();
                    }
                }
            }
        });

//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                play_next.performClick();
//            }
//        });

        play_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!(mediaPlayer ==null)) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    position = (position+1)%cardModelArrayList.size();
                    loadSong();
                    mediaPlayer = new MediaPlayer();
                    playAudio();
            }
        });

        play_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!(mediaPlayer ==null)) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    position = (position - 1)<0?cardModelArrayList.size()-1: position-1;
                    loadSong();
                    mediaPlayer = new MediaPlayer();
                    playAudio();
            }
        });

    }

    private void loadSong() {
        cardModel c = cardModelArrayList.get(position);
        artist.setText("Song By " + c.getArtistName());
        song.setText(c.getName());
        Picasso.get().load(c.getImg_url()).into(song_img);
    }

    private void playAudio() {

        String audioUrl = cardModelArrayList.get(position).getPrev_url();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();
            play.setImageResource(R.drawable.ic_pause);
            // below line is use to display a toast message.
            Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    play_next.performClick();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer = null;
            Toast.makeText(this, "Cannot Play This. Sorry :(", Toast.LENGTH_SHORT).show();
//            setTimeout(() -> {Log.e("TAG", "playAudio: ");play_next.performClick();}, 3000);
//            play_next.performClick();
        }
    }


}