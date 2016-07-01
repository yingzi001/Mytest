package com.ncbi.newsfor3dm;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.ncbi.newsfor3dm.utils.HttpUtils;
import com.ncbi.newsfor3dm.utils.MyDataBassHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImgDownloadService extends Service {
    private String imgurl;
    private String noOfImg;
    MyDataBassHelper helper;

    public ImgDownloadService() {
        helper = new MyDataBassHelper(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        imgurl = intent.getStringExtra("imgurl");
        noOfImg = intent.getStringExtra("imgname");
        SQLiteDatabase db = helper.getReadableDatabase();
                                    new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    byte[] imgData = HttpUtils.requestToByteArray(imgurl);
                                    String imgName = "img" + noOfImg + ".jpg";
                                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                        File SDroot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                        File img = new File(SDroot, imgName);
                                        try {
                                            FileOutputStream fos = new FileOutputStream(img);
                                            if (imgData != null) {
                                                fos.write(imgData,0,imgData.length);
                                                Log.i("aaa", "图片下载成功");
                                                fos = null;
                                            } else {
                                                Log.i("aaa", "图片下载失败");
                                            }
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }).start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
