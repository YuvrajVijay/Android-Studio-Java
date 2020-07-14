package com.example.playvideoexo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView=findViewById(R.id.playerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        exoPlayer= ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());

        playerView.setPlayer(exoPlayer);

        DataSource.Factory datasourceFactory=new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,getString(R.string.app_name)));

        MediaSource videoSource=new ExtractorMediaSource.Factory(datasourceFactory)
                .createMediaSource(Uri.parse("https://buildappswithpaulo.com/videos/outro_music.mp4"));

        exoPlayer.prepare(videoSource);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        exoPlayer.release();
        exoPlayer=null;
    }
}