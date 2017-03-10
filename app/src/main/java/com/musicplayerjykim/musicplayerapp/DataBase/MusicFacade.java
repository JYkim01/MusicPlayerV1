package com.musicplayerjykim.musicplayerapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.musicplayerjykim.musicplayerapp.Model.MusicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-03-09.
 */

public class MusicFacade {

    private MusicDbHelper mDbHelper;

    public MusicFacade(Context context) {
        mDbHelper = new MusicDbHelper(context);
    }

    public long insert(String image, String title, String artist) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MusicContract.MusicEntry.COLUMN_NAME_IMAGE, image);
        values.put(MusicContract.MusicEntry.COLUMN_NAME_TITLE, title);
        values.put(MusicContract.MusicEntry.COLUMN_NAME_ARTIST, artist);

        long newRowId = db.insert(MusicContract.MusicEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public List<MusicModel> getMusicArrayList(String selection,
                                              String[] selectionArgs,
                                              String groupBy,
                                              String having,
                                              String orderBy) {
        ArrayList<MusicModel> musicArrayList = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String order = MusicContract.MusicEntry._ID + " DESC";

        Cursor c = db.query(
                MusicContract.MusicEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy == null ? order : orderBy
        );

        if (c != null) {

            while (c.moveToNext()) {
                String image = c.getString(
                        c.getColumnIndexOrThrow(
                                MusicContract.MusicEntry.COLUMN_NAME_IMAGE));
                String title = c.getString(
                        c.getColumnIndexOrThrow(
                                MusicContract.MusicEntry.COLUMN_NAME_TITLE));
                String artist = c.getString(
                        c.getColumnIndexOrThrow(
                                MusicContract.MusicEntry.COLUMN_NAME_ARTIST));
                long id = c.getLong(
                        c.getColumnIndexOrThrow(
                                MusicContract.MusicEntry._ID
                        ));
                MusicModel musicModel = new MusicModel(image, title, artist);
                musicModel.setId(id);
                musicArrayList.add(musicModel);
            }
            c.close();
        }
        return musicArrayList;
    }

    public List<MusicModel> get() {
        return getMusicArrayList(null, null, null, null, null);
    }

    public int delete(long id) {
        String selection = MusicContract.MusicEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int deleted = db.delete(MusicContract.MusicEntry.TABLE_NAME,
                selection,
                selectionArgs);

        return deleted;
    }

    public int update(long id, String image, String title, String artist) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(MusicContract.MusicEntry.COLUMN_NAME_IMAGE, image);
        values.put(MusicContract.MusicEntry.COLUMN_NAME_TITLE, title);
        values.put(MusicContract.MusicEntry.COLUMN_NAME_ARTIST, artist);

        int count = db.update(
                MusicContract.MusicEntry.TABLE_NAME,
                values,
                MusicContract.MusicEntry._ID + "=" + id,
                null);

        return count;
    }
}
