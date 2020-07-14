package com.example.myaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.myaudioplayer.MainActivity.musicFiles;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.MyHolder> {

    private Context context;
    private ArrayList<MusicFiles> albumFiles;
    View view;

    public AlbumDetailsAdapter(Context context, ArrayList<MusicFiles> albumFiles) {
        this.context = context;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.music_items,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.albumName.setText(albumFiles.get(position).getTitle());
        byte[] image=getAlbumart(albumFiles.get(position).getPath());
        if(image!=null){
            Glide.with(context).asBitmap()
                    .load(image).into(holder.albumImage);
        }
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
            if(musicFiles.get(i).getTitle().equals(albumFiles.get(position).getTitle())){
                return i;
            }
        }
        return 0;
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

            albumImage=itemView.findViewById(R.id.music_img);
            albumName=itemView.findViewById(R.id.music_filename);

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
