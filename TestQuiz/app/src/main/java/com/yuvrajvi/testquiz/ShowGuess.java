package com.yuvrajvi.testquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShowGuess extends AppCompatActivity {

    private Button showguess;
    private EditText enterguess;
    private final int REQUEST_CODE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guess);
        showguess=findViewById(R.id.show_guess);
        enterguess=findViewById(R.id.enter_guess);
        showguess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String guess =enterguess.getText().toString().trim();
                if(!guess.isEmpty()){
                    Intent intent =new Intent(ShowGuess.this,guesstext.class);
                    intent.putExtra("Guess",guess);
                    startActivityForResult(intent,REQUEST_CODE);
                }else {
                    Toast.makeText(ShowGuess.this,"please enter something",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_OK) {
                assert data != null;
                String message = data.getStringExtra("message_back");
                Toast.makeText(ShowGuess.this, message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}