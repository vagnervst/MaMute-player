package com.vsoftware.mamute.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vsoftware.mamute.R;

import java.util.List;

/**
 * Created by Vagner on 03/11/2016.
 */
public class SongAdapter extends ArrayAdapter<Song> {
    private int resource;

    public SongAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if( v == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(this.resource, null);
        }

        Song song = getItem(position);

        TextView label = (TextView) v.findViewById(R.id.info_label);
        label.setText( song.getTitle() );

        return v;
    }
}
