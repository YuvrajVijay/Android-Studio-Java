package com.yuvrajvi.contactmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuvrajvi.contactmanager.adapter.RecyclerViewAdapter;
import com.yuvrajvi.contactmanager.data.databasehandler;
import com.yuvrajvi.contactmanager.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Contact> contactarraylist;
    private ArrayAdapter<String> arrayAdapter;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listView=findViewById(R.id.listview);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactarraylist=new ArrayList<>();

        databasehandler db=new databasehandler(MainActivity.this);

        List<Contact> contactList1=db.getallcontacts();

        for(Contact contact:contactList1){
            //db.deletecontect(contact);
            Log.d("MainActivity", "onCreate: "+contact.getId());
            contactarraylist.add(contact);
        }
        Log.d("Count", "onCreate: "+db.getcount());

        recyclerViewAdapter =new RecyclerViewAdapter(MainActivity.this,contactarraylist);

        recyclerView.setAdapter(recyclerViewAdapter);



//        Contact yuvraj=new Contact();
//        yuvraj.setName("yuvraj");
//        yuvraj.setPhonenumber("3456789");
//
//        db.addcontact(yuvraj);
//        Log.d("Count", "onCreate: "+db.getcount());
//        Contact contact1=db.getcontact(8);
//        contact1.setName("lala");
//        contact1.setPhonenumber("4567889");
//
//        Log.d("Count", "onCreate: "+db.getcount());
//        int updatedrow=db.updatecontact(contact1);
//        Log.d("MainActivity", "onCreate: "+contact1.getName()+" "+contact1.getPhonenumber());
//        Log.d("Row", "onCreate: "+updatedrow);
//
//
//        List<Contact> contactList=db.getallcontacts();
//
//        for(Contact contact:contactList){
//            Log.d("MainActivity", "onCreate: "+contact.getId());
//        }
//
//        db.deletecontect(contact1);
//        db.addcontact(new Contact("yuvraj","3456789"));
//        db.addcontact(new Contact("yuvrj","34789"));
//        db.addcontact(new Contact("yuvaj","345789"));
//        db.addcontact(new Contact("yvraj","34569"));
//        db.addcontact(new Contact("yuraj","345679"));
//        db.addcontact(new Contact("uvraj","345678"));
       // db.addcontact(new Contact("yuvr","6789"));


//        arrayAdapter=new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                contactarraylist
//        );
//
//        listView.setAdapter(arrayAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("List", "onItemClick: "+position);
//            }
//        });
    }
}