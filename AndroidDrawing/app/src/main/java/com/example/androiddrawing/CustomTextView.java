package com.example.androiddrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint paint;

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(200f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("Hello Yuvraj",getMeasuredWidth()/2,getMeasuredHeight()/2,paint);
        canvas.save();
    }
}
