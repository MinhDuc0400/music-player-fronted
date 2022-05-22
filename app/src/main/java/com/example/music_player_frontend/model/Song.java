package com.example.music_player_frontend.model;

public class Song {
    private String id, title, thumbnail, url, artists;
    private int time;

    public Song(String thumbnail, String url, int time) {
        this.thumbnail = thumbnail;
        this.url = url;
        this.time = time;
    }

    public Song(String id, String title, String thumbnail, String url, String artists, int time) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
        this.artists = artists;
        this.time = time;
    }

    public Song(String title, String thumbnail, String url, String artists, int time) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
        this.artists = artists;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
