package com.vsoftware.mamute.model;

import java.util.List;

/**
 * Created by Vagner on 31/10/2016.
 */
public class Album {
    private int id;
    private String title;
    private List<Song> songs;

    public static List<Album> findAll() {
        return null;
    }

    public static List<Album> findByArtistId(int id) {
        return null;
    }
}
