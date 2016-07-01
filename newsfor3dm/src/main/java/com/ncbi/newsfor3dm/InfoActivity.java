package com.ncbi.newsfor3dm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.ncbi.newsfor3dm.utils.MyDataBassHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InfoActivity extends AppCompatActivity {
    private ListView lv;
    private MyDataBassHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        lv = (ListView) findViewById(R.id.news_iv1);
        helper = new MyDataBassHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id as _id,litpic,title from news", null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.newslist_item, cursor, new String[]{"title", "litpic"}, new int[]{R.id.title_tv, R.id.litpic_iv});
        lv.setAdapter(adapter);

    }
}
