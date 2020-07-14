package com.example.myaudioplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.example.myaudioplayer.MainActivity.musicFiles;


public class PlaylistFragment extends Fragment {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private RelativeLayout create_playlist,fav_playlist;
    private MusicAdapter PlaylistAdapter;
    private EditText createplaylistedittext;
    private Button createButton;
    public PlaylistFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_playlist, container, false);
        recyclerView=view.findViewById(R.id.recyclerView_playlist);
        recyclerView.setHasFixedSize(true);
        create_playlist=view.findViewById(R.id.relative_layout_add_playlist);
        fav_playlist=view.findViewById(R.id.relative_layout_fav_playlist);

        create_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createpopup();
            }
        });
        fav_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        if(musicFiles.size()>=1){
//            PlaylistAdapter=new MusicAdapter(getContext(),musicFiles);
//            recyclerView.setAdapter(PlaylistAdapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
//        }
        return view;
    }

    private void createpopup() {
        builder=new AlertDialog.Builder(getContext());
        View view1=getLayoutInflater().inflate(R.layout.newplaylist,null);
        createplaylistedittext=view1.findViewById(R.id.edittextplaylist);
        createButton=view1.findViewById(R.id.createButton);
        builder.setView(view1);
        dialog=builder.create();
        dialog.show();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Set<String> tempset=new HashSet<>();
//                SharedPreferences getShareData=getActivity().getSharedPreferences("Playlistlist",Context.MODE_PRIVATE);
//                Set<String> value=getShareData.getStringSet("Playlists", Collections.singleton(""));
//                tempset.addAll(value);
//
//                SharedPreferences.Editor editor=getShareData.edit();
//                editor.putStringSet("Playlists",tempset);
//                SharedPreferences Playlistlist =getActivity().getSharedPreferences(createplaylistedittext.getText().toString().trim(), Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor=Playlistlist.edit();
//                editor.putStringSet("",message);
//                editor.apply(); //saving to disk
                dialog.dismiss();
                updateplaylist();
            }
        });
    }


//    public static ArrayList<MusicFiles> getAllPlaylists(Context context){
//        ArrayList<String> tempplaylist =new ArrayList<>();
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String[] projection={
//
//
//        };
//        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);
//        if(cursor!=null){
//            while (cursor.moveToNext()){
//                String album=cursor.getString(0);
//                String title=cursor.getString(1);
//                String duration=cursor.getString(2);
//                String path=cursor.getString(3);
//                String artist=cursor.getString(4);
//                String id=cursor.getString(5);
//                MusicFiles musicFiles =new MusicFiles(path,artist,album,title,duration,id);
//                //Log.e("path"+path, "getAllAudio: "+album );
//                tempaudiolist.add(musicFiles);
//            }
//            cursor.close();
//        }
//        return tempaudiolist;
//    }

    private void updateplaylist() {
//        SharedPreferences getShareData=getActivity().getSharedPreferences("Playlistlist",Context.MODE_PRIVATE);
//        Set<String> value=getShareData.getStringSet("Playlists", Collections.singleton(""));
        PlaylistAdapter=new MusicAdapter(getContext(),musicFiles);
        recyclerView.setAdapter(PlaylistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

}