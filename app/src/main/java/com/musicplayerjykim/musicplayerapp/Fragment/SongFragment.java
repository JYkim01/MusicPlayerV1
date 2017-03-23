package com.musicplayerjykim.musicplayerapp.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.musicplayerjykim.musicplayerapp.R;

/**
 * Created by user on 2017-03-09.
 */

public class SongFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    String imageFile;

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

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);
            }
        } else {


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Cursor cursor = getActivity().getContentResolver().query(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            null,
                            null,
                            null,
                            null
                    );

                    MusicCursorAdapter adapter = new MusicCursorAdapter (getActivity(), cursor);
                    mListView.setAdapter(adapter);

                } else {

                    getActivity().finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


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

    private class MusicCursorAdapter extends CursorAdapter {

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
            viewHolder.albumImage = (ImageView)convertView.findViewById(R.id.song_image);

            convertView.setTag(viewHolder);

            return convertView;
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            final ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.albumArtist.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
            viewHolder.albumTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
            Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
            Glide.with(SongFragment.this).load(getRealPathFromURI(uri)).into(viewHolder.albumImage);
        }

        private String getRealPathFromURI(Uri contentURI) {
            String result;
            Cursor cursor = getContext().getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                result = cursor.getString(idx);
                cursor.close();
            }
            return result;
        }

        private class ViewHolder {
            ImageView albumImage;
            TextView albumArtist;
            TextView albumTitle;
        }
    }
}