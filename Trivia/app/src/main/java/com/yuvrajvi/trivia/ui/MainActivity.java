package com.yuvrajvi.trivia.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuvrajvi.trivia.R;
import com.yuvrajvi.trivia.data.AnswerListAsyncResponse;
import com.yuvrajvi.trivia.data.QuestionBank;
import com.yuvrajvi.trivia.model.Question;
import com.yuvrajvi.trivia.model.Score;
import com.yuvrajvi.trivia.util.prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MESSAGE_ID ="highestscore" ;
    private TextView questiontextview,questioncounter,currentscore,highestscore;
    private Button truebutton,falsebutton,share;
    private ImageView nextbutton,previousbutton;
    private int currentquestionindex=0;
    private double CurrentScore=0;
    private List<Question> questionList;
    private Score score;
    private prefs pref;
    private Double highscore;
    private Button startnewgame;
    private int[] ans;
    private int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score=new Score();
        pref=new prefs(MainActivity.this);
        currentquestionindex=pref.getindex();
        score.setScore(pref.getcurrentscore());
        CurrentScore=score.getScore();



        nextbutton=findViewById(R.id.next_button);
        previousbutton=findViewById(R.id.prev_button);
        truebutton=findViewById(R.id.True_answer);
        falsebutton=findViewById(R.id.False_answer);
        questioncounter=findViewById(R.id.counter_text);
        questiontextview=findViewById(R.id.questiontextview);
        currentscore=findViewById(R.id.currentscore);
        highestscore=findViewById(R.id.highestscore);
        startnewgame=findViewById(R.id.startnewgame);
        share=findViewById(R.id.share);
        updatescore();
        updatehighscore();

        //Log.d("HIGH", "onCreate: "+HighestScore);
        nextbutton.setOnClickListener(this);
        previousbutton.setOnClickListener(this);
        truebutton.setOnClickListener(this);
        falsebutton.setOnClickListener(this);
        startnewgame.setOnClickListener(this);
        share.setOnClickListener(this);
        questionList=new QuestionBank().getQuestion(new AnswerListAsyncResponse() {
            @Override
            public void processfinished(ArrayList<Question> questionArrayList) {
                questioncounter.setText(MessageFormat.format("{0}/{1}", currentquestionindex + 1, questionArrayList.size()));
                questiontextview.setText(questionArrayList.get(currentquestionindex).getAnswer());
                n=questionArrayList.size()+1;
                ans=pref.getarray(n);
                //Log.d("ArrY", "processfinished: "+questionArrayList);
            }
        });

        //Log.d("okk", "onCreate: "+questionList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button:
                currentquestionindex=(currentquestionindex+1)%questionList.size();
                updatequestion();

                break;
            case R.id.prev_button:
                if(currentquestionindex==0){currentquestionindex=questionList.size();}
                currentquestionindex=currentquestionindex-1;
                updatequestion();
                break;
            case R.id.True_answer:
                if(ans[currentquestionindex]==1){Toast.makeText(MainActivity.this,"you already attempted",Toast.LENGTH_SHORT).show();break;}
                checkanswer(true);
                //////////////trick///////////
                updatescore();
                currentquestionindex=(currentquestionindex+1)%questionList.size();
                updatequestion();
                break;
            case R.id.False_answer:
                if(ans[currentquestionindex]==1){Toast.makeText(MainActivity.this,"you already attempted",Toast.LENGTH_SHORT).show();break;}
                checkanswer(false);
                updatescore();
                //////////////trick///////////
                currentquestionindex=(currentquestionindex+1)%questionList.size();
                updatequestion();
                break;

            case R.id.startnewgame:
                resetprefs();
                break;

            case R.id.share:
                shareexp();
                break;
        }
    }

    private void shareexp() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"This game is good");
        intent.putExtra(Intent.EXTRA_TEXT,"High Score: "+pref.getHighScore()+
                "Current Score: "+score.getScore()+"Current Question: "
                +questionList.get(currentquestionindex).getAnswer());
        startActivity(intent);
    }

    private void resetprefs(){
        pref.saveindex(0);
        pref.savecurrentscore((double) 0);
        score.setScore(pref.getcurrentscore());
        updatescore();
        updateindex();
    }

    private void checkanswer(boolean ca){
        if(questionList.get(currentquestionindex).isAnswerTrue()==ca){
            fadeview();
            addpoints();
            //Toast.makeText(MainActivity.this,"right",Toast.LENGTH_SHORT).show();
        }else{
            shakeanimation();
            deductpoints();
            //Toast.makeText(MainActivity.this,"wrong",Toast.LENGTH_SHORT).show();
        }
        ans[currentquestionindex]=1;
    }
    private void addpoints(){
        CurrentScore=score.getScore();
        CurrentScore++;
        score.setScore(CurrentScore);
    }
    private void deductpoints(){
        CurrentScore=score.getScore();
        CurrentScore-=.25;
        score.setScore(CurrentScore);
    }

    private void updatescore(){
        currentscore.setText(MessageFormat.format("Score:{0}", String.valueOf(score.getScore())));
        //Log.d("lalala2", "updatehighscore: ");
    }
    private void updatehighscore(){
        highestscore.setText(MessageFormat.format("Score:{0}", String.valueOf(pref.getHighScore())));
        //Log.d("lalala2", "updatehighscore: ");
    }

    private void updateindex(){
        currentquestionindex=pref.getindex();
        updatequestion();
    }

    private void updatequestion(){
        questioncounter.setText(MessageFormat.format("{0}/{1}", currentquestionindex + 1, questionList.size()));
        questiontextview.setText(questionList.get(currentquestionindex).getAnswer());
    }

    private void fadeview(){
        final CardView cardView=findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(150);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.parseColor("#FFEB3B"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void shakeanimation(){
        Animation shake= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        final CardView cardView =findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.parseColor("#FFEB3B"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onPause() {
        pref.saveindex(currentquestionindex);
        pref.savecurrentscore(CurrentScore);
        pref.savearray(ans,questionList.size()+1);
        highscore=pref.getHighScore();
        if(CurrentScore>highscore) {
            pref.savehighscore(CurrentScore);
            updatehighscore();
        }
        super.onPause();
    }
}