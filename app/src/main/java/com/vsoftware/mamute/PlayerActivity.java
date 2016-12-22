package com.vsoftware.mamute;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vsoftware.mamute.model.Album;
import com.vsoftware.mamute.model.Artist;
import com.vsoftware.mamute.model.Player;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static com.vsoftware.mamute.model.Player.getSong;

public class PlayerActivity extends AppCompatActivity {

    Context context;
    TextView artist_name, song_title;
    SeekBar progress;
    ImageButton player_control;
    ImageView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        artist_name = (TextView) findViewById(R.id.txt_artist_name);
        song_title = (TextView) findViewById(R.id.txt_song_title);
        progress = (SeekBar) findViewById(R.id.sk_progress);
        cover = (ImageView) findViewById(R.id.img_cover);
        player_control = (ImageButton) findViewById(R.id.btn_player_control);

        player_control.setOnClickListener( new ChangePlayerState() );

        if( Player.getPlayer().isPlaying() ) {
            artist_name.setText( Artist.findById( context, getSong().getArtistId() ).getName() );
            song_title.setText( getSong().getTitle() );

            cover.setImageURI( Album.findById( context, getSong().getAlbumId() ).getThumb() );

            updateButtonIcon();
        }

        Player.getPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                updateButtonIcon();
            }
        });

        progress.setMax(100);

        Timer updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                double song_progress = (double) Player.getPlayer().getCurrentPosition() / Player.getPlayer().getDuration();
                progress.setProgress((int) (song_progress*100));
            }
        }, 0, 1000);

        long time = Player.getPlayer().getTimestamp().getAnchorMediaTimeUs();
    }

    public void updateButtonIcon() {
        if( Player.getPlayer().isPlaying() ) {
            player_control.setImageResource( R.drawable.ic_pause_outline );
        } else {
            player_control.setImageResource( R.drawable.ic_play_outline );
        }
    }

    private class ChangePlayerState implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Player.changeState();
            updateButtonIcon();
        }
    }
}
