package com.musicplayerjykim.musicplayerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.musicplayerjykim.musicplayerapp.Model.MusicModel;
import com.musicplayerjykim.musicplayerapp.R;

import java.util.List;

/**
 * Created by user on 2017-03-09.
 */

public class PlaylistAdapter extends BaseAdapter {

    private Context mContext;
    private List<MusicModel> mMusicList;

    public PlaylistAdapter(Context contxet, List<MusicModel> musicList) {
        this.mContext = contxet;
        this.mMusicList = musicList;
    }

    @Override
    public int getCount() {
        return mMusicList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMusicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_playlist, parent, false);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.song_image);
            TextView songTitle = (TextView) convertView.findViewById(R.id.song_title_text);
            TextView songArtist = (TextView) convertView.findViewById(R.id.song_artist_text);

            viewHolder.songImage = imageView;
            viewHolder.songTitleText = songTitle;
            viewHolder.songArtistText = songArtist;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MusicModel musicModel = mMusicList.get(position);

        viewHolder.songImage.setImageResource(Integer.parseInt(musicModel.getmMusicImage()));
        viewHolder.songTitleText.setText(musicModel.getmSong());
        viewHolder.songArtistText.setText(musicModel.getmArtist());

        return convertView;
    }
    private static class ViewHolder {
        ImageView songImage;
        TextView songTitleText;
        TextView songArtistText;
    }
}
