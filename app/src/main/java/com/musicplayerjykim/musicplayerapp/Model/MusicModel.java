package com.musicplayerjykim.musicplayerapp.Model;

/**
 * Created by user on 2017-03-09.
 */

public class MusicModel {
    private String mSong;
    private String mArtist;
    private String mMusicImage;
    private long id;

    public MusicModel(String mSong, String mArtist, String mMusicImage) {
        this.mSong = mSong;
        this.mArtist = mArtist;
        this.mMusicImage = mMusicImage;
    }

    public String getmSong() {
        return mSong;
    }

    public void setmSong(String mSong) {
        this.mSong = mSong;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getmMusicImage() {
        return mMusicImage;
    }

    public void setmMusicImage(String mMusicImage) {
        this.mMusicImage = mMusicImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "mSong='" + mSong + '\'' +
                ", mArtist='" + mArtist + '\'' +
                ", mMusicImage='" + mMusicImage + '\'' +
                '}';
    }
}
