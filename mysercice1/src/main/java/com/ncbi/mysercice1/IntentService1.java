package com.ncbi.mysercice1;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class IntentService1 extends IntentService {
    MyHandler handler ;
    public IntentService1() {
        super("IntentService1");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Toast.makeText(getApplicationContext(),"下载成功",Toast.LENGTH_LONG).show();
            }
        }
    }

}
