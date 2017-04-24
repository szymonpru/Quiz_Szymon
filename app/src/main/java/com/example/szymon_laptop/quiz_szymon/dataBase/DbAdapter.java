package com.example.szymon_laptop.quiz_szymon.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.szymon_laptop.quiz_szymon.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon - Laptop on 12.04.2017.
 */

public class DbAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=7;
    private static final String DATABASE_NAME="SzymonQuiz";

    private static final String TABLE_NAME = "questions";

    private static final String KEY_ID ="id";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_OPTA = "optionA";
    private static final String KEY_OPTB = "optionB";
    private static final String KEY_OPTC = "optionC";
    private static final String KEY_OPTD = "optionD";

    private SQLiteDatabase myDataBase;

    public DbAdapter(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        myDataBase=sqLiteDatabase;

        String sql= "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUESTION
                + " TEXT, " + KEY_ANSWER + " TEXT, "+KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT, " + KEY_OPTD + " TEXT)";

        sqLiteDatabase.execSQL(sql);
        addQuestions();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addQuestions(){

        Question question1=new Question("What Does CPU stand for?","Central Processing Unit","Computer Processing Unit", "Central Power Unit","Computer Power Unit","Central Processing Unit");
        addQuestionToDataBase(question1);
        Question question2=new Question("How Many Kilo Bytes Are There In 1 Mb?","1024","10024","2048","1000","1024");
        addQuestionToDataBase(question2);
        Question question3=new Question("What is the purpose of a OS?","To control programs on the computer","To make your screen turn on", "To control programs on the computer","To be annoying","To make sure that your computer doesn't blow up");
        addQuestionToDataBase(question3);
        Question question4=new Question("What Is bigger?","10 terabytes","10 terabytes","100000 megabytes","1000000000000 bytes","1000 gigabytes");
        addQuestionToDataBase(question4);
        Question question5=new Question("What is one thing that must be compatible with everything else in the computer?","Motherboard","RAM", "Case","Motherboard","CPU");
        addQuestionToDataBase(question5);
    }

    public void addQuestionToDataBase(Question question){

        ContentValues cv=new ContentValues();

        cv.put(KEY_QUESTION, question.getQuestion());
        cv.put(KEY_ANSWER, question.getAnswer());
        cv.put(KEY_OPTA, question.getOptionA());
        cv.put(KEY_OPTB, question.getOptionB());
        cv.put(KEY_OPTC, question.getOptionC());
        cv.put(KEY_OPTD, question.getOptionD());

        myDataBase.insert(TABLE_NAME,null,cv);
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList=new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        myDataBase=this.getReadableDatabase();

        Cursor cursor=myDataBase.rawQuery(selectQuery,null);

        while(cursor.moveToNext()){
            Question quest=new Question();
            quest.setId(cursor.getInt(0));
            quest.setQuestion(cursor.getString(1));
            quest.setAnswer(cursor.getString(2));
            quest.setOptionA(cursor.getString(3));
            quest.setOptionB(cursor.getString(4));
            quest.setOptionC(cursor.getString(5));
            quest.setOptionD(cursor.getString(6));

            questionList.add(quest);
        }

    return questionList;
    }
}
