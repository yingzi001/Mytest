package com.ncbi.customview.huatu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.ncbi.customview.R;

public class HuatuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到屏幕的宽高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        HuatuView huatuView = new HuatuView(HuatuActivity.this,displayMetrics.widthPixels,displayMetrics.heightPixels);
        setContentView(huatuView);
    }
}