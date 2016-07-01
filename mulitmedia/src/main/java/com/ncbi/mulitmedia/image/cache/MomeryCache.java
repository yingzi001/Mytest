package com.ncbi.mulitmedia.image.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by acer on 2016/6/27.
 */
public class MomeryCache {
    //key   表示当前图片的url地址
    private LruCache<String, Bitmap> lruCache;

    //获取缓存大小
    public MomeryCache() {
        //获取当前app能够占用的总内存大小
        int maxMomery = (int) Runtime.getRuntime().maxMemory();//32M
        //把内存的1/8用来作缓存
        int cacheMomery = maxMomery / 8;//4M

        lruCache = new LruCache<String, Bitmap>(cacheMomery) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //得到当前每张图片的缓存大小；
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    //synchronized 同步
    public synchronized void addBitmapToLruCache(String key, Bitmap value) {
        if (key != null) {
            if (value != null) {
                lruCache.put(key, value);
            }
        }
    }

    public Bitmap getBitmapFromLruCache(String key) {
        if (key != null) {
            if (lruCache.get(key) != null) {
                Bitmap bitmap = lruCache.get(key);
                return bitmap;
            }
        }
        return null;
    }

    public void removeBitmapFromLruCache(String key) {
        if (key != null) {
            Bitmap bitmap = lruCache.remove(key);
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    public void clear() {
        if (lruCache.size() > 0) {
            lruCache.evictAll();
        }
        lruCache = null;
    }


}
