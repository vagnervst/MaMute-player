package com.vsoftware.mamute;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class AlbumActivity extends AppCompatActivity {

    Context context;
    ListView lv_songs;

    int albumId;

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

        }
    }

}
