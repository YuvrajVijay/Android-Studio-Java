package com.example.myaudioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Playlist_Details extends AppCompatActivity {

    RecyclerView recyclerView;
    String playlistName;
    ArrayList<MusicFiles> playlistSongs=new ArrayList<>();
    PlaylistDetailsAdapter playlistDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist__details);
        recyclerView=findViewById(R.id.recyclerView_playlist_details);
        playlistName=getIntent().getStringExtra("albumname");

    }
}