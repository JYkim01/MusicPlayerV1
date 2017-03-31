package com.musicplayerjykim.musicplayerapp.Services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.musicplayerjykim.musicplayerapp.MainActivity;
import com.musicplayerjykim.musicplayerapp.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by user on 2017-03-31.
 */

public class MusicService extends Service {

    public static String ACTION_PLAY = "play";
    public static String ACTION_RESUME = "resume";

    private MediaPlayer mMediaPlayer;
    private MediaMetadataRetriever mRetriever;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (ACTION_PLAY.equals(action)) {
            playMusic((Uri) intent.getParcelableExtra("uri"));
        } else if (ACTION_RESUME.equals(action)) {
            clickResumeButton();
        }
        return START_STICKY;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void playMusic(Uri uri) {
        try {
            mRetriever = new MediaMetadataRetriever();
            mRetriever.setDataSource(this, uri);

            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mMediaPlayer.setDataSource(this, uri);

            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    EventBus.getDefault().post(isPlaying());

                }
            });
            showNotification();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showNotification() {
        String title = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artist = mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.remote_view);
        remoteView.setTextViewText(R.id.title_text, title);
        remoteView.setTextViewText(R.id.artist_text, artist);

        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_launcher);

        remoteView.setImageViewBitmap(R.id.image_view, bitmap);

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setColor(Color.YELLOW);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setVibrate(new long[]{100, 200, 300});

        Intent stopIntent = new Intent(this, MusicService.class);
        stopIntent.setAction(ACTION_RESUME);

        startForeground(1, builder.build());
    }

    public MediaMetadataRetriever getMetaDataRetriever() {
        return mRetriever;
    }

    private void clickResumeButton() {
        if (isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }

        EventBus.getDefault().post(isPlaying());
    }

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MusicService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MusicService.this;
        }
    }
}
