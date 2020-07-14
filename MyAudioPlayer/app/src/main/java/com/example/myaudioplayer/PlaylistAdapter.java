package com.example.myaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private Context context;
    private ArrayList<MusicFiles> playlistFiles;
    View view;

    public PlaylistAdapter(Context context, ArrayList<MusicFiles> playlistFiles) {
        this.context = context;
        this.playlistFiles = playlistFiles;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.playlist_items,parent,false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, final int position) {
        holder.playlist_name.setText(playlistFiles.get(position).getAlbum());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,Playlist_Details.class);
                intent.putExtra("PlaylistName",playlistFiles.get(position).getAlbum());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return playlistFiles.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView playlist_name;
        ImageView menuMorePlaylist;
        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            playlist_name=itemView.findViewById(R.id.Playlist_name);
            menuMorePlaylist=itemView.findViewById(R.id.menu_more_playlist);

        }
    }
}
