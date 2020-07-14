package com.example.socialdis;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    //MediaPlayer mediaPlayer;
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String MESSAAGE_ID = "messages_pref";
    ListView listView,paired;
    BluetoothAdapter mybluetoothadapter;

    Intent btenableingintent;
    int requestcodeforenable;
    int facecount=0;
    int cc=0;
    int takedata=0,takedataclicked=0;
    int heartavg=0;
    float tempavg=0.0f;
    //ArrayList<Integer> heartavg=new ArrayList<>();
    //ArrayList<Float> tempavg=new ArrayList<>();

    boolean repeat = true;


    //String[] sensordata=new String[4];
    ArrayList<String> sensordata = new ArrayList<String>();
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayList<String> stringArrayList1 = new ArrayList<String>();
    ArrayList<String> stringArrayListpaired = new ArrayList<String>();
    ArrayList<String> stringArrayListpaired1 = new ArrayList<String>();
    ArrayList<datavalue> values=new ArrayList<datavalue>();
    ArrayAdapter<String> adapter;
    datavalueadapter adapter1;
    BluetoothAdapter myAdapter = BluetoothAdapter.getDefaultAdapter();

    BluetoothSocket btSocket = null;

    int heart,mpugyro,pir;
    float temp;
    TextView txt,txt1,txt2,txt3;
    Button b1,hrbt;

    int pre1=0,pre2=0,pre3=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //startService(new Intent(MainActivity.this,MyService.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(TAG, "onCreate: ");
        listView = (ListView) findViewById(R.id.availabledeviceslist);
        paired = (ListView) findViewById(R.id.paireddeviceslist);
        //listView2=(ListView)findViewById(R.id.listView2);
        //buttonon = (Button) findViewById(R.id.BTON);
        //buttonoff = (Button) findViewById(R.id.BTOFF);
        //scandevices = (Button) findViewById(R.id.scandevices);
        //paireddevices = (Button) findViewById(R.id.paireddevices);
        txt=(TextView)findViewById(R.id.heartrate);
        txt1=(TextView)findViewById(R.id.bodytemp);
        txt2=(TextView)findViewById(R.id.handcheacker);
        txt3=(TextView)findViewById(R.id.distancecheaker);
        b1=(Button)findViewById(R.id.caliberate);
        hrbt=(Button)findViewById(R.id.hrbt);
        //b2=(Button)findViewById(R.id.button2);



        getpermissions();

        mybluetoothadapter = BluetoothAdapter.getDefaultAdapter();
        btenableingintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestcodeforenable = 1;

        if (mybluetoothadapter == null) {
            Toast.makeText(getApplicationContext(), "Bluettoth does not support", Toast.LENGTH_LONG).show();
        } else {
            if (!mybluetoothadapter.isEnabled()) {
                startActivityForResult(btenableingintent, requestcodeforenable);
            }
        }

        //addNotification("Wash Your Hands","Stay clean Stay safe","my_channel_id_05",5);
        //addNotification("Cheak Heart Rate and Body Temperature","Tap Get HeartRate BodyTemp Button","my_channel_id_04",4);
        //System.out.println(hc05.getName());


        //addNotification("Wash Your Hands","Stay clean Stay safe");
        washyourhands();

        //try to connect to bluetooth device
        BluetoothDevice hc05 = myAdapter.getRemoteDevice("00:19:10:09:0C:A7");
        //Log.d("address", "onCreate: "+hc05);
        connecting(hc05);

        //initializing
        /*try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(38);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        exebutton();
        listViewlistener();
        scanbutton();
        hrbtlistener();
        caliberate();

        //normalbutton();
        justifyListViewHeightBasedOnChildren(listView);
        justifyListViewHeightBasedOnChildren(paired);



    }

    private void hrbtlistener(){
        hrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification("Calculating Heart rate and Body Temperature","Place the sensors correctly","my_channel_id_06",6);
                try {
                    OutputStream outputStream = btSocket.getOutputStream();
                    outputStream.write(58);
                    takedataclicked=1;
                    Log.d("hrbt", "onClick: "+"yes");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("hrbt", "onClick: "+"no");
                }
            }
        });
    }

    private void listViewlistener(){
        paired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),position+"yup "+stringArrayList.get(position)+stringArrayList1.get(position),Toast.LENGTH_SHORT).show();
                Log.d("address", "onItemClick: "+stringArrayListpaired.get(position));
                if(!btSocket.isConnected()) {
                    BluetoothDevice hc05 = myAdapter.getRemoteDevice(stringArrayListpaired.get(position));
                    Log.d("address", "onCreate: " + hc05);
                    connecting(hc05);
                }
            }
        });
    }
    private void connecting(BluetoothDevice hc05){
        int counter = 0;
        do {
            try {
                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                Log.d("address", "connecting: "+btSocket+hc05);
                //System.out.println(btSocket);
                btSocket.connect();
                //System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                Log.d("address", "connecting: "+"N0");
                e.printStackTrace();
            }
            counter++;
        } while (!btSocket.isConnected() && counter < 1);

    }
    private void washyourhands(){
        Timer timerAsync1 = new Timer();
        TimerTask timerTaskAsync1 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        addNotification("Wash Your Hands","Stay clean Stay safe","my_channel_id_05",5);
                        addNotification("Cheak Heart Rate and Body Temperature","Tap Get HeartRate BodyTemp Button","my_channel_id_04",4);
                        cc++;
                        takedata=1;
                    }
                });
            }
        };
        timerAsync1.schedule(timerTaskAsync1, 0, 1800000);
    }

    private static void justifyListViewHeightBasedOnChildren (ListView listView1) {

        ListAdapter adapter = listView1.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView1;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView1.getLayoutParams();
        par.height = totalHeight + (listView1.getDividerHeight() * (adapter.getCount() - 1));
        listView1.setLayoutParams(par);
        listView1.requestLayout();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getpermissions(){
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requestcodeforenable) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluettoth is enabled", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluettoth enableing canceled", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void exebutton() {

                Set<BluetoothDevice> bt = mybluetoothadapter.getBondedDevices();
                String[] strings = new String[bt.size()];
                //String[] strings1 = new String[bt.size()];
                int index = 0;
                stringArrayListpaired.clear();
                stringArrayListpaired1.clear();
                if (bt.size() > 0) {
                    for (BluetoothDevice device : bt) {
                        strings[index] = device.getName();
                        stringArrayListpaired1.add(device.getName());
                        stringArrayListpaired.add(device.getAddress());
                        //strings1[index]=device.getAddress();
                        Log.d("address", strings[index]+"exebutton: "+stringArrayListpaired.get(index));
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    paired.setAdapter(arrayAdapter);


                }
            }

    private void scanbutton() {
                start();
                //listView.setAdapter(null);
            }


    private void start() {

        //adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
        //listView.setAdapter(adapter);
        myAdapter.startDiscovery();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(myReceiver, intentFilter);
        adapter1=new datavalueadapter(this,R.layout.adapter_view_layout,values);
        listView.setAdapter(adapter1);



        Timer timerAsync = new Timer();
        TimerTask timerTaskAsync = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        getdata();
                        myAdapter.startDiscovery();
                        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        registerReceiver(myReceiver, intentFilter);
                        Log.d("repeat","after each 10 sec");
                        //call web service here to repeat
                    }
                });
            }
        };

        timerAsync.schedule(timerTaskAsync, 0, 1000);

    }

    public void getdata(){

        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s1="";

        InputStream inputStream = null;
        try {
            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());
            int b=0;
            int k=0;
            while(b!=35) {

                b = inputStream.read();
                System.out.println((char)b);
                if (b ==61) {
                    sensordata.add(s1);
                    k++;
                    s1="";

                } else {
                    if(b==35){continue;}
                    s1 = s1 + Character.toString((char)b);

                }
                //txt.setText(s1);
            }
            heart=Integer.valueOf(sensordata.get(0));
            txt.setText("Heart Rate:  "+sensordata.get(0));
            temp=Float.valueOf(sensordata.get(1));
            txt1.setText("Body Temperature:  "+sensordata.get(1));
            mpugyro=Integer.valueOf(sensordata.get(2));
            if(mpugyro==1){
                if(pre1==0){pre1=1;}
                else if(pre2==0){pre2=1;}
                //else if(pre3==0){pre3=1;}
            }
            else{pre3=0;pre2=0;pre1=0;}
            pir=Integer.valueOf(sensordata.get(3));
            Log.d("datadata", "getdata: "+heart+" "+temp);
            Log.d("heartavg", "getdata: "+heartavg);
            //heartavg+=heart;
            if(takedata==1&&takedataclicked==1){
                SharedPreferences getsharedata=getSharedPreferences(MESSAAGE_ID,MODE_PRIVATE);
                heartavg=getsharedata.getInt("heartavg",0);
                tempavg=getsharedata.getFloat("tempavg",0.0f);

                //heartavg.add(heart);tempavg.add(temp);
                heartavg+=heart;
                tempavg+=temp;
                SharedPreferences sharedPreferences=getSharedPreferences(MESSAAGE_ID,MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putInt("heartavg",heartavg);
                editor.putFloat("tempavg",tempavg);
                editor.apply();
                if(cc==20){
                    heartavg=heartavg/20;tempavg=tempavg/20;
                    //TODO:Send value of heartavg and tempavg to server
                    cc=0;//heartavg.clear();tempavg.clear();
                    heartavg=0;tempavg=0;
                    editor.putInt("heartavg",heartavg);
                    editor.putFloat("tempavg",tempavg);
                    editor.apply();
                }
                takedata=0;takedataclicked=0;
            }
            sensordata.clear();
            if((mpugyro==1&&pir==1)||(pre1==1&&pre2==1)){
                facecount=0;
                txt2.setText("Don't Touch Your Face");
                addNotification("Don't Touch Your Face","Touching face can cause diseases to spread","my_channel_id_01",1);
            }
            else{
                facecount++;
                if(facecount>4){
                    txt2.setText("Everything is fine");
                    facecount=0;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        /*try {
            btSocket.close();
            System.out.println(btSocket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = 1;
            int y=0;
            String b = null;
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!stringArrayList.contains(device.getName())&&!stringArrayListpaired1.contains(device.getName())) {
                    stringArrayList.add(device.getName());
                    stringArrayList1.add(device.getAddress());
                    Log.d("address", "onReceive: "+stringArrayList1);
                    a=0;
                }

                int rssi = intent.getShortExtra(device.EXTRA_RSSI, Short.MIN_VALUE);

                if (rssi > -65) {
                    b="Near";
                    y++;
                }
                if(rssi <= -65){
                    b="Far";
                }
                if(a==0){
                    datavalue x=new datavalue(device.getName(),"RSSI : "+Integer.toString(rssi),b);
                    values.add(x);
                    adapter1.notifyDataSetChanged();
                }
                if(a==1){
                    stringArrayList.clear();
                    stringArrayList1.clear();
                    stringArrayList.add(device.getName());
                    stringArrayList1.add(device.getAddress());
                    values.clear();
                    datavalue x=new datavalue(device.getName(),"RSSI : "+Integer.toString(rssi),b);
                    values.add(x);
                    adapter1.notifyDataSetChanged();
                }
                if(y>0){
                    txt3.setText("Maintain Social Distance");
                    addNotification("Maintain Social Distance","Someone is near you","my_channel_id_02",2);
                }
                if(y==0){
                    txt3.setText("Everything is fine");
                }
            }
        }

    };
    /*public void normalbutton(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
            }
        });

    }*/
    private void caliberate(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification("Initializing and Caliberating","Put your hand down for caliberation","my_channel_id_03",3);
                try {
                    OutputStream outputStream = btSocket.getOutputStream();
                    outputStream.write(38);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }});
        }

    private void addNotification(String title,String text,String channelid,int id) {
        // Builds your notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = channelid;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                //.setDefaults(Notification.DEFAULT_ALL)
                //.setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setTicker("Hearty365")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentTitle(title)
                .setContentText(text)
                .setContentInfo("Info");

        notificationManager.notify(/*notification id*/id, notificationBuilder.build());
    }

}
