package com.ncbi.newsfor3dm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ncbi.newsfor3dm.utils.HttpUtils;
import com.ncbi.newsfor3dm.utils.JsonUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private String jsonUrl = "http://www.3dmgame.com/sitemap/api.php?row=10&typeid=1&paging=1&page=1";
    private Button btn_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_info = (Button) findViewById(R.id.download_btn1);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("jsonurl",jsonUrl);
                startActivity(intent);
            }
        });

        Intent intentServiceFordownload = new Intent(this, DownloadService.class);
        intentServiceFordownload.putExtra("jsonurl", jsonUrl);
        startService(intentServiceFordownload);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this,DownloadService.class);
        stopService(intent);
    }
}
