package com.vsoftware.mamute;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.vsoftware.mamute.model.Album;
import com.vsoftware.mamute.model.SongAdapter;

public class AlbumActivity extends AppCompatActivity {

    Context context;
    ListView lv_songs;

    int albumId;
    Album selectedAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        lv_songs = (ListView) findViewById(R.id.lv_album_songs);

        albumId = getIntent().getIntExtra("albumId", 0);

        if( albumId != 0 ) {
            selectedAlbum = Album.findById(context, albumId);
            setTitle( selectedAlbum.getTitle() );

            SongAdapter adapter = new SongAdapter(context, R.layout.list_view_item, selectedAlbum.getSongs());
            lv_songs.setAdapter(adapter);
        }
    }

}
