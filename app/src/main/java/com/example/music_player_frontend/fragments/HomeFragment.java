package com.example.music_player_frontend.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_frontend.Data;
import com.example.music_player_frontend.MainActivity;
import com.example.music_player_frontend.R;
import com.example.music_player_frontend.activity.DetailPlaylistActivity;
import com.example.music_player_frontend.activity.MusicPlayActivity;
import com.example.music_player_frontend.adapter.RecyclerViewHomeAdapter;
import com.example.music_player_frontend.api.ApiService;
import com.example.music_player_frontend.model.TopChartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements RecyclerViewHomeAdapter.ItemListener {
    private RecyclerView recyclerView;
    private RecyclerViewHomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycle_view_home);
        adapter = new RecyclerViewHomeAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter.setItemListener(this);
        ApiService.apiService.getTop100().enqueue(new Callback<List<TopChartItem>>() {
            @Override
            public void onResponse(Call<List<TopChartItem>> call, Response<List<TopChartItem>> response) {
               List<TopChartItem> list = response.body();
               if (list != null && response.isSuccessful()) {
                   adapter.setItemList(list);
                   recyclerView.setAdapter(adapter);
               }
            }

            @Override
            public void onFailure(Call<List<TopChartItem>> call, Throwable t) {
                Toast.makeText(getContext(), "API Error", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {
        TopChartItem item = adapter.getItem(position);
        TopChartItem topChartItem = new TopChartItem(item.getEncodeId(),
                item.getTitle(),
                item.getThumbnail(),
                item.getDescription());
        Data.topChartItem = topChartItem;
        Intent intent = new Intent(getContext(), DetailPlaylistActivity.class);
        intent.putExtra("openPlaylist", true);
        startActivity(intent);
    }
}
