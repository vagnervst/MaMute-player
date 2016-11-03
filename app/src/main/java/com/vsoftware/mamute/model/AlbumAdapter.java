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
 * Created by Vagner on 02/11/2016.
 */
public class AlbumAdapter extends ArrayAdapter<Album> {
    private int resource;

    public AlbumAdapter(Context context, int resource, List<Album> objects) {
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

        Album album = getItem(position);

        TextView label = (TextView) v.findViewById(R.id.info_label);
        label.setText( album.getTitle() );

        return v;
    }
}
