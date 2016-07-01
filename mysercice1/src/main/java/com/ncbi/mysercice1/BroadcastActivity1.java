package com.ncbi.mysercice1;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BroadcastActivity1 extends AppCompatActivity {
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast1);
        btn1 = (Button) findViewById(R.id.broadcast1_btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BroadcastActivity1.this,BroadcastReceiver1.class);
                intent.setAction("com.ncbi.broadcastreceiver1");
                sendBroadcast(intent);

            }
        });

    }
}
