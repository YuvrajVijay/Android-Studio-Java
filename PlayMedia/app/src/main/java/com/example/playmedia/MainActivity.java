package com.example.playmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.playmedia.utils.constants;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton=findViewById(R.id.playButton);
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(constants.url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaPlayer.OnPreparedListener preparedListener=new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mediaPlayer) {
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mediaPlayer.isPlaying()){
                            pauseMusic();
                        }else{
                            playMusic();
                        }
                    }
                });
            }
        };
        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.prepareAsync();
        //mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.dangerously);

    }
    public void playMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
            playButton.setText("Pause");
        }
    }
    public void pauseMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            playButton.setText("Play");
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