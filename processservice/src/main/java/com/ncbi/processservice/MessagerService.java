package com.ncbi.processservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessagerService extends Service {
    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //得到客户端的消息
            Message message = Message.obtain(msg);
            //打印消息
            Log.i("aaa", message.arg1 + ":" + message.arg2);
            message.arg1 = message.arg1 + message.arg2;
            try {
                //给客户端回复消息,msg中包含有客户端的信息，及服务端如何返回消息
                Messenger msc = message.replyTo;
                msc.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    });

    public MessagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
