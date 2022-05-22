package com.example.music_player_frontend.model;

import java.io.Serializable;

public class PlaylistItem implements Serializable {
//    id: el.encodeId,
//    title: el.title,
//    artists: el.artistsNames,
//    thumbnail: el.thumbnailM
    String id, title, artists, thumbnail;

    public PlaylistItem(String id, String title, String artists, String thumbnail) {
        this.id = id;
        this.title = title;
        this.artists = artists;
        this.thumbnail = thumbnail;
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
}
