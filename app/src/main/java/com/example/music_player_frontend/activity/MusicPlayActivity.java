package com.example.music_player_frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.music_player_frontend.Data;
import com.example.music_player_frontend.R;
import com.example.music_player_frontend.api.ApiService;
import com.example.music_player_frontend.database.PlayListDB;
import com.example.music_player_frontend.model.Song;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPlayActivity extends AppCompatActivity {
    ImageView backBtn, thumbnailSongPlaying, preBtn, pauseBtn, nextBtn, favBtn;
    TextView titleSongPlaying, artistSongPlaying, startTime, songTime;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    WifiManager.WifiLock wifiLock;
    private List<String> songIdList;
    private int currentIndex;
    private Song currentSong;
    private Handler handler = new Handler();
    private static int sTime = 0, eTime = 0, oTime = 0;
    public List<String> getSongIdList() {
        return songIdList;
    }

    public void setSongIdList(List<String> songIdList) {
        this.songIdList = songIdList;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void playPauseSong() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            wifiLock.release();
            pauseBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        } else {
            mediaPlayer.start();
            wifiLock.acquire();
            pauseBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        }
    }

    public void nextSong() {
        if (songIdList.size() == 1 || (currentIndex + 1) == songIdList.size()) {
            Toast.makeText(getApplicationContext(), "No song to play next", Toast.LENGTH_SHORT).show();
        }
        else if (mediaPlayer != null && songIdList.size() != 1) {
            mediaPlayer.release();
            mediaPlayer = null;
            ApiService.apiService.getSong(songIdList.get(currentIndex += 1)).enqueue(new Callback<Song>() {
                @Override
                public void onResponse(Call<Song> call, Response<Song> response) {
                    Song song = response.body();
                    if (song != null && response.isSuccessful()) {
                        onSetPlaySong(song);
                    }
                }

                @Override
                public void onFailure(Call<Song> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_LONG).show();
                    nextSong();
                }
            });

        }
    }

    public void onSetPlaySong(Song song) {
        this.currentSong = song;
        PlayListDB db = new PlayListDB(this);
        if (db.isSongLiked(song.getId())) {
            favBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        pauseBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        String url = song.getUrl();
        Picasso.get().load(song.getThumbnail()).into(thumbnailSongPlaying);
        titleSongPlaying.setText(song.getTitle());
        artistSongPlaying.setText(song.getArtists());
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextSong();
            }
        });
        wifiLock.acquire();
        eTime = mediaPlayer.getDuration();
        sTime = mediaPlayer.getCurrentPosition();
        seekBar.setMax(eTime);
        songTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(eTime),
                TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(eTime))) );
        startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
                TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(sTime))) );
        seekBar.setProgress(sTime);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        handler.postDelayed(UpdateSongTome, 100);
        pauseBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
    }

    public void addRemoveFavoriteSong() {
        PlayListDB db = new PlayListDB(this);
        if (db.isSongLiked(currentSong.getId())) {
            db.deleteItem(currentSong.getId());
            favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            Toast.makeText(this, "Removed song from your library", Toast.LENGTH_SHORT).show();
        } else {
            db.addItem(currentSong);
            favBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
            Toast.makeText(this, "Added song to your library", Toast.LENGTH_SHORT).show();
        }
    }

    public void preSong() {
        if (songIdList.size() == 1 || (currentIndex) == 0) {
            Toast.makeText(getApplicationContext(), "No song to play previous", Toast.LENGTH_SHORT).show();
        }
        else if (mediaPlayer != null && songIdList.size() != 1) {
            mediaPlayer.release();
            mediaPlayer = null;
            ApiService.apiService.getSong(songIdList.get(currentIndex -= 1)).enqueue(new Callback<Song>() {
                @Override
                public void onResponse(Call<Song> call, Response<Song> response) {
                    Song song = response.body();
                    if (song != null && response.isSuccessful()) {
                        onSetPlaySong(song);
                    }
                }

                @Override
                public void onFailure(Call<Song> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_LONG).show();
                    nextSong();
                }
            });

        }
    }

    private Runnable UpdateSongTome = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                sTime = mediaPlayer.getCurrentPosition();
                startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))) );
                seekBar.setProgress(sTime);
                handler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            wifiLock.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        songIdList = new ArrayList<>();
        backBtn = findViewById(R.id.back_btn_song_playing);
        thumbnailSongPlaying =findViewById(R.id.thumbnail_song_playing);
        preBtn = findViewById(R.id.play_pre);
        pauseBtn = findViewById(R.id.pause);
        nextBtn = findViewById(R.id.play_next);
        favBtn = findViewById(R.id.fav_btn);
        titleSongPlaying = findViewById(R.id.title_song_playing);
        artistSongPlaying = findViewById(R.id.artist_song_playing);
        seekBar = findViewById(R.id.seek_bar);
        startTime = findViewById(R.id.text_start_time);
        songTime = findViewById(R.id.text_song_time);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRemoveFavoriteSong();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPauseSong();
            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preSong();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });
        Intent intent = getIntent();
        Boolean isOpen = intent.getBooleanExtra("open", false);
        wifiLock = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE))
                        .createWifiLock(WifiManager.WIFI_MODE_FULL, "myLock");
        if (isOpen && Data.songList.size() > 0) {
            setSongIdList(Data.songList);
            setCurrentIndex(0);
            ApiService.apiService.getSong(songIdList.get(0)).enqueue(new Callback<Song>() {
                @Override
                public void onResponse(Call<Song> call, Response<Song> response) {
                    Song song = response.body();
                    if (song != null && response.isSuccessful()) {
                        onSetPlaySong(song);
                    }
                }

                @Override
                public void onFailure(Call<Song> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_LONG).show();
                    nextSong();
                }
            });
        }
    }
}