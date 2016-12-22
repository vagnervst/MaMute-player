package com.vsoftware.mamute.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Vagner on 22/12/2016.
 */

public class Player {
    public static MediaPlayer player = new MediaPlayer();

    private static void prepare(Context context, Uri song_uri) {
        try {

            if( player.isPlaying() ) {
                player.stop();
                player.reset();
            }

            player.setDataSource(context, song_uri);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void play(Context context, Uri song_uri) {
        prepare(context, song_uri);
        player.start();
    }
}
