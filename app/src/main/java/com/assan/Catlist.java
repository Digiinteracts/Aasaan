package com.assan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Digi-T1 on 4/12/2016.
 */
public class Catlist extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private Integer[] imageid;
    private Activity context;

    public Catlist(Activity context, String[] names, String[] desc, Integer[] imageid) {
        super(context, R.layout.list_layout, names);
        this.context = context;
        this.names = names;
        this.desc = desc;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.categroie_list_item, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(names[position]);
        image.setImageResource(imageid[position]);
        return  listViewItem;
    }
}