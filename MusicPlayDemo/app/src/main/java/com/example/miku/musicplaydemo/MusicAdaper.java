package com.example.miku.musicplaydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by miku on 2016/7/19.
 */
public class MusicAdaper extends ArrayAdapter<Music> {
    private int resourceid;
    public MusicAdaper(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        resourceid = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music music = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_music);
        TextView name = (TextView) view.findViewById(R.id.musuic_name);
        TextView geshou = (TextView) view.findViewById(R.id.music_geshou);
        imageView.setImageDrawable(music.getBt());
        name.setText(music.getName());
        geshou.setText(music.getmArtist());
        return view;
    }
}
