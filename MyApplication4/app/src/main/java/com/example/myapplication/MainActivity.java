package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button rollbutton = (Button)findViewById(R.id.rollbutton);
        final TextView resultstextview = (TextView)findViewById(R.id.resultstextview);
        final SeekBar seekbar =(SeekBar)findViewById(R.id.seekBar);
        final Random random ;
        random=new Random();
        rollbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int range = seekbar.getProgress();
                int randomNum;

                if(range==0){
                    randomNum = random.nextInt();
                }else{
                    randomNum = random.nextInt(range+1);
                }
                resultstextview.setText(Integer.toString(randomNum));
            }
        });

    }
}
