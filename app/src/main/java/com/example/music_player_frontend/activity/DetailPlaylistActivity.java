package com.example.music_player_frontend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_frontend.Data;
import com.example.music_player_frontend.MainActivity;
import com.example.music_player_frontend.R;
import com.example.music_player_frontend.adapter.RecyclerViewLikedSongAdapter;
import com.example.music_player_frontend.adapter.RecyclerViewPlaylistAdapter;
import com.example.music_player_frontend.api.ApiService;
import com.example.music_player_frontend.model.PlaylistItem;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPlaylistActivity extends AppCompatActivity implements RecyclerViewLikedSongAdapter.ItemListener {
    ImageView thumbnailPlaylist, backBtn;
    TextView descriptionPlaylist;
    RecyclerView recyclerView;
    RecyclerViewPlaylistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Boolean isOpen = intent.getBooleanExtra("openPlaylist", false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_playlist);
        thumbnailPlaylist = findViewById(R.id.thumbnail_playlist);
        descriptionPlaylist = findViewById(R.id.text_des_playlist_detail);
        recyclerView = findViewById(R.id.recycle_view_detail_playlist);
        backBtn = findViewById(R.id.back_btn);
        adapter = new RecyclerViewPlaylistAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter.setItemListener(this::onItemClick);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
        if (Data.topChartItem != null && isOpen) {
            System.out.println(Data.topChartItem.getTitle());
            descriptionPlaylist.setText(Data.topChartItem.getDescription());
            Picasso.get().load(Data.topChartItem.getThumbnail()).into(thumbnailPlaylist);
            ApiService.apiService.getDetailPlaylist(Data.topChartItem.getEncodeId()).enqueue(new Callback<List<PlaylistItem>>() {
                @Override
                public void onResponse(Call<List<PlaylistItem>> call, Response<List<PlaylistItem>> response) {
                    List<PlaylistItem> list = response.body();
                    if (list != null && response.isSuccessful()) {
                        adapter.setItemList(list);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<PlaylistItem>> call, Throwable t) {
                    Toast.makeText(getApplication(), "API Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        PlaylistItem item = adapter.getItem(position);
        ArrayList<String> listToPlay = new ArrayList<>();
        listToPlay.add(item.getId());
        for (PlaylistItem playlistItem : adapter.getItemList()) {
            if (playlistItem.getId() != item.getId()) {
                listToPlay.add(playlistItem.getId());
            }
        }
        Data.songList = listToPlay;
        Intent intent = new Intent(this, MusicPlayActivity.class);
        intent.putExtra("open", true);

        startActivity(intent);
    }
}