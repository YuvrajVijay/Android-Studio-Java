package com.example.myaudioplayer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PlaylistNamesAdapter extends RecyclerView.Adapter<PlaylistNamesAdapter.PlaylistsNameHolder> {

    private Context context;
    private Activity activity;

    View view;

    public PlaylistNamesAdapter(Context context,Activity activity) {
        SharedPreferences getShareData=activity.getSharedPreferences("Playlistlist",Context.MODE_PRIVATE);
        Set<String> value=getShareData.getStringSet("Playlists", Collections.singleton(""));
        int n = value.size();
        String arr[] = new String[n];
        arr = value.toArray(arr);
        this.context = context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public PlaylistsNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.playlist_items,parent,false);
        return new PlaylistsNameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistsNameHolder holder, int position) {
//        holder.playlist_name.setText(playlistFiles.get(position).getAlbum());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(context,Playlist_Details.class);
//                intent.putExtra("PlaylistName",playlistFiles.get(position).getAlbum());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PlaylistsNameHolder extends RecyclerView.ViewHolder {
        TextView playlist_name;
        ImageView menuMorePlaylist;
        public PlaylistsNameHolder(@NonNull View itemView) {
            super(itemView);
            playlist_name=itemView.findViewById(R.id.Playlist_name);
            menuMorePlaylist=itemView.findViewById(R.id.menu_more_playlist);
        }
    }



}
