package com.example.music_player_frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.music_player_frontend.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: navigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.menu_search).setChecked(true);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.menu_library).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        viewPagerAdapter.getItem(0);
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_search:
                        viewPagerAdapter.getItem(1);
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_library:
                        viewPagerAdapter.getItem(2);
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        viewPagerAdapter.getItem(3);
                        viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }

    public void setPage(int id) {
        viewPagerAdapter.getItem(id);
        viewPager.setCurrentItem(id);
    }

    public void setDetailPlaylist() {}
}