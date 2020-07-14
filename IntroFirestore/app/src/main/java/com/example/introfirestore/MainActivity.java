package com.example.introfirestore;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    private EditText enterTitle,enterThoughts;
    private Button saveButton,showButton;
    private TextView recTitle,recThoughts;



    //Keys
    public static final String KEY_TITLE="title";
    public static final String KEY_THOUGHTS="thoughts";

    //connection to firestore

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference documentReference=db.collection("Journal")
            .document("First Thoughts");

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=enterTitle.getText().toString().trim();
                String thoughts=enterThoughts.getText().toString().trim();

                Map<String,Object> data=new HashMap<>();
                data.put(KEY_TITLE,title);
                data.put(KEY_THOUGHTS,thoughts);

                documentReference.set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess: ");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: "+e.toString());
                            }
                        });

            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> data=new HashMap<>();
                documentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    String title=documentSnapshot.getString(KEY_TITLE);
                                    String thoughts=documentSnapshot.getString(KEY_THOUGHTS);

                                    recTitle.setText(title);
                                    recThoughts.setText(thoughts);
                                }else {
                                    Toast.makeText(MainActivity.this,"no data exists",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: "+e.toString());
                            }
                        });
            }
        });
    }
}