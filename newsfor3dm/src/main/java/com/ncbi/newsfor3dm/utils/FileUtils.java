package com.ncbi.newsfor3dm.utils;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by acer on 2016/6/25.
 */
public class FileUtils {
    //保存图片到sd卡
    public static boolean SaveFileToSD(String dir, String fileName, byte[] data) {
        // 判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (SDPath != null) {
                StatFs statFs = new StatFs(SDPath);
                //获取块数，
                int freeCount = statFs.getFreeBlocks();
                //获取块的大小
                long size = statFs.getBlockSize();
                //中的可用空间大小
                long area = freeCount * size;
                //判断空间是否够用
                if (area > data.length) {
                    File dirFile = new File(SDPath + File.separator + dir);
                    //如果不存在，就创建目录；
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
                    try {
                        FileOutputStream fos = new FileOutputStream(new File(dirFile, fileName));
                        fos.write(data, 0, data.length);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return false;
    }
}
