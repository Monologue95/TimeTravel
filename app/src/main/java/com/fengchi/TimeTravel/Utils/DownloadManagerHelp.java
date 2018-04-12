package com.fengchi.TimeTravel.Utils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by stormbaron on 17-5-27.
 */

/**
 * 文件下载功能,使用Android的下载服务
 */
public class DownloadManagerHelp {
    public DownloadManagerHelp(Context context) {
    }

    public static void load(Context mContext, String url, String fileNsme) {
        @SuppressLint("ServiceCast") DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        //显示下载界面
        request.setVisibleInDownloadsUi(true);
        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*/

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            request.setDestinationInExternalPublicDir("/juli/files/", fileNsme);
        }
        long id = downloadManager.enqueue(request);
    }
}
