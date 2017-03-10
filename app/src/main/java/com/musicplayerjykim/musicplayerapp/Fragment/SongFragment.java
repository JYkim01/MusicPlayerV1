package com.musicplayerjykim.musicplayerapp.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.musicplayerjykim.musicplayerapp.R;

/**
 * Created by user on 2017-03-09.
 */

public class SongFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    public SongFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.playlist);

        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );

        MusicCursorAdapter adapter = new MusicCursorAdapter (getActivity(), cursor);
        mListView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

        public interface OnFragmentInteractionListener {
            void onFragmentInteraction(Uri uri);
        }

    private static class MusicCursorAdapter extends CursorAdapter {

        public MusicCursorAdapter(Context context, Cursor c) {
            super(context, c, false);
        }
        @Override
        public View newView(Context context, final Cursor cursor, ViewGroup parent) {
            final ViewHolder viewHolder = new ViewHolder();
            View convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_playlist, parent, false);

            viewHolder.albumArtist = (TextView)convertView.findViewById(R.id.song_artist_text);
            viewHolder.albumTitle = (TextView)convertView.findViewById(R.id.song_title_text);
//            viewHolder.albumImage = (ImageView)convertView.findViewById(R.id.song_image);

            convertView.setTag(viewHolder);

            return convertView;
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            final ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.albumArtist.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
            viewHolder.albumTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
//            viewHolder.albumImage.setImageResource(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//            Glide.with(context).load(path).into(viewHolder.albumImage);
        }

        private class ViewHolder {
//            ImageView albumImage;
            TextView albumArtist;
            TextView albumTitle;
        }
    }
}