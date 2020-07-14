package com.example.playvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private Button pause,play,skip;
    private SurfaceView surfaceView;
    private LinearLayout linearLayout;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer=MediaPlayer.create(this,R.raw.madara_uchiha_centuries);


        surfaceView=findViewById(R.id.surfaceView);
        linearLayout=findViewById(R.id.linearLayout);

        surfaceView.setKeepScreenOn(true);

        SurfaceHolder holder=surfaceView.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(400,300);

        pause=findViewById(R.id.buttonPause);
        play=findViewById(R.id.buttonPlay);
        skip=findViewById(R.id.buttonSkip);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPlay:
                mediaPlayer.start();
                break;
            case R.id.buttonPause:
                mediaPlayer.pause();
                break;
            case R.id.buttonSkip:
                mediaPlayer.seekTo(mediaPlayer.getDuration()/2);
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }
}