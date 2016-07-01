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

public class MessengerClientActivity extends AppCompatActivity {
    private Button btn1, btn2;
    private Messenger sendMessenger;
    private Messenger clientMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);
        btn1 = (Button) findViewById(R.id.message_btn1);
        btn2 = (Button) findViewById(R.id.message_btn2);

        clientMessenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("aaa", "客户端发过来的消息" + msg.arg1);
            }
        });

        Intent intent = new Intent();
        intent.setAction("com.message.service");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i("aaa", "连接成功");
                sendMessenger = new Messenger(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Context.BIND_AUTO_CREATE);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = Message.obtain();
                msg.arg1 = 1;
                msg.arg2 = 9;
                msg.replyTo = clientMessenger;
                try {
                    sendMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
