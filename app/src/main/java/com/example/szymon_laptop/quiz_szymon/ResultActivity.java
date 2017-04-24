package com.example.szymon_laptop.quiz_szymon;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.szymon_laptop.quiz_szymon.model.Question;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<String> answersList;
    private TextView tvFirstResultComment;
    private TextView tvSecondResultComment;
    private RatingBar ratingBar;
    private Button btryAgain;
    private Button banswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        onResume();

        tvFirstResultComment=(TextView)findViewById(R.id.tvFirstResultComment);
        tvSecondResultComment=(TextView)findViewById(R.id.tvSecondResultComment);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        btryAgain=(Button)findViewById(R.id.bTryAgain);
        banswers=(Button)findViewById(R.id.bAnswers);
        answersList=new ArrayList<>();

        Bundle b = getIntent().getExtras();
        int score= b.getInt("score");
        answersList=b.getStringArrayList("myAnswers");
        int questions=answersList.size();

        ratingBar.setRating(score);
        tvFirstResultComment.setText("You earned "+score+" out of "+questions+" points!");

        float percentage=(score*100)/questions;

        if (percentage>=80){
            tvSecondResultComment.setText("Score is Excellent !");
        }else if(percentage>=70 && percentage<=79){
            tvSecondResultComment.setText("Score is Best");
        }else if(percentage>=60 && percentage<=69){
            tvSecondResultComment.setText("Score is Good");
        }else if(percentage>=50 && percentage<=59){
            tvSecondResultComment.setText("Score is Average!");
        }else if(percentage>=33 && percentage<=49){
            tvSecondResultComment.setText("Score is  Below Average!");
        }else{
            tvSecondResultComment.setText("Score is Poor! You need to practice more!");
        }

        btryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResultActivity.this, QuestionsActivity.class);
                startActivity(intent);
            }
        });

        banswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResultActivity.this,ViewAnswersActivity.class);
                intent.putExtra("myAnswers",answersList);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
    }
}
