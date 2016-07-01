package com.ncbi.processservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class Messenger2Service extends Service {
    private Messenger messenger = new Messenger(new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("aaa","客户端发来的消息"+msg.arg1+":"+msg.arg2);
            msg.arg1 = msg.arg1+msg.arg2;
            Messenger msger = msg.replyTo;
            try {
                msger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });
    public Messenger2Service() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
