package com.vsoftware.mamute.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vagner on 31/10/2016.
 */
public class Album {
    private int id;
    private String title;
    private List<Song> songs;
    private Uri thumb;

    public Album(int id, String title, Uri thumb) {
        this.setId(id);
        this.setTitle(title);
        this.thumb = thumb;
    }

    public static List<Album> findAll(Context context) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Albums.ALBUM.length() + " > 0";
        String order = MediaStore.Audio.Albums.ALBUM;

        String[] projection = {
                MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART
        };

        Cursor cursor = cr.query(uri, projection, selection, null, order);

        List<Album> albumsFound = null;
        if( cursor != null && cursor.getCount() > 0 ) {
            albumsFound = new ArrayList<>();

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); ++i) {
                int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String album_title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String album_art = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Albums.ALBUM_ART ) );

                Uri art_uri = null;
                if( album_art != null ) {
                    art_uri = Uri.fromFile(new File(album_art));
                }

                Album albumobj = new Album(album_id, album_title, art_uri);

                albumsFound.add(albumobj);

                cursor.moveToNext();
            }

            cursor.close();
        }

        return albumsFound;
    }

    public static Album findById(Context context, int albumId) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Albums.ALBUM.length() + " > 0 AND " + MediaStore.Audio.Albums._ID + " = ?";
        String order = MediaStore.Audio.Albums.ALBUM;

        String[] projection = {
                MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART
        };

        Cursor cursor = cr.query(uri,
                projection,
                selection,
                new String[] { String.valueOf(albumId) },
                order
        );

        Album album = null;
        if( cursor != null && cursor.getCount() > 0 ) {
            cursor.moveToFirst();

            int album_id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Albums._ID ) );
            String album_title = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Albums.ALBUM ) );
            String album_art = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Albums.ALBUM_ART ) );

            Uri art_uri = null;
            if( album_art != null ) {
                art_uri = Uri.fromFile(new File(album_art));
            }

            album = new Album(album_id, album_title, art_uri);
            album.setSongs( Song.findByAlbumId(context, album_id) );

            cursor.close();
        }

        return album;
    }

    public static List<Album> findByArtistName(Context context, String artistName) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Albums.ARTIST + " = " + DatabaseUtils.sqlEscapeString(artistName);
        String order = MediaStore.Audio.Albums.ALBUM;

        String[] projection = {
                MediaStore.Audio.Albums._ID + ", " + MediaStore.Audio.Albums.ALBUM + ", " + MediaStore.Audio.Albums.ALBUM_ART
        };

        Cursor cursor = cr.query(uri, projection, selection, null, order);

        List<Album> albumsFound = null;
        if( cursor != null && cursor.getCount() > 0 ) {
            albumsFound = new ArrayList<>();

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); ++i) {
                int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String album_title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String art_path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

                Uri album_art = null;
                if( art_path != null ) {
                    album_art = Uri.fromFile(new File(art_path));
                }
                Album albumobj = new Album(album_id, album_title, album_art);

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

    public Uri getThumb() {
        return thumb;
    }

    public void setThumb(Uri thumb) {
        this.thumb = thumb;
    }
}
