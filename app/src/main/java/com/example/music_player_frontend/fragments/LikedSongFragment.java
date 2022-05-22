package com.example.music_player_frontend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_frontend.Data;
import com.example.music_player_frontend.MainActivity;
import com.example.music_player_frontend.R;
import com.example.music_player_frontend.activity.MusicPlayActivity;
import com.example.music_player_frontend.adapter.RecyclerViewLikedSongAdapter;
import com.example.music_player_frontend.adapter.RecyclerViewPlaylistAdapter;
import com.example.music_player_frontend.api.ApiService;
import com.example.music_player_frontend.database.PlayListDB;
import com.example.music_player_frontend.model.PlaylistItem;
import com.example.music_player_frontend.model.Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedSongFragment extends Fragment implements RecyclerViewLikedSongAdapter.ItemListener{
    RecyclerView recyclerView;
    RecyclerViewLikedSongAdapter adapter;
    TextView numberSong;
    FloatingActionButton floatingActionButton;
    private PlayListDB db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liked_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycle_view_liked);
        adapter = new RecyclerViewLikedSongAdapter();
        floatingActionButton = view.findViewById(R.id.play_shuffle_btn);
        numberSong = view.findViewById(R.id.number_song);
        db = new PlayListDB(getContext());
        List<Song> likedList = db.getAll();
        numberSong.setText(likedList.size() + " songs");
        System.out.println(likedList.toArray());
        adapter.setItemList(likedList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listToPlay = new ArrayList<>();
                for (Song song : adapter.getItemList()) {
                        listToPlay.add(song.getId());
                }
                Collections.shuffle(listToPlay);
                Data.songList = listToPlay;
                Intent intent = new Intent(getContext(), MusicPlayActivity.class);
                intent.putExtra("open", true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Song> likedList = db.getAll();
        numberSong.setText(likedList.size() + " songs");
        adapter.setItemList(likedList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Song item = adapter.getItem(position);
        ArrayList<String> listToPlay = new ArrayList<>();
        listToPlay.add(item.getId());
        for (Song song : adapter.getItemList()) {
            if (song.getId() != item.getId()) {
                listToPlay.add(song.getId());
            }
        }
        Data.songList = listToPlay;
        Intent intent = new Intent(getContext(), MusicPlayActivity.class);
        intent.putExtra("open", true);
        startActivity(intent);
    }
}
