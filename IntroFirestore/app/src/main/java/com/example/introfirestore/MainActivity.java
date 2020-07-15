package com.example.introfirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="MainActivity" ;
    private EditText enterTitle,enterThoughts;
    private Button saveButton,showButton,updateButton,deleteButton,deleteAll;
    private TextView recTitle,recThoughts;



    //Keys
    public static final String KEY_TITLE="title";
    public static final String KEY_THOUGHTS="thoughts";

    //connection to firestore

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference documentReference=db.collection("Journal")
            .document("First Thoughts");
    private CollectionReference collectionReference=db.collection("Journal");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterTitle=findViewById(R.id.edit_text_title);
        enterThoughts=findViewById(R.id.edit_text_thoughts);
        saveButton=findViewById(R.id.save_button);
        recTitle=findViewById(R.id.rec_title);
        recThoughts=findViewById(R.id.rec_thoughts);
        showButton=findViewById(R.id.show_button);
        updateButton=findViewById(R.id.update_button);
        deleteButton=findViewById(R.id.delete_button);
        deleteAll=findViewById(R.id.delete_all);

        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        deleteAll.setOnClickListener(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_data_on_new_field();
//                String title=enterTitle.getText().toString().trim();
//                String thoughts=enterThoughts.getText().toString().trim();
//
//                Journal journal=new Journal();
//                journal.setTitle(title);
//                journal.setThoughts(thoughts);
//
////                Map<String,Object> data=new HashMap<>();
////                data.put(KEY_TITLE,title);
////                data.put(KEY_THOUGHTS,thoughts);
//
//                documentReference.set(journal)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, "onSuccess: ");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d(TAG, "onFailure: "+e.toString());
//                            }
//                        });

            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Map<String,Object> data=new HashMap<>();
                showAllData();
//                documentReference.get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                if(documentSnapshot.exists()){
//                                    Journal journal =documentSnapshot.toObject(Journal.class);
////                                    String title=documentSnapshot.getString(KEY_TITLE);
////                                    String thoughts=documentSnapshot.getString(KEY_THOUGHTS);
//
//                                    if (journal != null) {
//                                        recTitle.setText(journal.getTitle());
//                                        recThoughts.setText(journal.getThoughts());
//                                    }
//                                }else {
//                                    Toast.makeText(MainActivity.this,"no data exists",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d(TAG, "onFailure: "+e.toString());
//                            }
//                        });
            }
        });
    }

    private void showAllData() {
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data="";
                        for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                            Journal journal=snapshot.toObject(Journal.class);
                            data+="Title: "+journal.getTitle()+"\n"
                            +"Thoughts: "+journal.getThoughts()+"\n";


                            //recThoughts.setText(journal.getThoughts());
                            //Log.d(TAG, "onSuccess: "+snapshot.getString(KEY_TITLE));
                        }
                        recTitle.setText(data);
                    }
                });
    }

    private void save_data_on_new_field() {
        String title=enterTitle.getText().toString().trim();
        String thoughts=enterThoughts.getText().toString().trim();

        Journal journal=new Journal();
        journal.setTitle(title);
        journal.setThoughts(thoughts);

        collectionReference.add(journal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(MainActivity.this,"something went wrong",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Journal journal=documentSnapshot.toObject(Journal.class);

//                    String title=documentSnapshot.getString(KEY_TITLE);
//                    String thoughts=documentSnapshot.getString(KEY_THOUGHTS);

                    if (journal != null) {
                        recTitle.setText(journal.getTitle());
                        recThoughts.setText(journal.getThoughts());
                    }

                }else {
                    recTitle.setText("");
                    recThoughts.setText("");
                }

            }
        });

        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                collectionReference.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                String data="";
                                for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                                    Journal journal=snapshot.toObject(Journal.class);
                                    data+="Title: "+journal.getTitle()+"\n"
                                            +"Thoughts: "+journal.getThoughts()+"\n";


                                    //recThoughts.setText(journal.getThoughts());
                                    //Log.d(TAG, "onSuccess: "+snapshot.getString(KEY_TITLE));
                                }
                                recTitle.setText(data);
                            }
                        });
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_button:
                updateMyData();
                break;
            case R.id.delete_button:
                deleteThoughts();
                break;
            case R.id.delete_all:
                deletealldata();
                break;
        }
    }

    private void deletealldata() {
        documentReference.delete();
    }

    private void deleteThoughts() {
        documentReference.update(KEY_THOUGHTS, FieldValue.delete());
    }

    private void updateMyData() {
        String title=enterTitle.getText().toString().trim();
        String thoughts=enterThoughts.getText().toString().trim();


//        Journal journal=new Journal();
//        journal.setTitle(title);
//        journal.setThoughts(thoughts);
        Map<String,Object> data=new HashMap<>();
        data.put(KEY_TITLE,title);
        data.put(KEY_THOUGHTS,thoughts);

        documentReference.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Can't update",Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
}