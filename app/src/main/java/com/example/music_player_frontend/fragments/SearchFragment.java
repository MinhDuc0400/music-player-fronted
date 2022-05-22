package com.example.music_player_frontend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.example.music_player_frontend.activity.DetailPlaylistActivity;
import com.example.music_player_frontend.activity.MusicPlayActivity;
import com.example.music_player_frontend.adapter.RecyclerViewSearchArtistAdapter;
import com.example.music_player_frontend.adapter.RecyclerViewSearchSongAdapter;
import com.example.music_player_frontend.api.ApiService;
import com.example.music_player_frontend.model.Artist;
import com.example.music_player_frontend.model.PlaylistItem;
import com.example.music_player_frontend.model.SearchItem;
import com.example.music_player_frontend.model.Song;
import com.example.music_player_frontend.model.TopChartItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements RecyclerViewSearchSongAdapter.ItemListener, RecyclerViewSearchArtistAdapter.ItemListener{
    RecyclerView recyclerViewArtist, recyclerViewSong;
    SearchView searchView;
    TextView textViewSong, textViewArtist;
    RecyclerViewSearchSongAdapter songAdapter;
    RecyclerViewSearchArtistAdapter artistAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewArtist = view.findViewById(R.id.recycle_view_search_artists);
        recyclerViewSong = view.findViewById(R.id.recycle_view_search_songs);
        textViewSong = view.findViewById(R.id.text_view_song_search);
        textViewArtist = view.findViewById(R.id.text_view_artist_search);
        searchView = view.findViewById(R.id.search_view);
        artistAdapter = new RecyclerViewSearchArtistAdapter();
        songAdapter = new RecyclerViewSearchSongAdapter();
        LinearLayoutManager songManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager artistManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSong.setLayoutManager(songManager);
        recyclerViewArtist.setLayoutManager(artistManager);
        artistAdapter.setItemListener(this);
        songAdapter.setItemListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("onQuerySubmt " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println(newText);
                if (newText == "") {
                    textViewSong.setText("");
                    textViewArtist.setText("");
                    artistAdapter.setArtistList(new ArrayList<>());
                    songAdapter.setSongList(new ArrayList<>());
                    recyclerViewArtist.setAdapter(artistAdapter);
                    recyclerViewSong.setAdapter(songAdapter);
                }
                ApiService.apiService.search(newText).enqueue(new Callback<SearchItem>() {
                    @Override
                    public void onResponse(Call<SearchItem> call, Response<SearchItem> response) {
                        SearchItem data = response.body();
                        if (data != null && response.isSuccessful()) {
                            System.out.println("infi");
                            artistAdapter.setArtistList(data.getArtistList());
                            songAdapter.setSongList(data.getSongList());
                            recyclerViewArtist.setAdapter(artistAdapter);
                            recyclerViewSong.setAdapter(songAdapter);
                            textViewSong.setText("Songs");
                            textViewArtist.setText("Artists");
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchItem> call, Throwable t) {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "API Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return false;
            }
        });
    }


    @Override
    public void onArtistItemClick(View view, int position) {
        Artist item = artistAdapter.getItem(position);
        TopChartItem topChartItem = new TopChartItem(item.getPlaylistId(),
                item.getName(),
                item.getThumbnail(),
                Integer.toString(item.getTotalNumber()));
        Data.topChartItem = topChartItem;
        Intent intent = new Intent(getContext(), DetailPlaylistActivity.class);
        intent.putExtra("openPlaylist", true);
        startActivity(intent);
    }

    @Override
    public void onSongItemClick(View view, int position) {
        Song item = songAdapter.getItem(position);
        ArrayList<String> listToPlay = new ArrayList<>();
        listToPlay.add(item.getId());
        Data.songList = listToPlay;
        Intent intent = new Intent(getContext(), MusicPlayActivity.class);
        intent.putExtra("open", true);
        startActivity(intent);
    }
}
