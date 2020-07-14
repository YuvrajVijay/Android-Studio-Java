package com.yuvrajvi.sharedpref_datasave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String MESSAAGE_ID = "messages_pref";
    private EditText editText;
    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editTextTextPersonName);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editText.getText().toString().trim();

                SharedPreferences sharedPreferences=getSharedPreferences(MESSAAGE_ID,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("message",message);
                editor.apply(); //saving to disk
                view();
            }
        });

        //get data back from sp


    }
    private void view(){
        SharedPreferences getShareData=getSharedPreferences(MESSAAGE_ID,MODE_PRIVATE);
        String value=getShareData.getString("message","Nothing yet");

        textView.setText(value);
    }
}