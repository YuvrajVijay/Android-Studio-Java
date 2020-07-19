package com.example.androiddrawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

import java.lang.reflect.Array;

public class Drawing extends View{
    private Paint brush,redBrush;
    private LinearGradient linearGradient;
    private RadialGradient radialGradient;
    private SweepGradient sweepGradient;
    private Bitmap bitmap;
    public Drawing(Context context) {
        super(context);
        init();
    }

    private void init() {
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.you);
        linearGradient=new LinearGradient(0,0,200,200,Color.RED,Color.GREEN, Shader.TileMode.MIRROR);
        radialGradient=new RadialGradient(0,0,50f,Color.RED,Color.BLUE,Shader.TileMode.REPEAT);
        sweepGradient=new SweepGradient(getMeasuredWidth()/2,getMeasuredHeight()/2,new int[]{Color.RED,Color.BLUE,Color.GREEN},null);
        brush=new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setColor(Color.parseColor("green"));
        redBrush=new Paint(Paint.ANTI_ALIAS_FLAG);
        redBrush.setColor(Color.RED);
        redBrush.setStrokeWidth(24f);
        brush.setShader(sweepGradient);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,getMeasuredWidth()/2-bitmap.getWidth()/2,getMeasuredHeight()/2-bitmap.getHeight()/2,null);
//        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,100f,brush);
//        canvas.drawLine(0,0,getMeasuredWidth()/2,getMeasuredHeight()/2,redBrush);

        canvas.save();
    }
}
