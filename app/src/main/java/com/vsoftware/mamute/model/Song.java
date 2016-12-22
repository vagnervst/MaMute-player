package com.vsoftware.mamute.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vagner on 31/10/2016.
 */
public class Song {
    private int id;
    private String title;
    private Uri uri;
    private int artistId;
    private int albumId;

    public Song(int id, String title, Uri uri, int artistId, int albumId) {
        this.setId(id);
        this.setTitle(title);
        this.setUri(uri);
        this.setArtistId(artistId);
        this.setAlbumId(albumId);
    }

    public static List<Song> findAll(Context context) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String order = MediaStore.Audio.Media.TITLE + " ASC";

        String[] projection = {
            MediaStore.Audio.Media._ID + ", " + MediaStore.Audio.Media.TITLE + ", "
                    + MediaStore.Audio.Media.ALBUM_ID + ", " + MediaStore.Audio.Media.ARTIST_ID
        };

        Cursor cursor = cr.query(uri, projection, selection, null, order);

        List<Song> songsFound = null;
        if( cursor != null && cursor.getCount() > 0 ) {
            songsFound = new ArrayList<>();

            cursor.moveToFirst();
            for( int i = 0; i < cursor.getCount(); ++i ) {
                int song_id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media._ID ) );
                String song_title = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TITLE ) );
                Uri song_uri = ContentUris.withAppendedId( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song_id);
                int song_artistid = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST_ID ) );
                int song_albumid = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ALBUM_ID ) );

                Song songobj = new Song(song_id, song_title, song_uri, song_artistid, song_albumid);
                songsFound.add(songobj);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return songsFound;
    }

    public static List<Song> findByArtistId(Context context, int artistId) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " +
                MediaStore.Audio.Media.ARTIST_ID + " = ?";

        String order = MediaStore.Audio.Media.TITLE + " ASC";

        String[] projection = {
                MediaStore.Audio.Media._ID + ", " + MediaStore.Audio.Media.TITLE + ", "
                        + MediaStore.Audio.Media.ALBUM_ID + ", " + MediaStore.Audio.Media.ARTIST_ID
        };

        Cursor cursor = cr.query(uri, projection, selection, new String[] { String.valueOf(artistId) }, order);

        List<Song> songsFound = null;
        if( cursor != null && cursor.getCount() > 0 ) {
            songsFound = new ArrayList<>();

            cursor.moveToFirst();
            for( int i = 0; i < cursor.getCount(); ++i ) {
                int song_id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media._ID ) );
                String song_title = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TITLE ) );
                Uri song_uri = ContentUris.withAppendedId( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song_id);
                int song_artistid = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST_ID ) );
                int song_albumid = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ALBUM_ID ) );

                Song songobj = new Song(song_id, song_title, song_uri, song_artistid, song_albumid);
                songsFound.add(songobj);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return songsFound;
    }

    public static List<Song> findByAlbumId(Context context, int albumId) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.ALBUM_ID + " = ?";
        String order = MediaStore.Audio.Media.TITLE;

        String[] projection = {
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST_ID, MediaStore.Audio.Media.ALBUM_ID
        };

        Cursor cursor = cr.query(uri,
                projection,
                selection,
                new String[] { String.valueOf(albumId) },
                order
        );

        List<Song> songs = new ArrayList<>();
        if( cursor != null && cursor.getCount() > 0 ) {
            cursor.moveToFirst();

            for( int i = 0; i < cursor.getCount(); ++i ) {
                int song_id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media._ID ) );
                String song_title = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TITLE ) );
                Uri song_uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song_id);
                int artist_id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST_ID ) );
                int album_Id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ALBUM_ID ) );

                Song s = new Song(song_id, song_title, song_uri, artist_id, album_Id);

                songs.add(s);
                cursor.moveToNext();
            }

            cursor.close();
        }

        return songs;
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

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
