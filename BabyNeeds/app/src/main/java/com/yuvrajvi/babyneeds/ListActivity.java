package com.yuvrajvi.babyneeds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yuvrajvi.babyneeds.data.databasehandler;
import com.yuvrajvi.babyneeds.model.Details;
import com.yuvrajvi.babyneeds.ui.itemshow;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivitytag";
    private RecyclerView recyclerView;
    private databasehandler db;
    private itemshow is;
    private List<Details> itemlist;
    private databasehandler dbh;
    private FloatingActionButton fab;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db=new databasehandler(this);

        recyclerView=findViewById(R.id.recyclerView);
        fab=findViewById(R.id.fab);

        dbh=new databasehandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemlist=new ArrayList<>();

        itemlist=dbh.getallvalues();

        for(Details item:itemlist){
            Log.d(TAG, "onCreate: "+item.getBabyItem());
        }

        is=new itemshow(this,itemlist);
        recyclerView.setAdapter(is);
        is.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createpopup();
            }
        });
    }

    private void createpopup() {
        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);
        babyItem=view.findViewById(R.id.babyItem);
        itemQuantity=view.findViewById(R.id.itemQuantity);
        itemSize=view.findViewById(R.id.itemSize);
        itemColor=view.findViewById(R.id.itemColor);
        saveButton=view.findViewById(R.id.saveButton);
        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!babyItem.getText().toString().isEmpty()
                        && !itemColor.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !itemSize.getText().toString().isEmpty()) {
                    saveItem();
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
                        alertDialog.dismiss();

                        //TODO:Recycler view,itemshow.java,contentshow.xml
                        startActivity(new Intent(ListActivity.this,ListActivity.class));
                        finish();
                    }
                },1200);

            }
        });
    }
    private void saveItem(){
        Details details=new Details();
        details.setBabyItem(String.valueOf(babyItem.getText()));
        details.setItemQuantity(Integer.valueOf(String.valueOf(itemQuantity.getText())));
        details.setItemColor(String.valueOf(itemColor.getText()));
        details.setItemSize(Integer.valueOf(String.valueOf(itemSize.getText())));
        //details.setDate_created();
        db.addvalues(details);
    }
}