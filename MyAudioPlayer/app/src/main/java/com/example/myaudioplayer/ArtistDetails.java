package com.example.myaudioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.myaudioplayer.MainActivity.musicFiles;

public class ArtistDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView artistNameInside;
    String artistName;
    ArrayList<MusicFiles> artistSongs=new ArrayList<>();
    ArtistDetailsAdapter artistDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);
        recyclerView=findViewById(R.id.recyclerView_artist_details);
        artistNameInside=findViewById(R.id.artist_name_inside);
        artistName=getIntent().getStringExtra("artistName");
        int j=0;
        for(int i=0;i<musicFiles.size();i++){
            if(artistName.equals(musicFiles.get(i).getArtist())){
                artistSongs.add(j,musicFiles.get(i));
                j++;
            }
        }
        artistNameInside.setText(artistSongs.get(0).getArtist());
    }
    protected void onResume() {
        super.onResume();
        if(artistSongs.size()>=1){
            artistDetailsAdapter=new ArtistDetailsAdapter(this,artistSongs);
            recyclerView.setAdapter(artistDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        }
    }
}