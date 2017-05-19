package com.toolsclass.chenjun.general.generalcj.AppMedia.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.toolsclass.chenjun.general.generalcj.AppMedia.model.Music;
import com.toolsclass.chenjun.general.generalcj.Config.ShareConfig;
import com.toolsclass.chenjun.general.generalcj.Libs.sharelibrary.Share;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import java.util.List;

import static android.R.attr.id;


/**
 * 歌曲工具类
 * Created by wcy on 2015/11/27.
 */
public class MusicUtils {

    /**
     * 扫描歌曲
     */
    public static void scanMusic(Context context, List<Music> musicList) {
        musicList.clear();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return;
        }
        while (cursor.moveToNext()) {
            // 是否为音乐，魅族手机上始终为0
            // int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            // if (isMusic == 0) {
            //     continue;
            // }
            long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            LogUtils.i("文件大小："+fileSize + "\n比较大小：" + Share.getInt(ShareConfig.SHARE_MOBILE_SCEN_FILE_SIZE,300)*1024);
            if (fileSize < Share.getInt(ShareConfig.SHARE_MOBILE_SCEN_FILE_SIZE,300)*1024)
                continue;
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String unknown = context.getString(R.string.media_unknown);
            artist = artist.equals("<unknown>") ? unknown : artist;
            String album = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            String coverPath = getCoverPath(context, albumId);
            String fileName = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
            Music music = new Music();
            music.setId(id);
            music.setType(Music.Type.LOCAL);
            music.setTitle(title);
            music.setArtist(artist);
            music.setAlbum(album);
            music.setDuration(duration);
            music.setPath(path);
            music.setCoverPath(coverPath);
            music.setFileName(fileName);
            music.setFileSize(fileSize);
            CoverLoader.getInstance().loadThumbnail(music);
            musicList.add(music);
        }
        cursor.close();
    }

    private static String getCoverPath(Context context, long albumId) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://media/external/audio/albums/" + albumId),
                new String[]{"album_art"}, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            if (cursor.getColumnCount() > 0) {
                path = cursor.getString(0);
            }
            cursor.close();
        }
        return path;
    }
}
