package com.example.miku.musicplayerlistview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private Button play,pause,stop;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private BitmapDrawable bmpDraw; //图片
    private ImageView imageView;
    private int Duration; //时长
    private ProgressBar progressBar;
    private int time = 0;
    private int i;
    private String data;
    private int pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        data = intent.getStringExtra("data_music");
        //传递图片id 使用 MusicAdper 内public 方法获取 bitMap
        pic = intent.getIntExtra("Index",-1);
        Duration = intent.getIntExtra("Duration",0);
        i = intent.getIntExtra("id",-1);
        Duration = Duration/1000; //歌曲时长 秒
        init(pic);
        //使用广播监控播放完成 销毁次activity 再次Intent
        // 进度条 传递歌曲长度
        //如何使用进度条
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        initMediaPlayer(data); //初始化MediaPlayer
    }
    private void init(int AlbumId) {
        progressBar = (ProgressBar) findViewById(R.id.parentPanel);
        imageView = (ImageView) findViewById(R.id.image_xiangq);
        String albumArt = MediaUtils.getAlbumArt(this,AlbumId);
        Bitmap bm = null;
        if (albumArt == null) {
            //判断为空
            bm = BitmapFactory.decodeResource(this.getBaseContext().getResources(),R.mipmap.ic_pic);
            bmpDraw = new BitmapDrawable(bm);
            imageView.setImageDrawable(bmpDraw);
        } else {
            bm = BitmapFactory.decodeFile(albumArt);
            bmpDraw = new BitmapDrawable(bm);
            imageView.setImageDrawable(bmpDraw);
        }
    }
    private void next() {
            Toast.makeText(Main2Activity.this, "Play end", Toast.LENGTH_SHORT).show();
            List<Audio> list = MediaUtils.getAudioList(Main2Activity.this);
            Audio audio= list.get(i+1);
            data = audio.getPath();
            pic = audio.getAlbumId();
            Duration = audio.getDuration();
            Duration = Duration/1000;
            init(pic);
            initMediaPlayer(data);
            mediaPlayer.start();
    }
    private void initMediaPlayer(String files) {
        try {
            mediaPlayer.reset();
            File file = new File(files);
            mediaPlayer.setDataSource(file.getPath()); //制定音频文件
            mediaPlayer.prepare(); //进入准备状态
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                    Log.d("Music","end");
                    next();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    progressBar.setMax(Duration);
                    new Thread(new Runnable() {
                        public void run() {
                            while (time/1000 <= Duration) {
                                time = mediaPlayer.getCurrentPosition();
                                progressBar.setProgress(time/1000);
                            }
                        }
                    }).start();
                    mediaPlayer.start(); //开始播放
                }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); //暂停播放
                }
                break;
            case R.id.stop:
                if (mediaPlayer.isPlaying()) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        onDestroy();
                    }
                }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
