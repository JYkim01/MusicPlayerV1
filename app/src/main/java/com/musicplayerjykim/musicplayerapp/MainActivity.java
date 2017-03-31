package com.musicplayerjykim.musicplayerapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.musicplayerjykim.musicplayerapp.Fragment.ListFragment;
import com.musicplayerjykim.musicplayerapp.Fragment.SongFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SongFragment mSongFragment;
    private ListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tap_id);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_id);

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
//                case 1:
//                    return mListViewFragment;
//                case 2:
//                    return mSongFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "플레이어";
//                case 1:
//                    return "아티스트";
//                case 2:
//                    return "노래";
            }
            return null;
        }
    }
}
