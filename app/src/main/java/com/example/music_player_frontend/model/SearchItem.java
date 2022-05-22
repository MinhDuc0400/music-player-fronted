package com.example.music_player_frontend.model;

import java.util.List;

public class SearchItem {
    private List<Artist> artistList;
    private List<Song> songList;

    public SearchItem(List<Artist> artistList, List<Song> songList) {
        this.artistList = artistList;
        this.songList = songList;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
