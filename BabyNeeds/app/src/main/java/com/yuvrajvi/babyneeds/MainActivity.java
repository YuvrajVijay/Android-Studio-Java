package com.yuvrajvi.babyneeds;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yuvrajvi.babyneeds.data.databasehandler;
import com.yuvrajvi.babyneeds.model.Details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;
    private databasehandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        db=new databasehandler(MainActivity.this);
        byPassActivity();

        FloatingActionButton fab = findViewById(R.id.fab);

        List<Details> contactList1=db.getallvalues();

        for(Details contact:contactList1){
            //db.deletecontect(contact);
            Log.d("MainActivity1", "onCreate: "+contact.getDate_created());
        }
        //db.delete();
        Log.d("MainActivity1", "onClick: "+"end");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //Log.d("lalala", "onClick: "+view);
            }


        });
    }

    private void byPassActivity() {
        if(db.getcount()>0){
          startActivity(new Intent(MainActivity.this,ListActivity.class));
          finish();
        }
    }

    private void createPopupDialog() {
        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);
        babyItem=view.findViewById(R.id.babyItem);
        itemQuantity=view.findViewById(R.id.itemQuantity);
        itemSize=view.findViewById(R.id.itemSize);
        itemColor=view.findViewById(R.id.itemColor);
        saveButton=view.findViewById(R.id.saveButton);
        builder.setView(view);
        dialog=builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!babyItem.getText().toString().isEmpty()
                        && !itemColor.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !itemSize.getText().toString().isEmpty()) {
                    saveitem();
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
                List<Details> contactList1=db.getallvalues();

                for(Details contact:contactList1){
                    //db.deletecontect(contact);
                    Log.d("MainActivity1", "onCreate: "+contact.getDate_created());
                }
                Log.d("MainActivity1", "onClick: "+"end"+db.getcount());
                Log.d("Count", "onCreate: "+db.getcount());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();

                        //TODO:Recycler view,itemshow.java,contentshow.xml
                        startActivity(new Intent(MainActivity.this,ListActivity.class));
                    }
                },1200);

            }
        });

    }

    private void saveitem(){
        Details details=new Details();
        details.setBabyItem(String.valueOf(babyItem.getText()));
        details.setItemQuantity(Integer.valueOf(String.valueOf(itemQuantity.getText())));
        details.setItemColor(String.valueOf(itemColor.getText()));
        details.setItemSize(Integer.valueOf(String.valueOf(itemSize.getText())));
        //details.setDate_created();
        db.addvalues(details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            Toast.makeText(getApplicationContext(),"yupp",Toast.LENGTH_SHORT).show();
//            Snackbar.make(, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}