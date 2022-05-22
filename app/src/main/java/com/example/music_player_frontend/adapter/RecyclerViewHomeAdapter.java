package com.example.music_player_frontend.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_frontend.R;
//import com.example.music_player_frontend.api.ItemListener;
import com.example.music_player_frontend.model.TopChartItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.HomeViewHolder>{
    private List<TopChartItem> itemList;
    private ItemListener itemListener;

    public RecyclerViewHomeAdapter() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }
    public TopChartItem getItem(int position) {
        return itemList.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_chart, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        TopChartItem item = getItem(position);
        holder.chartTitle.setText(item.getTitle());
        holder.chartDes.setText(item.getDescription());
        Picasso.get().load(item.getThumbnail()).into(holder.thumbnail);
//        holder.thumbnail.setImageURI(Uri.parse(item.getThumbnail()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<TopChartItem> itemList) {
        this.itemList = itemList;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView chartTitle, chartDes;
        private ImageView thumbnail;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            chartTitle = itemView.findViewById(R.id.chart_title);
            thumbnail = itemView.findViewById(R.id.thumbnail_top_chart);
            chartDes = itemView.findViewById(R.id.chart_des);
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
