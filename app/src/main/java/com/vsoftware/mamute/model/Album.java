package com.vsoftware.mamute.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vagner on 31/10/2016.
 */
public class Album {
    private int id;
    private String title;
    private List<Song> songs;

    public Album(int id, String title) {
        this.setId(id);
        this.setTitle(title);
    }

    public static List<Album> findAll(Context context) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Albums.ALBUM.length() + " > 0";
        String order = MediaStore.Audio.Albums.ALBUM;

        String[] projection = {
                MediaStore.Audio.Albums._ID + ", " + MediaStore.Audio.Albums.ALBUM
        };

        Cursor cursor = cr.query(uri, projection, selection, null, order);

        List<Album> albumsFound = null;
        if( cursor != null && cursor.getCount() > 0 ) {
            albumsFound = new ArrayList<>();

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); ++i) {
                int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String album_title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));

                Album albumobj = new Album(album_id, album_title);

                albumsFound.add(albumobj);

                cursor.moveToNext();
            }

            cursor.close();
        }

        return albumsFound;
    }

    public static List<Album> findByArtistName(Context context, String artistName) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Albums.ARTIST + " = " + DatabaseUtils.sqlEscapeString(artistName);
        String order = MediaStore.Audio.Albums.ALBUM;

        String[] projection = {
                MediaStore.Audio.Albums._ID + ", " + MediaStore.Audio.Albums.ALBUM
        };

        Cursor cursor = cr.query(uri, projection, selection, null, order);

        List<Album> albumsFound = null;
        if( cursor != null && cursor.getCount() > 0 ) {
            albumsFound = new ArrayList<>();

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); ++i) {
                int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String album_title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));

                Album albumobj = new Album(album_id, album_title);

                albumsFound.add(albumobj);

                cursor.moveToNext();
            }

            cursor.close();
        }

        return albumsFound;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
