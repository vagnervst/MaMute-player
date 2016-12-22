package com.vsoftware.mamute.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Vagner on 22/12/2016.
 */

public class Player {
    private static MediaPlayer player = new MediaPlayer();
    private static Song current_song;

    private static void prepare(Context context) {
        try {

            if( player.isPlaying() ) {
                player.stop();
                player.reset();
            }

            player.setDataSource(context, current_song.getUri());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean changeState() {
        if( player.isPlaying() ) {
            player.pause();
            return false;
        } else {
            player.start();
            return true;
        }
    }

    public static void play(Context context) {
        prepare(context);
        player.start();
    }

    public static void setSong(Song song) {
        current_song = song;
    }

    public static Song getSong() {
        return current_song;
    }

    public static MediaPlayer getPlayer() {
        return player;
    }
}
