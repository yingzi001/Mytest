package com.ncbi.newsfor3dm.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by acer on 2016/6/22.
 */
public class HttpUtils {

    public static byte[] requestToByteArray(String urlPaath) {
        ByteArrayOutputStream baoos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlPaath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
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

    public static Bitmap requestToBitmap(String imgUrl) {
        byte[] data = requestToByteArray(imgUrl);
        if (data != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        }
        return null;
    }

}
