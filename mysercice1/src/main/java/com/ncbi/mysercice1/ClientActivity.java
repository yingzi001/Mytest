package com.ncbi.mysercice1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_1, btn_2;
private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        btn_1 = (Button) findViewById(R.id.client_btn1);
        btn_2 = (Button) findViewById(R.id.client_btn2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        intent = new Intent();
        intent.setAction("com.processservice.add");

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.client_btn1:
                startService(intent);
                break;
            case R.id.client_btn2:

                break;
        }
    }
}
