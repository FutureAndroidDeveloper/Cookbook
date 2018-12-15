package com.example.kirill.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class CardGridAdapter extends BaseAdapter {
    private String[] names;
    private int[] imageResourceIds;
    private int layout;
    private LayoutInflater inflater;

    CardGridAdapter(Context context, String[] names, int[] imageResourceIds, int layout) {
        this.inflater = (LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        this.names = names;
        this.imageResourceIds = imageResourceIds;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_info);
        TextView textView = (TextView) convertView.findViewById(R.id.name_info);
        imageView.setImageResource(imageResourceIds[position]);
        textView.setText(names[position]);

        return convertView;
    }
}
