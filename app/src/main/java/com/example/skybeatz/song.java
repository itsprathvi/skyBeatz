package com.example.skybeatz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class song extends AppCompatActivity {
    TextView artist, song;
    ImageView song_img;
    Button back, play, pause;
    MediaPlayer mediaPlayer;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        artist = findViewById(R.id.artist_text);
        song = findViewById(R.id.songName);
        song_img = findViewById(R.id.song_img);
        back = findViewById(R.id.back_button);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        // initializing media player
        mediaPlayer = new MediaPlayer();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(! mediaPlayer.isPlaying()){
                    playAudio();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    // pausing the media player if media player
                    // is playing we are calling below line to
                    // stop our media player.
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();

                    // below line is to display a message
                    // when media player is paused.
                    Toast.makeText(song.this, "Audio has been paused", Toast.LENGTH_SHORT).show();
                } else {
                    // this method is called when media
                    // player is not playing.
                    Toast.makeText(song.this, "Audio has not played", Toast.LENGTH_SHORT).show();
                }
            }
        });

        intent = getIntent();
//        cardModel cm = (cardModel) intent.getSerializableExtra("songObj");
        artist.setText("Song By " + intent.getStringExtra("artistName"));
        song.setText(intent.getStringExtra("songName"));
        Picasso.get().load(intent.getStringExtra("songImg")).into(song_img);
    }

    private void playAudio() {

        String audioUrl = intent.getStringExtra("preview_url");

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Cannot Play This. Sorry :(", Toast.LENGTH_SHORT).show();
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
}