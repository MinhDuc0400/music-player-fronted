package com.example.music_player_frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_frontend.R;
import com.example.music_player_frontend.model.Artist;
import com.example.music_player_frontend.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSearchArtistAdapter extends RecyclerView.Adapter<RecyclerViewSearchArtistAdapter.SearchViewHolder> {
    private List<Artist> artistList;
    ItemListener itemListener;

    public RecyclerViewSearchArtistAdapter() {
        artistList = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }


    public void setArtistList(List<Artist> itemList) {
        this.artistList = itemList;
        notifyDataSetChanged();
    }


    public Artist getItem(int position) {
        return artistList.get(position);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            Artist item = getItem(position);
            Picasso.get().load(item.getThumbnail()).into(holder.imageView);
            holder.textView.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail_search);
            textView = itemView.findViewById(R.id.text_view_search);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.onArtistItemClick(v, getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onArtistItemClick(View view, int position);
    }
}
