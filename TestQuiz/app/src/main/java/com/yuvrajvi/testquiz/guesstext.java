package com.yuvrajvi.testquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class guesstext extends AppCompatActivity {
    private TextView showGuessTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesstext);
        showGuessTextView=findViewById(R.id.guesstext);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            showGuessTextView.setText(extra.getString("Guess"));
        }

        showGuessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =getIntent();
                intent.putExtra("message_back","From Second Activity");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        //String value= getIntent().getStringExtra("Guess");
        //if(value!=null){
        //    showGuessTextView.setText(value);
        //}
    }


}