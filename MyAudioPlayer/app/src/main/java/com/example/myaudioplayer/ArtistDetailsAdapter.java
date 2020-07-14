package com.example.myaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.myaudioplayer.MainActivity.musicFiles;

public class ArtistDetailsAdapter extends RecyclerView.Adapter<ArtistDetailsAdapter.ArtistDetailsHolder> {

    private Context context;
    private ArrayList<MusicFiles> artistFiles;
    View view;

    public ArtistDetailsAdapter(Context context, ArrayList<MusicFiles> artistFiles) {
        this.context = context;
        this.artistFiles = artistFiles;
    }

    @NonNull
    @Override
    public ArtistDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.music_items,parent,false);
        return new ArtistDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistDetailsHolder holder, final int position) {
        holder.MusicName.setText(artistFiles.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,PlayerActivity.class);
                int position1=getposition(position);
                intent.putExtra("position",position1);
                context.startActivity(intent);
            }
        });
    }

    private int getposition(int position) {
        for(int i=0;i<musicFiles.size();i++){
            if(musicFiles.get(i).getTitle().equals(artistFiles.get(position).getTitle())){
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return artistFiles.size();
    }

    public class ArtistDetailsHolder extends RecyclerView.ViewHolder {
        TextView MusicName;
        public ArtistDetailsHolder(@NonNull View itemView) {
            super(itemView);
            MusicName=itemView.findViewById(R.id.music_filename);
        }
    }
}
