package com.example.myaudioplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.myaudioplayer.MainActivity.musicFiles;

public class PlaylistDetailsAdapter extends RecyclerView.Adapter<PlaylistDetailsAdapter.PlalistDetailsHolder> {

    private Context context;
    private ArrayList<MusicFiles> PlaylistFiles;
    View view;

    public PlaylistDetailsAdapter(Context context, ArrayList<MusicFiles> playlistFiles) {
        this.context = context;
        PlaylistFiles = playlistFiles;
    }

    @NonNull
    @Override
    public PlalistDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.music_items,parent,false);
        return new PlalistDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlalistDetailsHolder holder, int position) {
        holder.songName.setText(PlaylistFiles.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return PlaylistFiles.size();
    }

    public class PlalistDetailsHolder extends RecyclerView.ViewHolder {
        TextView songName;
        public PlalistDetailsHolder(@NonNull View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.music_filename);
        }
    }
}
