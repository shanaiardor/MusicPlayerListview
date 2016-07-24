package com.example.miku.musicplayerlistview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Audio> list;
    private ListView lv;

    public Audio getList(int i) {
        return list.get(i);
    }

    private BitmapDrawable bmpDraw; //图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = MediaUtils.getAudioList(this);  //get Audio List
        lv = (ListView) findViewById(R.id.list_item);//get ListVie object
        lv.setAdapter(new Myadapter()); //new adapter
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Audio audio = list.get(i);
                Toast.makeText(MainActivity.this, audio.getPath(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("data_music",audio.getPath());
                intent.putExtra("Index", audio.getAlbumId());
                intent.putExtra("Duration",audio.getDuration());
                intent.putExtra("id",i);
                startActivity(intent);
            }
        });
    }
    class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            Audio audio = list.get(i);
            View v = null;
            String albumArt = MediaUtils.getAlbumArt(MainActivity.this,audio.getAlbumId());
            Bitmap bm = null;
            if (albumArt == null) {
                bm = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_pic);
                bmpDraw = new BitmapDrawable(bm);
            } else {
                bm = BitmapFactory.decodeFile(albumArt);
                bmpDraw = new BitmapDrawable(bm);
            }
            if(convertView == null){
                v = View.inflate(MainActivity.this, R.layout.music_item, null);
            }
            else{
                v = convertView;
            }
            ImageView imageView = (ImageView) v.findViewById(R.id.image_music);
            TextView name = (TextView) v.findViewById(R.id.musuic_name);
            TextView geshou = (TextView) v.findViewById(R.id.music_geshou);
            imageView.setImageDrawable(bmpDraw);
            name.setText(audio.getTitle());
            geshou.setText(audio.getArtist());
            return v;
        }
    }
}

