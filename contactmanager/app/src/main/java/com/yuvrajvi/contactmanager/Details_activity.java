package com.yuvrajvi.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Details_activity extends AppCompatActivity {
    private TextView data_name,data_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_activity);
        data_name=findViewById(R.id.data_name);
        data_phone=findViewById(R.id.data_phone);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            String name=bundle.getString("name");
            String number=bundle.getString("number");

            data_name.setText(name);
            data_phone.setText(number);
        }
    }
}