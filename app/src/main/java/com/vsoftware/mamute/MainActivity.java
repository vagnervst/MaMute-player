package com.vsoftware.mamute;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vsoftware.mamute.model.Album;
import com.vsoftware.mamute.model.AlbumAdapter;
import com.vsoftware.mamute.model.Artist;
import com.vsoftware.mamute.model.ArtistAdapter;
import com.vsoftware.mamute.model.Player;
import com.vsoftware.mamute.model.Song;
import com.vsoftware.mamute.model.SongAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int READ_STORAGE_REQUEST = 42;

    Context context;
    ListView lv_data;
    Button btn_artists, btn_albums, btn_songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        lv_data = (ListView) findViewById(R.id.lv_data);
        btn_artists = (Button) findViewById(R.id.btn_artists);
        btn_albums = (Button) findViewById(R.id.btn_albums);
        btn_songs = (Button) findViewById(R.id.btn_songs);

        askPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_player:
                Intent playerWin = new Intent(context, PlayerActivity.class);
                startActivity(playerWin);
                break;
        }

        return true;
    }

    public void selectArtists(View view) {
        //Fill the listview with all artists stored on the device
        List<Artist> artists = Artist.findAll(context);
        ArtistAdapter adapter = new ArtistAdapter(context, R.layout.list_view_item, artists);

        lv_data.setAdapter(adapter);
        lv_data.setOnItemClickListener( new OpenArtist() );
    }

    public void selectAlbums(View view) {
        //Fill the listview with all albums stored on the device

        List<Album> albums = Album.findAll(context);
        AlbumAdapter adapter = new AlbumAdapter(context, R.layout.list_view_item, albums);

        lv_data.setAdapter(adapter);
        lv_data.setOnItemClickListener( new OpenAlbum() );
    }

    public void selectSongs(View view) {
        //Fill the listview with all songs stored on the device

        List<Song> songs = Song.findAll(context);
        SongAdapter adapter = new SongAdapter(context, R.layout.list_view_item, songs);

        lv_data.setAdapter(adapter);
        lv_data.setOnItemClickListener( new OpenSong() );
    }

    public void askPermission() {
        int is_granted = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        if( is_granted != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    READ_STORAGE_REQUEST);
        }
    }

    private class OpenArtist implements AdapterView.OnItemClickListener {
        //ListView item click handler which opens artist's details activity

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent artistWin = new Intent(context, ArtistaActivity.class);
            Artist selectedArtist = (Artist) lv_data.getItemAtPosition(position);

            artistWin.putExtra("artistId", selectedArtist.getId());

            startActivity(artistWin);
        }
    }

    private class OpenAlbum implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent albumWin = new Intent(context, AlbumActivity.class);
            Album selectedAlbum = (Album) lv_data.getItemAtPosition(position);

            albumWin.putExtra("albumId", selectedAlbum.getId());

            startActivity(albumWin);
        }
    }

    private class OpenSong implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Song selectedSong = (Song) lv_data.getItemAtPosition(position);
            Player.setSong( selectedSong );
            Player.play(context);
        }
    }
}
