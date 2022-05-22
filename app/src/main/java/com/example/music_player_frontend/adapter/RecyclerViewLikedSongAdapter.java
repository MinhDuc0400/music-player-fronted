package com.example.music_player_frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_frontend.R;
import com.example.music_player_frontend.database.PlayListDB;
import com.example.music_player_frontend.model.PlaylistItem;
import com.example.music_player_frontend.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewLikedSongAdapter extends RecyclerView.Adapter<RecyclerViewLikedSongAdapter.LikedSongViewHolder>{
    private List<Song> itemList;
    private ItemListener itemListener;

    public RecyclerViewLikedSongAdapter() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public List<Song> getItemList() {
        return itemList;
    }

    public Song getItem(int position) {
        return itemList.get(position);
    }

    public void setItemList(List<Song> itemList) {
        this.itemList = itemList;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public LikedSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liked_song, parent, false);
        return new LikedSongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedSongViewHolder holder, int position) {
        Song item = getItem(position);
        holder.songName.setText(item.getTitle());
        holder.songArtists.setText(item.getArtists());
        Picasso.get().load(item.getThumbnail()).into(holder.songThumbnail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class LikedSongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView songName, songArtists;
        private ImageView songThumbnail, favBtn;
        public LikedSongViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_title);
            songArtists = itemView.findViewById(R.id.song_artist);
            songThumbnail = itemView.findViewById(R.id.thumbnail_song);
            favBtn = itemView.findViewById(R.id.remove_liked_song);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}
