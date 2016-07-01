package com.ncbi.mysercice1;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class ServiceActivity2 extends AppCompatActivity {
    private String urlPath = "http://b.hiphotos.baidu.com/image/pic/item/7a899e510fb30f2493c8cbedcc95d143ac4b0389.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2);

    }
    public void service2_btn_downloadimg(View view){
        Intent intent = new Intent(getApplicationContext(),MyService2.class);
        intent.putExtra("urlPath",urlPath);
        startService(intent);
    }
}
