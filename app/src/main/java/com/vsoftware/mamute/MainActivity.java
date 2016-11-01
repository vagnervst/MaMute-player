package com.vsoftware.mamute;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.vsoftware.mamute.model.Artist;
import com.vsoftware.mamute.model.ArtistAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int READ_STORAGE_REQUEST = 42;

    Context context;
    ListView lv_data;
    Button btn_artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        lv_data = (ListView) findViewById(R.id.lv_data);
        btn_artists = (Button) findViewById(R.id.btn_artists);

        askPermission();
    }

    public void selectArtists(View view) {
        List<Artist> artists = Artist.findAll(context);
        ArtistAdapter adapter = new ArtistAdapter(context, R.layout.list_view_item, artists);

        lv_data.setAdapter(adapter);
    }

    public void askPermission() {
        int is_granted = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        if( is_granted != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    READ_STORAGE_REQUEST);
        }
    }
}
