package com.example.myaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {

    private Context context;
    private ArrayList<MusicFiles> artistFiles;
    View view;


    public ArtistAdapter(Context context, ArrayList<MusicFiles> artistFiles) {
        this.context = context;

        HashSet<MusicFiles> hs = new HashSet<>();
        hs.addAll(artistFiles);
        ArrayList<MusicFiles> al = new ArrayList<>();
        al.addAll(hs);
        Log.d("Artist", "ArtistAdapter: "+al);
        this.artistFiles = al;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.artist_item,parent,false);
        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, final int position) {
        holder.artist_name.setText(artistFiles.get(position).getArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,ArtistDetails.class);
                intent.putExtra("artistName",artistFiles.get(position).getArtist());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistFiles.size();
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {
        TextView artist_name;
        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            artist_name=itemView.findViewById(R.id.artist_name_item);
        }
    }
}
