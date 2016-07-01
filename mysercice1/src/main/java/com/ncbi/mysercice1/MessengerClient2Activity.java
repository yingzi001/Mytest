package com.ncbi.mysercice1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MessengerClient2Activity extends AppCompatActivity {
    private Messenger messengerSend;
    private Messenger messengerClient;
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client2);
        btn1 = (Button) findViewById(R.id.messengerclient2_btn1);

        Intent intent = new Intent();
        intent.setAction("com.message.service2");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //绑定服务
                messengerSend = new Messenger(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Context.BIND_AUTO_CREATE);

            messengerClient = new Messenger(new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.i("aaa","服务端回复的信息："+msg.arg1);
                }
            });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = Message.obtain();
                message.arg1 = 1;
                message.arg2 = 9;
                message.replyTo = messengerClient;
                try {
                    messengerSend.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
