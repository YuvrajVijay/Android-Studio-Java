package com.example.androiddrawing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawing drawing=new Drawing(this);
        CustomTextView textView=new CustomTextView(this,null);
        setContentView(textView);

//        textView=findViewById(R.id.textView);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"Clicked success",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}