package com.yuvrajvi.testquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button falseButton,showanswer;
    private Button trueButton;
    private ImageButton nextButton,prevButton;
    private TextView questionTextView;
    private int currentQuestionIndex=0;
    //FileInputStream fi;

    /*{
        try {
            fi = openFileInput("E:/you");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    Drawable butt=Drawable.createFromStream(fi,null);*/


    //private Question q=new Question(1,true);
    //**
    private Question[] questionBank=new Question[]{
        new Question(R.string.my_text_question,true),
        new Question(R.string.my_text_question1,true),
        new Question(R.string.my_text_question2,false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        nextButton=findViewById(R.id.next_button);
        prevButton=findViewById(R.id.prev_button);
        falseButton=findViewById(R.id.false_text);
        trueButton=findViewById(R.id.true_text);
        showanswer=findViewById(R.id.show_answer);

        questionTextView=findViewById(R.id.answer_text_view);
        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        showanswer.setOnClickListener(this);
        //nextButton.setBackground(butt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.false_text:
                cheakanswer(false);
                //Toast.makeText(MainActivity.this,"False",Toast.LENGTH_SHORT).show();
                break;

            case R.id.true_text:
                cheakanswer(true);
                //Toast.makeText(MainActivity.this,"True",Toast.LENGTH_SHORT).show();
                break;

            case R.id.next_button:
                currentQuestionIndex=(currentQuestionIndex+1)%questionBank.length;
                updateQuestion();
                break;

            case R.id.prev_button:
                if(currentQuestionIndex>0){currentQuestionIndex--;}
                else {currentQuestionIndex=questionBank.length-1;}
                updateQuestion();
                break;

            case R.id.show_answer:
                Intent intent =new Intent(MainActivity.this,ShowGuess.class);
                startActivity(intent);
        }
    }
    private void updateQuestion(){
        questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }
    private void cheakanswer(boolean userChooseCorrect){
        boolean answerIsTrue=questionBank[currentQuestionIndex].isAnsTrue();
        //string toastMessageId;
        //************toastMessageId is int becoz R.string.name gives a id(integer)
        int toastMessageId=0;
        if(userChooseCorrect==answerIsTrue){
            //Log.d("type", Integer.toString(toastMessageId));
            toastMessageId=R.string.correct_answer;
            //Log.d("type",Integer.toString(toastMessageId));
            //Toast.makeText(MainActivity.this,R.string.correct_answer,Toast.LENGTH_SHORT).show();
        }else{
            toastMessageId=R.string.wrong_answer;
        }

        Toast.makeText(MainActivity.this,toastMessageId,Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,toastMessageId,Toast.LENGTH_SHORT).show();

    }
}