package com.example.szymon_laptop.quiz_szymon;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.szymon_laptop.quiz_szymon.dataBase.DbAdapter;
import com.example.szymon_laptop.quiz_szymon.model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.szymon_laptop.quiz_szymon.R.layout.activity_view_answers;

public class ViewAnswersActivity extends AppCompatActivity {

    private ListView lvQsAns;

    private List<Question> questionsList;
    private Question currentQuestion;

    ArrayList<HashMap<String, Object>> originalValues = new ArrayList<HashMap<String, Object>>();

    HashMap<String, Object> temp = new HashMap<String, Object>();

    public static String KEY_QUES = "questions";
    public static String KEY_CANS = "canswer";
    public static String KEY_YANS = "yanswer";

    private CustomAdapter adapter;

    ArrayList<String> myAnsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answers);
        onResume();

        Intent in = getIntent();
        myAnsList=in.getExtras().getStringArrayList("myAnswers");
        System.out.println(myAnsList.size());


        lvQsAns=(ListView)findViewById(R.id.lvAnswers);

        //Initialize the database
        final DbAdapter dbAdapter=new DbAdapter(this);
        questionsList= dbAdapter.getAllQuestions();

        for (int i = 0; i < questionsList.size(); i++) {
            currentQuestion=questionsList.get(i);
            temp = new HashMap<String, Object>();
            temp.put(KEY_QUES,  currentQuestion.getQuestion());
            temp.put(KEY_CANS, currentQuestion.getAnswer());
            temp.put(KEY_YANS, myAnsList.get(i));

            // add the row to the ArrayList
            originalValues.add(temp);

        }

        adapter = new CustomAdapter(ViewAnswersActivity.this, R.layout.list_row,
                originalValues);
        lvQsAns.setAdapter(adapter);
    }

    // define your custom adapter
    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {
        LayoutInflater inflater;

        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<HashMap<String, Object>> Strings) {
            super(context, textViewResourceId, Strings);
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // class for caching the views in a row
        private class ViewHolder {

            TextView tvQS, tvCans, tvYouans;

        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.list_row, null);
                viewHolder = new ViewHolder();

                viewHolder.tvQS = (TextView) convertView.findViewById(R.id.tvQuestions);

                viewHolder.tvCans = (TextView) convertView
                        .findViewById(R.id.tvCorrectAns);
                viewHolder.tvYouans = (TextView) convertView
                        .findViewById(R.id.tvYourAns);

                // link the cached views to the convertview
                convertView.setTag(viewHolder);

            } else
                viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.tvQS.setText(originalValues.get(position).get(KEY_QUES)
                    .toString());

            viewHolder.tvCans.setText("Correct Ans: "+originalValues.get(position).get(KEY_CANS)
                    .toString());
            viewHolder.tvYouans.setText("Your Ans: "+originalValues.get(position)
                    .get(KEY_YANS).toString());

            // return the view to be displayed
            return convertView;
        }
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
