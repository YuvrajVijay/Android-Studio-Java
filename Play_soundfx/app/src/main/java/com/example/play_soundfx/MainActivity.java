package com.example.play_soundfx;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1,button2,button3,button4;
    private SoundPool soundPool;
    private int sound1,sound2,sound3,sound4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioAttributes audioAttributes=new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();
        soundPool=new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(audioAttributes)
                .build();

        sound1=soundPool.load(this,R.raw.complete,1);
        sound2=soundPool.load(this,R.raw.correct,1);
        sound3=soundPool.load(this,R.raw.defeat_one,1);
        sound4=soundPool.load(this,R.raw.defeat_two,1);


        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                soundPool.play(sound1,0,1,0,0,2);
                break;
            case R.id.button2:
                soundPool.play(sound2,1,0,0,0,1);
                break;
            case R.id.button3:
                soundPool.play(sound3,1,1,1,1,1);
                break;
            case R.id.button4:
                soundPool.play(sound4,1,1,0,0,1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(soundPool!=null){
            soundPool.release();
            soundPool=null;
        }
    }
}