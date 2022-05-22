package com.example.music_player_frontend.model;

import java.io.Serializable;

public class Artist implements Serializable {
//    "id": "IWZ97DB0",
//            "name": "Sơn Tùng M-TP",
//            "thumbnail": "https://photo-resize-zmp3.zmdcdn.me/w360_r1x1_jpeg/avatars/4/a/9/1/4a91d506fc7144c7716b9d3166f2c4b6.jpg",
//            "playlistId": "Z6BOEWAA",
//            "totalFollow": 2390452

    private String id, name, thumbnail, playlistId;
    private int totalNumber;

    public Artist(String id, String name, String thumbnail, String playlistId, int totalNumber) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.playlistId = playlistId;
        this.totalNumber = totalNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
}
