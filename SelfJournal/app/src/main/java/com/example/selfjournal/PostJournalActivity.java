package com.example.selfjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PostJournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_journal);

//        Bundle bundle=getIntent().getExtras();
//        if(bundle!=null){
//            String username=bundle.getString("username");
//            String userId=bundle.getString("userId");
//        }
    }
}