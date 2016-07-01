package com.ncbi.mysercice1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bound_Service1Activity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    BoundService1.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service1);
        btn1 = (Button) findViewById(R.id.bound_service1_btn1);
        btn2 = (Button) findViewById(R.id.bound_service1_btn2);
        btn3 = (Button) findViewById(R.id.bound_service1_btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        Intent intent = new Intent(this, BoundService1.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = (BoundService1.MyBinder) iBinder;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bound_service1_btn1:
                myBinder.start();
                break;
            case R.id.bound_service1_btn2:
                myBinder.pause();
                break;
            case R.id.bound_service1_btn3:
                myBinder.stop();
                break;
        }
    }
}