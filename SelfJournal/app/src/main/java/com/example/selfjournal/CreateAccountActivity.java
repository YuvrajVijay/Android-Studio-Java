package com.example.selfjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.JournalApi;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currrentUser;


    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("users");

    private Button createAcctButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);


        firebaseAuth=FirebaseAuth.getInstance();

        createAcctButton=findViewById(R.id.create_acc_button);
        emailEditText=findViewById(R.id.email_account);
        passwordEditText=findViewById(R.id.password_account);
        userNameEditText=findViewById(R.id.username_account);
        progressBar=findViewById(R.id.create_acct_progress);

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currrentUser=firebaseAuth.getCurrentUser();
                if(currrentUser!=null){
                    //user is already logged in
                }else{
                    //user not logged in
                }
            }
        };

        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= emailEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();
                String username=userNameEditText.getText().toString().trim();

                createUserEmailAccount(email,password,username);
            }
        });
    }
    private void createUserEmailAccount(String email, String password, final String username){
        if(!TextUtils.isEmpty(email)&&
        !TextUtils.isEmpty(password)&&
        !TextUtils.isEmpty(username)){
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        currrentUser=firebaseAuth.getCurrentUser();
                        assert currrentUser != null;
                        final String currentUserId=currrentUser.getUid();

                        Map<String,String> userObj=new HashMap<>();
                        userObj.put("userId",currentUserId);
                        userObj.put("username",username);

                        collectionReference.add(userObj)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        documentReference.get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if(Objects.requireNonNull(task.getResult()).exists()){
                                                            progressBar.setVisibility(View.INVISIBLE);

                                                            //can use username given in this function directly
                                                            String name=task.getResult().getString("username");

                                                            JournalApi journalApi=JournalApi.getInstance();
                                                            journalApi.setUserId(currentUserId);
                                                            journalApi.setUsername(name);

                                                            Intent intent=new Intent(CreateAccountActivity.this,
                                                                    PostJournalActivity.class);
//                                                            intent.putExtra("username",name);
//                                                            intent.putExtra("userId",currentUserId);
                                                            startActivity(intent);
                                                        }else{
                                                            progressBar.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CreateAccountActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateAccountActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(CreateAccountActivity.this,"Empty Fields not allowed",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        currrentUser=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}