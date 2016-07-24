package com.example.miku.musicplaydemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by miku on 2016/7/19.
 */
public class Music {
    private BitmapDrawable bt;
    private String name;
    private String mArtist;
    private String mPath;

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public BitmapDrawable getBt() {
        return bt;
    }

    public void setBt(BitmapDrawable bt) {
        this.bt = bt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }
}
