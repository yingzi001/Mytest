package com.ncbi.newsfor3dm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.ncbi.newsfor3dm.utils.FileUtils;
import com.ncbi.newsfor3dm.utils.HttpUtils;
import com.ncbi.newsfor3dm.utils.JsonUtils;
import com.ncbi.newsfor3dm.utils.MyDataBassHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class DownloadService extends Service {
    public static boolean jsonLoadFinash = false;
    private String jsonUrl;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private MyHandler handler;
    public DownloadService() {
    }

    MyDataBassHelper helper;

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new MyDataBassHelper(getApplicationContext());
        handler = new MyHandler();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setTicker("你有一条新信息")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("下载完成");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        //第一次，失败
//                              Log.i("aaa", "onStartCommand执行了");
//                        //异步任务下载json数据并解析，写入数据库；
//                        jsonUrl = intent.getStringExtra("jsonurl");
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                byte[] data = HttpUtils.requestToByteArray(jsonUrl);
//                                if (data != null) {
//                                    String json = null;
//                                    try {
//                                        //json解析
//                                        json = new String(data, "utf-8");
//                                        imgUrls = JsonUtils.jsonToList(json, getApplicationContext());
////                        //获取数据库
////                        SQLiteDatabase db = helper.getReadableDatabase();
////                        //读取图片网址
////                        Cursor cursorlitpic = db.query("news", new String[]{"litpic"}, null, null, null, null, null);
////                        StringBuilder sbImgurls = new StringBuilder();
////                        while (cursorlitpic.moveToNext()) {
////                            sbImgurls.append(cursorlitpic.getString(0).toString() + ",");
////                        }
////                        String temp = new String(sbImgurls);
////                        Log.i("aaa", "图片下载成功" + temp);
////                        picUrl = temp.split(",");
////                        for (int i = 0; i < picUrl.length - 1; i++) {
////                            if (picUrl[i] != "") {
////                                Log.i("aaa", "获取图片网址" + "http://www.3dmgame.com" + picUrl[i]);
////                                if (jsonLoadFinash) {
//                                        //启动服务下载图片
////                            Intent intentForImgDwonload = new Intent(getApplicationContext(),ImgDownloadService.class);
////                            intentForImgDwonload.putExtra("imgurl",picUrl[i]);
////                            intentForImgDwonload.putExtra("imgname",noOfImg);
////                            startService(intent);
//
//
//                                        //启动线程下载图片
////                                    new Thread(new Runnable() {
////                                        @Override
////                                        public void run() {
////                                            byte[] imgData = HttpUtils.requestToByteArray("http://www.3dmgame.com" + picUrl[noOfImg]);
////                                            String imgName = "img" + noOfImg + ".jpg";
////                                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
////                                                File SDroot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
////                                                File img = new File(SDroot, imgName);
////                                                try {
////                                                    FileOutputStream fos = new FileOutputStream(img);
////                                                    if (imgData != null) {
////                                                        fos.write(imgData);
////                                                        Log.i("aaa", "图片下载成功" + picUrl[noOfImg]);
////                                                        fos.close();
////                                                    } else {
////                                                        Log.i("aaa", "图片下载失败" + picUrl[noOfImg]);
////                                                    }
////                                                } catch (FileNotFoundException e) {
////                                                    e.printStackTrace();
////                                                } catch (IOException e) {
////                                                    e.printStackTrace();
////                                                }
////                                            }
////
////                                        }
////                                    }).start();
////                                }
////                            }
////                            noOfImg++;
////                        }
//
//
//                        Iterator<String> it = imgUrls.iterator();
//                        while (it.hasNext()){
//                            String str = it.next();
//                            Log.i("aaa",str);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.i("aaa", "网络解析失败");
//                }
//            }
//        }).start();


        final SQLiteDatabase db = helper.getReadableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = intent.getStringExtra("jsonurl");
                byte[] jsonByte = HttpUtils.requestToByteArray(url);
                if (jsonByte != null) {
                    String json = null;
                    try {
                        json = new String(jsonByte, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //进行json解析；
                    JsonUtils.jsonToList(json, getApplicationContext());
                    int i = 0;
                    //读取数据库的图片列
                    Cursor cursor = db.query("news", new String[]{"id", "litpic"}, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(0);
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        String imgName = path + "/download/" + "img" + i + ".jpg";
                        String imgUrl = "http://www.3dmgame.com" + cursor.getString(1);
                        byte[] imgByte = HttpUtils.requestToByteArray(imgUrl);
                        FileUtils.SaveFileToSD("download", "img" + i + ".jpg", imgByte);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("litpic", "img" + i + ".jpg");
                        Log.i("aaa", "imgname" + imgName);
                        db.execSQL("update news set litpic=? where id=?", new String[]{imgName, id});
                        i++;
                    }

                } else {
                    Log.i("aaa", "json解析失败");
                }
                handler.sendEmptyMessage(1);
            }
        }).start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            builder.setContentText("数据已经下载完成");
            manager.notify(1, builder.build());
            stopSelf();
            Toast.makeText(getApplicationContext(), "下载完成，请点击查看", Toast.LENGTH_LONG).show();
        }
    }

}
