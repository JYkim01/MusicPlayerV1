package com.musicplayerjykim.musicplayerapp.DataBase;

import android.provider.BaseColumns;

/**
 * Created by user on 2017-03-09.
 */

public final class MusicContract {
    public static final String SQL_CREATE_MUSIC_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s IMAGE, %s TEXT, %s TEXT);",
                    MusicEntry.TABLE_NAME,
                    MusicEntry._ID,
                    MusicEntry.COLUMN_NAME_IMAGE,
                    MusicEntry.COLUMN_NAME_TITLE,
                    MusicEntry.COLUMN_NAME_ARTIST);

    private MusicContract() {}

    public static class MusicEntry implements BaseColumns {
        public static final String TABLE_NAME = "music";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ARTIST = "artist";
    }
}
