package com.musicplayerjykim.musicplayerapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.musicplayerjykim.musicplayerapp.Fragment.ListFragment;
import com.musicplayerjykim.musicplayerapp.Fragment.PlayerFragment;
import com.musicplayerjykim.musicplayerapp.Fragment.SongFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SongFragment mSongFragment;
    private ListFragment mListFragment;
    private PlayerFragment mPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tap_id);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        // 플레이어
        mPlayerFragment = new PlayerFragment();

        List<String> artistList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            artistList.add("가수 " + i);
        }
        mListFragment = ListFragment.newInstance(artistList);

        mSongFragment = new SongFragment();

        MusicPlayerPagerAdapter adapter = new MusicPlayerPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private class MusicPlayerPagerAdapter extends FragmentPagerAdapter {

        public MusicPlayerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mSongFragment;
                case 1:
                    return mListFragment;
                case 2:
                    return mPlayerFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "노래";
                case 1:
                    return "아티스트";
                case 2:
                    return "플레이어";
            }
            return null;
        }
    }
}
