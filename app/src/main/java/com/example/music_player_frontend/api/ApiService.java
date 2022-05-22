package com.example.music_player_frontend.api;

import com.example.music_player_frontend.model.PlaylistItem;
import com.example.music_player_frontend.model.SearchItem;
import com.example.music_player_frontend.model.Song;
import com.example.music_player_frontend.model.TopChartItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    @GET("get-top-100")
    Call<List<TopChartItem>> getTop100();

    @GET("get-detail-playlist/{id}")
    Call<List<PlaylistItem>> getDetailPlaylist(@Path("id") String id);

    @GET("get-song/{id}")
    Call<Song> getSong(@Path("id") String id);

    @GET("search/{key}")
    Call<SearchItem> search(@Path("key") String key);
}
