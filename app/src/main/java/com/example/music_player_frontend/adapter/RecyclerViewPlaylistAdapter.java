package com.example.music_player_frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_frontend.R;
//import com.example.music_player_frontend.api.ItemListener;
import com.example.music_player_frontend.model.PlaylistItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPlaylistAdapter extends RecyclerView.Adapter<RecyclerViewPlaylistAdapter.PlaylistViewHolder>{
    private List<PlaylistItem> itemList;
    private ItemListener itemListener;

    public RecyclerViewPlaylistAdapter() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public List<PlaylistItem> getItemList() {
        return itemList;
    }

    public PlaylistItem getItem(int position) {
        return itemList.get(position);
    }

    public void setItemList(List<PlaylistItem> itemList) {
        this.itemList = itemList;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        System.out.println("ONBINDVIEWHOLDER");
        PlaylistItem item = getItem(position);
        holder.ranking.setText(Integer.toString(position + 1));
        holder.songName.setText(item.getTitle());
        holder.songArtists.setText(item.getArtists());
        Picasso.get().load(item.getThumbnail()).into(holder.songThumbnail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ranking, songName, songArtists;
        private ImageView songThumbnail;
        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            ranking = itemView.findViewById(R.id.song_ranking);
            songName = itemView.findViewById(R.id.song_title);
            songArtists = itemView.findViewById(R.id.song_artist);
            songThumbnail = itemView.findViewById(R.id.thumbnail_song);
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
