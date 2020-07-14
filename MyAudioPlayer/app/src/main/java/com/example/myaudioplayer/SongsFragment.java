package com.example.myaudioplayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.myaudioplayer.MainActivity.currentmusic;
import static com.example.myaudioplayer.MainActivity.musicFiles;
import static com.example.myaudioplayer.PlayerActivity.mediaPlayer;

public class SongsFragment extends Fragment {


    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private RelativeLayout miniplayer;
    private TextView miniplayersongname;
    private FloatingActionButton play_pause;
    private Thread playThread;
    public SongsFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        miniplayer=view.findViewById(R.id.miniPlayer);
        miniplayersongname=view.findViewById(R.id.miniplayersongname);
        play_pause=view.findViewById(R.id.play_pause_mini);

        Log.d("Trackinfo", "onCreateView: "+currentmusic);
        if(currentmusic!=null){
            miniplayersongname.setText(currentmusic.getTitle());
        }
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                play_pause.setImageResource(R.drawable.ic_baseline_play_arrow);
                //seekBar.setMax(mediaPlayer.getDuration()/1000);
            }else {
                play_pause.setImageResource(R.drawable.ic_baseline_pause);
                //seekBar.setMax(mediaPlayer.getDuration()/1000);
            }
        }
        playThreadbtn();
        miniplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlayerActivity.class);
                //intent.putExtra("position",position);
                getContext().startActivity(intent);
            }
        });
        if(musicFiles.size()>=1){
            musicAdapter=new MusicAdapter(getContext(),musicFiles);
            recyclerView.setAdapter(musicAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        }
        return view;
    }

    private void playThreadbtn() {
        playThread=new Thread(){
            @Override
            public void run() {
                super.run();

                if(currentmusic!=null){
                    miniplayersongname.setText(currentmusic.getTitle());
                }
                play_pause.setOnClickListener(new View.OnClickListener() {
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
        if(currentmusic!=null){
            miniplayersongname.setText(currentmusic.getTitle());
        }
        if(mediaPlayer.isPlaying()){
            play_pause.setImageResource(R.drawable.ic_baseline_play_arrow);
            mediaPlayer.pause();
            //seekBar.setMax(mediaPlayer.getDuration()/1000);
        }else {
            play_pause.setImageResource(R.drawable.ic_baseline_pause);
            mediaPlayer.start();
            //seekBar.setMax(mediaPlayer.getDuration()/1000);
        }
    }

}