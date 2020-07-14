package com.example.myapplication3;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication3.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter myAdapter = BluetoothAdapter.getDefaultAdapter();
        //.out.println(btAdapter.getBondedDevices());

        BluetoothDevice hc05 = myAdapter.getRemoteDevice("00:19:10:09:0C:A7");
        //System.out.println(hc05.getName());

        BluetoothSocket btSocket = null;
        int counter = 0;
        do {
            try {
                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                //System.out.println(btSocket);
                btSocket.connect();
                //System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
        } while (!btSocket.isConnected() && counter < 3);


        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s1="";
        TextView txt;
        txt=(TextView)findViewById(R.id.id1);
        InputStream inputStream = null;
        try {
            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());
            int b=0;
            while(b!=35) {

                b = inputStream.read();
                System.out.println((char)b);
                if (b ==61) {
                    s1 = s1 + " ";
                } else {
                    if(b==35){continue;}
                    s1 = s1 + Character.toString((char)b);

                }
                txt.setText(s1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            btSocket.close();
            System.out.println(btSocket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}