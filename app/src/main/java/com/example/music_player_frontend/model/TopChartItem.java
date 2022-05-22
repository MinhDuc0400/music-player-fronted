package com.example.music_player_frontend.model;

import java.io.Serializable;

public class TopChartItem implements Serializable {
    private String encodeId, title, thumbnail, description;

    public TopChartItem(String encodeId, String title, String thumbnail, String description) {
        this.encodeId = encodeId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;

    }

    public String getEncodeId() {
        return encodeId;
    }

    public void setEncodeId(String encodeId) {
        this.encodeId = encodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
