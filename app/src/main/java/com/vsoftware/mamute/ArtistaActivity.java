package com.vsoftware.mamute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.vsoftware.mamute.model.Album;
import com.vsoftware.mamute.model.AlbumAdapter;
import com.vsoftware.mamute.model.Artist;
import com.vsoftware.mamute.model.Song;
import com.vsoftware.mamute.model.SongAdapter;

import java.util.List;

public class ArtistaActivity extends AppCompatActivity {

    Context context;
    ListView lv_artistdata;

    Artist selectedArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        lv_artistdata = (ListView) findViewById(R.id.lv_artistdata);

        Intent sharedData = getIntent();
        int artistId = sharedData.getIntExtra("artistId", 0);

        selectedArtist = Artist.findById(context, artistId);
    }

    public void selectSongs(View view) {
        List<Song> songs = Song.findByArtistId(context, selectedArtist.getId());

        SongAdapter adapter = new SongAdapter(context, R.layout.list_view_item, songs);
        lv_artistdata.setAdapter(adapter);
    }

    public void selectAlbums(View view) {
        List<Album> albums = Album.findByArtistName(context, selectedArtist.getName());

        if( albums != null ) {
            AlbumAdapter adapter = new AlbumAdapter(context, R.layout.list_view_item, albums);
            lv_artistdata.setAdapter(adapter);
        }
    }
}
