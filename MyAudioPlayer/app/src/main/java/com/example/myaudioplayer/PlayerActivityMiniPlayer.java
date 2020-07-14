package com.example.myaudioplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.myaudioplayer.MainActivity.currentmusic;
import static com.example.myaudioplayer.MainActivity.musicFiles;
import static com.example.myaudioplayer.MainActivity.repeatBoolean;
import static com.example.myaudioplayer.MainActivity.shuffleBoolean;
import static com.example.myaudioplayer.PlayerActivity.mediaPlayer;

public class PlayerActivityMiniPlayer extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private TextView song_name,artist_name,durationPlayed,durationTotal;
    private ImageView cover_art,next_btn,prev_btn,back_btn,shuffle_btn,repeat_btn;
    private FloatingActionButton playPauseBtn;
    private SeekBar seekBar;
    int position=-1;
    static Uri uri;
    static ArrayList<MusicFiles> listsongs =new ArrayList<>();
    private Handler handler;
    private Thread playThread,previousThread,nextThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_mini_player);
        initview();
        getIntentMethod();
//        song_name.setText(listsongs.get(position).getTitle());
//        artist_name.setText(listsongs.get(position).getArtist());
//        mediaPlayer.setOnCompletionListener(this);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
//                if(mediaPlayer!=null && fromuser){
//                    mediaPlayer.seekTo(progress*1000);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        runonuithred();
//        shuffle_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(shuffleBoolean){shuffleBoolean=false;
//                    shuffle_btn.setImageResource(R.drawable.ic_baseline_shuffle);}
//                else{
//                    shuffleBoolean=true;
//                    shuffle_btn.setImageResource(R.drawable.ic_baseline_shuffle_on);
//                }
//            }
//        });
//        repeat_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(repeatBoolean){repeatBoolean=false;
//                    repeat_btn.setImageResource(R.drawable.ic_baseline_repeat_one);}
//                else{
//                    repeatBoolean=true;
//                    repeat_btn.setImageResource(R.drawable.ic_baseline_repeat_on);
//                }
//            }
//        });
    }

    @Override
    protected void onPostResume() {
        playThreadbtn();
        nextThreadbtn();
        previousThreadbtn();
        super.onPostResume();
    }

    private void playThreadbtn() {
        playThread=new Thread(){
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if(mediaPlayer.isPlaying()){
            playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow);
            mediaPlayer.pause();
            //seekBar.setMax(mediaPlayer.getDuration()/1000);
            runonuithred();
        }else {
            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
            mediaPlayer.start();
            //seekBar.setMax(mediaPlayer.getDuration()/1000);
            runonuithred();
        }
    }

    private void runonuithred(){
        seekBar.setMax(mediaPlayer.getDuration()/1000);
        PlayerActivityMiniPlayer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mCurrentPosition=mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(mCurrentPosition);
                    durationPlayed.setText(formattedtime(mCurrentPosition));

                }
                handler=new Handler();
                handler.postDelayed(this,1000);
            }
        });
    }

    private void nextThreadbtn() {
        nextThread=new Thread(){
            @Override
            public void run() {
                super.run();
                next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        int pause_=0;
        int play_=0;
        if(mediaPlayer.isPlaying()){
            play_=1;
        }else{pause_=1;}
        mediaPlayer.stop();
        mediaPlayer.release();
        if(shuffleBoolean && !repeatBoolean){
            position= getRandom(listsongs.size()-1);
        }
        else if(!shuffleBoolean && !repeatBoolean){
            position=(position+1)%listsongs.size();
        }

        uri=Uri.parse(listsongs.get(position).getPath());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        metaData(uri);
        artist_name.setText(listsongs.get(position).getArtist());
        song_name.setText(listsongs.get(position).getTitle());
        runonuithred();

        mediaPlayer.start();

        if(play_==1){
            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
        }else if(pause_==1){playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow);
            mediaPlayer.pause();}
        mediaPlayer.setOnCompletionListener(this);
    }

    private int getRandom(int i) {
        Random random=new Random();
        return random.nextInt(i+1);
    }

    private void previousThreadbtn() {
        previousThread=new Thread(){
            @Override
            public void run() {
                super.run();
                prev_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        previousBtnClicked();
                    }
                });
            }
        };
        previousThread.start();
    }

    private void previousBtnClicked() {
//        if(mediaPlayer.isPlaying()){
//            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
//        }else{
//            playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow);
//        }
        int pause_=0;
        int play_=0;
        if(mediaPlayer.isPlaying()){
            play_=1;
        }else{pause_=1;}
        mediaPlayer.stop();
        mediaPlayer.release();
        if(shuffleBoolean && !repeatBoolean){
            position= getRandom(listsongs.size()-1);
        }
        else if(!shuffleBoolean && !repeatBoolean){
            if(position<=0){position=listsongs.size()-1;}
            else {position=position-1;}
        }

        uri=Uri.parse(listsongs.get(position).getPath());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        metaData(uri);
        artist_name.setText(listsongs.get(position).getArtist());
        song_name.setText(listsongs.get(position).getTitle());
        runonuithred();

        mediaPlayer.start();
        if(play_==1){
            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
        }else if(pause_==1){playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow);
            mediaPlayer.pause();}
        mediaPlayer.setOnCompletionListener(this);
    }

    private String formattedtime(int mCurrentPosition) {
        String totalout="";
        String totalnew="";
        String seconds=String.valueOf(mCurrentPosition%60);
        String minutes=String.valueOf(mCurrentPosition/60);
        //String hours=String.valueOf(mCurrentPosition/3600);
        totalout=minutes+":"+seconds;
        totalnew=minutes+":"+"0"+seconds;
        if(seconds.length()>1){return totalout;}
        else{return totalnew;}
    }

    private void getIntentMethod() {
        //position=getIntent().getIntExtra("position",-1);
        listsongs=musicFiles;

        if(listsongs!=null){
            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
            uri= Uri.parse(currentmusic.getPath());
        }
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }
        else{
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }
        //seekBar.setMax(mediaPlayer.getDuration()/1000);
        //durationTotal.setText(formattedtime(mediaPlayer.getDuration()/1000));
        //metaData(uri);

    }

    private void initview() {
        song_name=findViewById(R.id.song_name);
        artist_name=findViewById(R.id.artist_name);
        durationPlayed=findViewById(R.id.durationPlayed);
        durationTotal=findViewById(R.id.TotalDuration);
        cover_art=findViewById(R.id.cover_art);
        next_btn=findViewById(R.id.skip_next);
        prev_btn=findViewById(R.id.skip_pre);
        back_btn=findViewById(R.id.back_button);
        shuffle_btn=findViewById(R.id.shuffle);
        repeat_btn=findViewById(R.id.repeat);
        playPauseBtn=findViewById(R.id.play_pause);
        seekBar=findViewById(R.id.seekBar);
    }
    private void metaData(Uri uri){
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int duration_total=Integer.parseInt(listsongs.get(position).getDuration())/1000;
        durationTotal.setText(formattedtime(duration_total));
        byte[] art=retriever.getEmbeddedPicture();
        Bitmap bitmap = null;
        if(art!=null){

            //Glide.with(this).asBitmap().load(art).into(cover_art);
            bitmap= BitmapFactory.decodeByteArray(art,0,art.length);
            ImageAnimation(this,cover_art,bitmap);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    Palette.Swatch swatch=palette.getDominantSwatch();
                    if(swatch!=null){
                        ImageView gradient=findViewById(R.id.imageViewGradient);
                        RelativeLayout mContainer=findViewById(R.id.mContainer);
                        gradient.setBackgroundResource(R.drawable.gradient_bg);
                        mContainer.setBackgroundResource(R.drawable.main_bg);
                        GradientDrawable gradientDrawable=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{swatch.getRgb(),0x00000000});
                        gradient.setBackground(gradientDrawable);
                        GradientDrawable gradientDrawablebg=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{swatch.getRgb(),swatch.getRgb()});
                        mContainer.setBackground(gradientDrawablebg);
                        song_name.setTextColor(swatch.getTitleTextColor());
                        artist_name.setTextColor(swatch.getBodyTextColor());
                    }else{
                        ImageView gradient=findViewById(R.id.imageViewGradient);
                        RelativeLayout mContainer=findViewById(R.id.mContainer);
                        gradient.setBackgroundResource(R.drawable.gradient_bg);
                        mContainer.setBackgroundResource(R.drawable.main_bg);
                        GradientDrawable gradientDrawable=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{0xff000000,0x00000000});
                        gradient.setBackground(gradientDrawable);
                        GradientDrawable gradientDrawablebg=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{0xff000000,0xff000000});
                        mContainer.setBackground(gradientDrawable);
                        song_name.setTextColor(Color.WHITE);
                        artist_name.setTextColor(Color.DKGRAY);
                    }
                }
            });
        }else {
            Glide.with(this).asBitmap().load(R.drawable.you).into(cover_art);
            ImageView gradient=findViewById(R.id.imageViewGradient);
            RelativeLayout mContainer=findViewById(R.id.mContainer);
            gradient.setBackgroundResource(R.drawable.gradient_bg);
            mContainer.setBackgroundResource(R.drawable.main_bg);
            song_name.setTextColor(Color.WHITE);
            artist_name.setTextColor(Color.DKGRAY);
        }
    }
    public void ImageAnimation(final Context context, final ImageView imageView, final Bitmap bitmap){
        Animation animOut= AnimationUtils.loadAnimation(context,android.R.anim.fade_out);
        final Animation animIn= AnimationUtils.loadAnimation(context,android.R.anim.fade_in);
        animOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Glide.with(context).load(bitmap).into(imageView);
                animIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(animIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animOut);

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        nextBtnClicked();
        playPauseBtnClicked();
//        if(mediaPlayer!=null){
////            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
////            mediaPlayer.start();
////            mediaPlayer.setOnCompletionListener(this);
//
//        }
    }
}