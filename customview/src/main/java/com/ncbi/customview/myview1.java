package com.ncbi.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by acer on 2016/6/30.
 */
public class myview1 extends View {
    private String txt_value;
    private Drawable img_value;

    public myview1(Context context) {
        super(context);
    }

    public myview1(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myview1);
        txt_value = typedArray.getString(R.styleable.myview1_txt);
        img_value = typedArray.getDrawable(R.styleable.myview1_image);
    }

    public myview1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        BitmapDrawable bd = (BitmapDrawable) img_value;
        Bitmap b = bd.getBitmap();
        canvas.drawBitmap(b, 0, 0, paint);
        canvas.drawText(txt_value, b.getWidth(), (b.getHeight() + paint.getTextSize()) / 2, paint);
    }
}
