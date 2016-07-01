package com.ncbi.mysercice1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by acer on 2016/6/24.
 */
public class BroadcastReceiver1 extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("aaa","通知收到了！");
    }
}
