//package com.example.miku.musicplaydemo;
//
//import android.content.ContentResolver;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.provider.MediaStore;
//
///**
// * Created by miku on 2016/7/19.
// */
//public class Pic {
//    private Cursor getCursor(String filePath) {
//        String path = null;
//
//        Cursor c = getContentResolver().query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
//                MediaStore.Audio.Media.DEFAULT_SORT_ORDER); //ContentProvider内容提供器查询信息uri
//        // System.out.println(c.getString(c.getColumnIndex("_data")));
//        if (c.moveToFirst()) {
//            do {
//                // 通过Cursor 获取路径，如果路径相同则break；
//                System.out.println("////////"+filePath);
//                path = c.getString(c
//                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//                //getColumnIndexOrThrow如果没有找到该列名,会抛出IllegalArgumentException异常
//                //歌曲文件的全路径  MediaStore.Audio.Media.DATA
//                System.out.println("?????????"+path);
//                // 查找到相同的路径则返回，此时cursorPosition 便是指向路径所指向的Cursor 便可以返回了
//                if (path.equals(filePath)) {
//                    // System.out.println("audioPath = " + path);
//                    // System.out.println("filePath = " + filePath);
//                    // cursorPosition = c.getPosition();
//                    break;
//                }
//            } while (c.moveToNext());
//        }
//        // 这两个没有什么作用，调试的时候用
//        // String audioPath = c.getString(c
//        // .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//        //
//        // System.out.println("audioPath = " + audioPath);
//        return c; //指向路径所指向的Cursor
//    }
//
//    private String getAlbumArt(int album_id) {
//        String mUriAlbums = "content://media/external/audio/albums";
//        String[] projection = new String[] { "album_art" };
//        Cursor cur = this.getContentResolver().query(
//                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),// 拼接uri
//                projection, null, null, null);
//        String album_art = null;
//        //返回列的总数
//        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
//            cur.moveToNext();
//            album_art = cur.getString(0);
//        }
//        cur.close();
//        cur = null;
//        return album_art;
//    }
//    private void getImage(){
//        Cursor currentCursor = getCursor("/mnt/sdcard/"+mp3Info); //传入路径
//        int album_id = currentCursor.getInt(currentCursor
//                .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)); //获得歌曲id
//        String albumArt = getAlbumArt(album_id);
//        Bitmap bm = null;
//        if (albumArt == null) {
//            mImageView.setBackgroundResource(R.drawable.staring);
//        } else {
//            bm = BitmapFactory.decodeFile(albumArt);
//            BitmapDrawable bmpDraw = new BitmapDrawable(bm);
//            mImageView.setImageDrawable(bmpDraw);
//        }
//    }
//
//
//}
