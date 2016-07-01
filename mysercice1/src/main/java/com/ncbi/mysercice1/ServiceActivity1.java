package com.ncbi.mysercice1;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServiceActivity1 extends AppCompatActivity {
    Button btn1;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service1);
        btn1 = (Button) findViewById(R.id.service1_btn1);
        btn2 = (Button) findViewById(R.id.service1_btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(ServiceActivity1.this,MyService1.class);
                startService(startIntent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopIntent = new Intent(ServiceActivity1.this,MyService1.class);
                stopService(stopIntent);
            }
        });

    }
}
