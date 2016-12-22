package com.vsoftware.mamute;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsoftware.mamute.model.Album;
import com.vsoftware.mamute.model.Artist;
import com.vsoftware.mamute.model.Player;

import java.io.File;

public class PlayerActivity extends AppCompatActivity {

    Context context;
    TextView artist_name, song_title;
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
        cover = (ImageView) findViewById(R.id.img_cover);
        player_control = (ImageButton) findViewById(R.id.btn_player_control);

        player_control.setOnClickListener( new ChangePlayerState() );

        if( Player.getPlayer().isPlaying() ) {
            artist_name.setText( Artist.findById( context, Player.getSong().getArtistId() ).getName() );
            song_title.setText( Player.getSong().getTitle() );

            cover.setImageURI( Album.findById( context, Player.getSong().getAlbumId() ).getThumb() );

            updateButtonIcon();
        }
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
