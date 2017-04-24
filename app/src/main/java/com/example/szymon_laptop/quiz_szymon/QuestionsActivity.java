package com.example.szymon_laptop.quiz_szymon;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.szymon_laptop.quiz_szymon.dataBase.DbAdapter;
import com.example.szymon_laptop.quiz_szymon.model.Question;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon - Laptop on 12.04.2017.
 */

public class QuestionsActivity extends AppCompatActivity{

    private Question currentQuestion;
    private int currentQuestionID=0;

    private List<Question> questionList;

    private TextView tvQuestionNumer;
    private TextView tvQuestion;
    private RadioButton rbtnA, rbtnB, rbtnC, rbtnD;
    private RadioGroup radioGroup;
    private Button nextButton;

    private int score=0;
    private int answeredQuestions=0;

    ArrayList<String> myAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        onResume();

        tvQuestionNumer=(TextView)findViewById(R.id.tvQuestionNumber);
        tvQuestion=(TextView)findViewById(R.id.tvQuestion);
        radioGroup= (RadioGroup)findViewById(R.id.radioGroup);
        rbtnA=(RadioButton)findViewById(R.id.radioAnswerA);
        rbtnB=(RadioButton)findViewById(R.id.radioAnswerB);
        rbtnC=(RadioButton)findViewById(R.id.radioAnswerC);
        rbtnD=(RadioButton)findViewById(R.id.radioAnswerD);
        nextButton=(Button)findViewById(R.id.nextButton);
        myAnswers=new ArrayList<>();

        final DbAdapter dbAdapter=new DbAdapter(this);
        questionList=dbAdapter.getAllQuestions();
        currentQuestion=questionList.get(currentQuestionID);

        setQuestionsView();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton clientAnswer=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

                if(clientAnswer!=null){
                    myAnswers.add(""+ clientAnswer.getText());

                    if(clientAnswer.getText().equals(currentQuestion.getAnswer())){
                        score++;
                    }

                    if(currentQuestionID<questionList.size()){
                        currentQuestion=questionList.get(currentQuestionID);
                        setQuestionsView();
                    }
                    else{
                        Intent intent = new Intent(QuestionsActivity.this, ResultActivity.class);

                        Bundle b = new Bundle();
                        b.putInt("score", score);
                        b.putInt("totalQs", questionList.size());
                        b.putStringArrayList("myAnswers", myAnswers);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Toast.makeText(QuestionsActivity.this, "First, choose answer!",Toast.LENGTH_LONG).show();
                }
                //need to clear radio Buttons
                radioGroup.clearCheck();
            }
        });
    }

    private void setQuestionsView(){

        answeredQuestions=currentQuestionID+1;
        //need to clear answers
        rbtnA.setChecked(false);
        rbtnB.setChecked(false);
        rbtnC.setChecked(false);
        rbtnD.setChecked(false);

        tvQuestionNumer.setText("Question " + answeredQuestions + " of " + questionList.size());
        tvQuestion.setText(currentQuestion.getQuestion());
        rbtnA.setText(currentQuestion.getOptionA());
        rbtnB.setText(currentQuestion.getOptionB());
        rbtnC.setText(currentQuestion.getOptionC());
        rbtnD.setText(currentQuestion.getOptionD());

        currentQuestionID++;
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
