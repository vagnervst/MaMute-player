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
 * Created by Vagner on 31/10/2016.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {
    private int resource;

    public ArtistAdapter(Context context, int resource, List<Artist> objects) {
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

        Artist artist = getItem(position);

        TextView info_label = (TextView) v.findViewById(R.id.info_label);
        info_label.setText( artist.getName() );

        return v;
    }
}
