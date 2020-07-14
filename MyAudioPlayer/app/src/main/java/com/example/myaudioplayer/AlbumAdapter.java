package com.example.myaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyHolder> {

    private Context context;
    private ArrayList<MusicFiles> albumFiles;
    View view;

    public AlbumAdapter(Context context, ArrayList<MusicFiles> albumFiles) {
        this.context = context;
        HashSet<MusicFiles> hs = new HashSet<>();
        hs.addAll(albumFiles);
        ArrayList<MusicFiles> al = new ArrayList<>();
        al.addAll(hs);
        Log.d("Artist", "ArtistAdapter: "+al);
        this.albumFiles = al;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.album_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.albumName.setText(albumFiles.get(position).getAlbum());
        byte[] image=getAlbumart(albumFiles.get(position).getPath());
        if(image!=null){
            Glide.with(context).asBitmap()
                    .load(image).into(holder.albumImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,AlbumDetails.class);
                intent.putExtra("albumname",albumFiles.get(position).getAlbum());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            albumImage=itemView.findViewById(R.id.album_image);
            albumName=itemView.findViewById(R.id.album_Name);

        }
    }
    private byte[] getAlbumart(String uri){
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
