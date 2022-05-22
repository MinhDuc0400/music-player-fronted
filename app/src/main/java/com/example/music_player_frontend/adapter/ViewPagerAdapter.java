package com.example.music_player_frontend.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.music_player_frontend.fragments.HomeFragment;
import com.example.music_player_frontend.fragments.LikedSongFragment;
import com.example.music_player_frontend.fragments.SearchFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new SearchFragment();
            case 2: return new LikedSongFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
