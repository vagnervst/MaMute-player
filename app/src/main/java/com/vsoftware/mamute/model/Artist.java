package com.vsoftware.mamute.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vagner on 31/10/2016.
 */
public class Artist {
    private int id;
    private String name;
    private List<Album> albums;

    public Artist() {

    }

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Artist> findAll(Context context) {
        //find all artists for audio files located at at the external storage
        //and create a list

        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; //get data from external storage
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"; //search for audio files only
        String order = MediaStore.Audio.Media.ARTIST; //order by artist name

        String[] projection = { //capture artist id and artist name, not repeating them
                "DISTINCT " + MediaStore.Audio.Media.ARTIST_ID + ", " + MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = cr.query(uri, projection, selection, null, order);

        List<Artist> foundArtists = null;
        if( cursor != null && cursor.getCount() > 0 ) { //One or more artists found
            cursor.moveToFirst();

            foundArtists = new ArrayList<>();
            for( int i = 0; i < cursor.getCount(); ++i ) {
                int artist_id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST_ID ) );
                String artist_name = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST ) );

                Artist artist = new Artist(artist_id, artist_name);

                foundArtists.add(artist);

                cursor.moveToNext();
            }
        }

        return foundArtists;
    }

    public static Artist findById(Context context, int id) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; //get data from external storage
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"; //search for audio files only

        String[] projection = {
                "DISTINCT " + MediaStore.Audio.Media.ARTIST_ID + " as _id, " + MediaStore.Audio.Media.ARTIST
        };

        String where[] = {
                "_id = " + String.valueOf(id)
        }; //where clause bound to id

        Cursor cursor = cr.query(uri, projection, selection, where, null);

        Artist artistFound = null;
        if( cursor != null && cursor.getCount() == 1 ) { //the artist was found
            cursor.moveToFirst();

            int artist_id = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST_ID ) );
            String artist_name = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST ) );

            artistFound = new Artist(artist_id, artist_name);
        }

        return artistFound;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
