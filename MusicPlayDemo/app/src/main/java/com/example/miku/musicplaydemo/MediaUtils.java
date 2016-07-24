package com.example.miku.musicplaydemo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miku on 2016/7/19.
 */
public class MediaUtils {
    public static final String[] AUDIO_KEYS = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.TITLE_KEY,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST_KEY,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,//歌曲id 用来获取图片
            MediaStore.Audio.Media.ALBUM_KEY,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.IS_RINGTONE,
            MediaStore.Audio.Media.IS_PODCAST,
            MediaStore.Audio.Media.IS_ALARM,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.IS_NOTIFICATION,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.DATA
    };
    public static String getAlbumArt(Context context,int album_id) {//根据id获得图片
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        ContentResolver resolver = context.getContentResolver();
        Cursor cur = resolver.query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),// 拼接uri
                projection, null, null, null);
        String album_art = null;
        //返回列的总数
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }

    public static List<Audio> getAudioList(Context context) {//获取所有Audio储存在List
        List<Audio> audioList = new ArrayList<Audio>();

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                AUDIO_KEYS,
                null,
                null,
                null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Bundle bundle = new Bundle ();
            for (int i = 0; i < AUDIO_KEYS.length; i++) {
                final String key = AUDIO_KEYS[i];
                final int columnIndex = cursor.getColumnIndex(key);
                final int type = cursor.getType(columnIndex);
                switch (type) {
                    case Cursor.FIELD_TYPE_BLOB:
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        float floatValue = cursor.getFloat(columnIndex);
                        bundle.putFloat(key, floatValue);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        int intValue = cursor.getInt(columnIndex);
                        bundle.putInt(key, intValue);
                        break;
                    case Cursor.FIELD_TYPE_NULL:
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        String strValue = cursor.getString(columnIndex);
                        bundle.putString(key, strValue);
                        break;

                }
            }
            Audio audio = new Audio (bundle);
            audioList.add(audio);
        }
        cursor.close();
        return audioList;
    }
}
