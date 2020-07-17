package com.example.selfjournal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.AutoText;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import Model.Journal;
import util.JournalApi;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button createAcctButton;
    private AutoCompleteTextView email;
    private EditText password;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();

        loginButton=findViewById(R.id.email_sign_in_button);
        createAcctButton=findViewById(R.id.create_acc_button_login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmialPasswordUser(email.getText().toString().trim(),
                        password.getText().toString().trim());
            }
        });
        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
            }
        });

    }

    private void loginEmialPasswordUser(String email, String password) {
        if(!TextUtils.isEmpty(email)&&
        !TextUtils.isEmpty(password)){

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            assert user != null;
                            String currentUserId=user.getUid();

                            collectionReference
                                    .whereEqualTo("userId",currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if(error!=null){
                                            }
                                            assert value != null;
                                            if(!value.isEmpty()){
                                                for(QueryDocumentSnapshot snapshot: value){
                                                    JournalApi journalApi=JournalApi.getInstance();
                                                    journalApi.setUsername(snapshot.getString("username"));
                                                    journalApi.setUserId(snapshot.getString("userId"));

                                                    //Go to ListActivity
                                                    startActivity(new Intent(LoginActivity.this,
                                                            PostJournalActivity.class));
                                                    //finish();
                                                }

                                            }
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }else {
            Toast.makeText(LoginActivity.this,
                    "Fields Can't be empty",Toast.LENGTH_SHORT).show();
        }
    }

}