package com.example.miku.musicplaydemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Audio> list;
    ListView lv;
    List<Music> musicsList;
    BitmapDrawable bmpDraw; //图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicsList = new ArrayList<Music>();
        list = MediaUtils.getAudioList(this);  //Audio List
        lv = (ListView) findViewById(R.id.list_item);
        init();
        MusicAdaper musicAdaper = new MusicAdaper(MainActivity.this,R.layout.music_item,musicsList);
        lv.setAdapter(musicAdaper);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Audio audio = list.get(i);
                Toast.makeText(MainActivity.this, audio.getPath(), Toast.LENGTH_SHORT).show();
                //点击事件
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                //此处使用索引 i 可获取 javabean music 和 Audio内数据
                intent.putExtra("data_music",audio.getPath());
                intent.putExtra("Index", audio.getAlbumId());
                intent.putExtra("Duration",audio.getDuration());
                startActivity(intent);
            }
        });
    }
    private void init() {
        for (Audio a:
                list) {
            Music music = new Music();
            music.setName(a.getTitle());
            music.setmArtist(a.getArtist());
            music.setmPath(a.getPath());
//            title.add(a.getTitle());
//            a.getAlbumId(); //歌曲id
            String albumArt = MediaUtils.getAlbumArt(this,a.getAlbumId());
            Bitmap bm = null;
            if (albumArt == null) {
                //判断为空
                bm = BitmapFactory.decodeResource(this.getBaseContext().getResources(),R.mipmap.ic_pic);
                bmpDraw = new BitmapDrawable(bm);
                music.setBt(bmpDraw);
            } else {
                bm = BitmapFactory.decodeFile(albumArt);
                bmpDraw = new BitmapDrawable(bm);
                music.setBt(bmpDraw);
            }
            musicsList.add(music);
        }
    }
//        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,title));
}

