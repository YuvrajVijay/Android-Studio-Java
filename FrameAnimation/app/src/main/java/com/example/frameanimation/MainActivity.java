package com.example.frameanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //private AnimationDrawable setanimation;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=findViewById(R.id.imageView);
        //imageView.setBackgroundResource(R.drawable.anim_list);
        //setanimation= (AnimationDrawable) imageView.getBackground();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //setanimation.start();
        Handler handler=new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                imageView.startAnimation(animation);
                //setanimation.stop();
            }
        },50);
        return super.onTouchEvent(event);
    }
}