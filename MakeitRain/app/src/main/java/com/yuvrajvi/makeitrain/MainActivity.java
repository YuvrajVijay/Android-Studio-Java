package com.yuvrajvi.makeitrain;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    //private Button showMoney,showTag;

    private TextView moneytext;
    private int moneyCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moneytext=findViewById(R.id.money_text);

        //showMoney=findViewById(R.id.button_make_rain);
       // showTag=findViewById(R.id.showTag);

       /* showMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mytag", "onClick: Show Money");
            }
        });*/
    }
    public void showtag(View v){
        //Log.d("mytag", "onClick: Show Money");
        Toast.makeText(getApplicationContext(),R.string.app_name,Toast.LENGTH_SHORT).show();
    }
    public void makeitrain(View v){
        NumberFormat numberFormat =NumberFormat.getCurrencyInstance();

        moneyCounter+=1000;
        moneytext.setText(numberFormat.format(moneyCounter));
        /*if(moneyCounter>10000){
            moneytext.setTextColor(getResources().getColor(R.color.mycolor));
        }*/
        switch (moneyCounter){
            case 10000:
                moneytext.setTextColor(Color.BLUE);
                moneytext.setBackgroundColor(Color.WHITE);
                break;
            case 20000:
                moneytext.setTextColor(Color.YELLOW);
                moneytext.setBackgroundColor(Color.RED);
                break;
            case 40000:
                moneytext.setTextColor(Color.GREEN);
                moneytext.setBackgroundColor(Color.BLACK);
                break;
            default:
                break;

        }
        Log.d("mytag", "MakeItRain: tapped"+moneyCounter);
    }
}