package com.ncbi.customview.huatu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by acer on 2016/6/30.
 */
public class HuatuView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private float mx, my;
    private Path path;

    public HuatuView(Context context, int width, int height) {
        super(context);
        paint = new Paint();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);//给画板添加画布；
        paint.setAntiAlias(true);//设置平滑效果；
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);//线的

    }

    public HuatuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HuatuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        float x = event.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("aaa", "down");
                path = new Path();
                path.moveTo(x, y);
                mx = x;
                my = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("aaa", "move");
                //得到滑动距离
                float dx = Math.abs(x - mx);
                float dy = Math.abs(y - my);
                if (dx > 4 | dy > 4) {
                }
                mx = x;
                my = y;
                break;
            case MotionEvent.ACTION_UP:
                Log.i("aaa","up");
                break;
        }
        path.lineTo(x, y);
        canvas.drawPath(path, paint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
