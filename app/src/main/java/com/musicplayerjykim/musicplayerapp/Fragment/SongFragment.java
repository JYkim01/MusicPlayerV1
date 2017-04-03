package com.musicplayerjykim.musicplayerapp.Fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.musicplayerjykim.musicplayerapp.Adapter.CursorRecyclerViewAdapter;
import com.musicplayerjykim.musicplayerapp.R;
import com.musicplayerjykim.musicplayerapp.Services.MusicService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by user on 2017-03-09.
 */

public class SongFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        Cursor cursor = getActivity().getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

        recyclerView.setAdapter(new SongRecyclerAdapter(getActivity(), cursor));
    }

    public static class SongRecyclerAdapter extends CursorRecyclerViewAdapter<ViewHolder> {

        private Context mContext;

        public SongRecyclerAdapter(Context context, Cursor cursor) {
            super(context, cursor);
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
            // content://audio/media/1"
            final Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursor.getLong(
                    cursor.getColumnIndexOrThrow(BaseColumns._ID)));

            final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(mContext, uri);

            // 미디어 정보
            String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            // 오디오 앨범 자켓 이미지
            byte albumImage[] = retriever.getEmbeddedPicture();
            if (null != albumImage) {
                Glide.with(mContext).load(albumImage).into(viewHolder.mAlbumImage);
            } else {
                Glide.with(mContext).load(R.mipmap.ic_launcher).into(viewHolder.mAlbumImage);
            }

            viewHolder.mTitle.setText(title);
            viewHolder.mArtist.setText(artist);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * 음악 틀기
                     * {@link com.musicplayerjykim.musicplayerapp.services.MusicService#playMusic(Uri)}
                     */
                    Intent intent = new Intent(mContext, MusicService.class);
                    intent.setAction(MusicService.ACTION_PLAY);
                    intent.putExtra("uri", uri);
                    mContext.startService(intent);

                    /**
                     * 아래쪽 프래그먼트로 정보 쏘기
                     * * {@link MusicControllerFragment#updateUI(MediaMetadataRetriever)}
                     */
                    EventBus.getDefault().post(retriever);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mAlbumImage;
        TextView mTitle;
        TextView mArtist;

        public ViewHolder(View itemView) {
            super(itemView);

            mAlbumImage = (ImageView) itemView.findViewById(R.id.album_image_item);
            mTitle = (TextView) itemView.findViewById(R.id.album_title_item);
            mArtist = (TextView) itemView.findViewById(R.id.album_artist_item);
        }
    }
}