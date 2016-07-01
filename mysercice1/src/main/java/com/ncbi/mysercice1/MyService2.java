package com.ncbi.mysercice1;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyService2 extends Service {
    String urlPath = null;

    public MyService2() {
    }

    //声明通知管理器
    NotificationManager manager;
    //声明构通知建器
    NotificationCompat.Builder builder;
    MyHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        //构建器和管理器初始化
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(getApplicationContext());
        //设置小图标，否则不显示通知
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("下载图片");
        builder.setContentText("下载中···");
        handler = new MyHandler();
    }

    @Override
    //onStartCommand方法中进行耗时操作；
    public int onStartCommand(Intent intent, int flags, int startId) {
        urlPath = intent.getStringExtra("urlPath");
        if (urlPath != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] data = requestToByteArray(urlPath);
                    boolean result = saveFile(data, "bingbing.jpg");
                    if (result) {
                        //文件保存成功，
                        handler.sendEmptyMessage(1);
                    }

                }
            }).start();
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public byte[] requestToByteArray(String urlPaath) {
        ByteArrayOutputStream baoos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlPaath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式；
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            //开启输入流
            connection.setDoInput(true);
            connection.connect();
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
                int totalLength = connection.getContentLength();
                int currentLength = 0;
                baoos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024 * 4];
                while ((len = is.read(buf)) != -1) {
                    currentLength += len;
                    int currentRate = (int) (currentLength / (float) totalLength * 100);
                    baoos.write(buf, 0, len);
                    Message msg = new Message();
                    msg.arg1 = currentRate;
                    handler.sendMessage(msg);
                }
            }
            return baoos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean saveFile(byte[] data, String fileName) {
        boolean flag = false;
        //判断sd卡是否挂在成功；
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获取SD卡根目录；
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(root, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                if (fos != null) {
                    fos.close();
                }
                flag = true;
                return flag;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 != 0) {
                //更新进度
                builder.setProgress(100, msg.arg1, false);
                manager.notify(100, builder.build());
            }
            if (msg.what == 1) {
                //下载完成时，更改内容
                builder.setContentText("下载完成");
                manager.notify(100, builder.build());
                //关闭服务，stopService
                stopSelf();
                Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_LONG).show();
            }
        }
    }
}


