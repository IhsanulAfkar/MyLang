package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LearnJp0_2 extends AppCompatActivity {
    private SQLiteOpenHelper Opendb;
    private SQLiteDatabase mydb;
    SessionManager sm;
    HashMap userdata;

    EditText ans1,ans2,ans3,ans4,ans5,ans6,ans7,ans8;
    TextView q1,q2,q3,q4,q5,q6,q7,q8;
    int totquiz = question_jp.question.length;

    ImageView btnNext;
    int score = 0;
    int currquiz = 0;
    int quizlen = question_jp.question[currquiz].length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_jp02);
        sm = new SessionManager(getBaseContext());
        userdata = sm.getUserDetails();

        btnNext = (ImageView) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(op);

        q1 = (TextView) findViewById(R.id.q1);
        q2 = (TextView) findViewById(R.id.q2);
        q3 = (TextView) findViewById(R.id.q3);
        q4 = (TextView) findViewById(R.id.q4);
        q5 = (TextView) findViewById(R.id.q5);
        q6 = (TextView) findViewById(R.id.q6);
        q7 = (TextView) findViewById(R.id.q7);
        q8 = (TextView) findViewById(R.id.q8);

        ans1 = (EditText) findViewById(R.id.ans1);
        ans2 = (EditText) findViewById(R.id.ans2);
        ans3 = (EditText) findViewById(R.id.ans3);
        ans4 = (EditText) findViewById(R.id.ans4);
        ans5 = (EditText) findViewById(R.id.ans5);
        ans6 = (EditText) findViewById(R.id.ans6);
        ans7 = (EditText) findViewById(R.id.ans7);
        ans8 = (EditText) findViewById(R.id.ans8);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        btnBack.setOnClickListener(op);
        logo.setOnClickListener(op);
        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1){
            @Override
            public void onCreate(SQLiteDatabase db){ };
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){};
        };
        mydb = Opendb.getWritableDatabase();
        putquiz();

//        Toast.makeText(this, "Your score is: " + question_jp.ans[currquiz][0], Toast.LENGTH_LONG).show();
    }
    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnNext:quiz();break;
                case R.id.btnBack:goBack();break;
                case R.id.logo:goHome();break;
            }
        }
    };
    void quiz()
    {
        String ans[] = {
                ans1.getText().toString(),
                ans2.getText().toString(),
                ans3.getText().toString(),
                ans4.getText().toString(),
                ans5.getText().toString(),
                ans6.getText().toString(),
                ans7.getText().toString(),
                ans8.getText().toString(),
        };
        for (int a = 0; a < quizlen; a++){
            if(ans[a].equals(question_jp.ans[currquiz][a])){
                score++;
            }
        }
        if ((currquiz + 1) == totquiz){
            int totscore = quizlen * totquiz;
            Toast.makeText(this, "Your score is: " + score + "/" + totscore, Toast.LENGTH_LONG).show();

            ContentValues cv = new ContentValues();
            cv.put("quiz_jp", Integer.toString(score));
            cv.put("level_jp", "1");
            mydb.update("user", cv, "username = ?", new String[]{userdata.get("username").toString()});
            sm.updateLevelJp("1", Integer.toString(score));
            int sts = 0;
            if(score > 10){
                sts = 0;
            } else if(score > 5 && score <=10){
                sts = 1;
            } else {
                sts = 2;
            }
            sm.createTemp(Integer.toString(sts));
            Intent move = new Intent(getBaseContext(), LvFinish.class);
            startActivityForResult(move, 0);
        } else {
//            Toast.makeText(this, "Test: " + ans[0] + " " + currquiz , Toast.LENGTH_LONG).show();
            currquiz++;
            putquiz();
        }


    }
    void putquiz()
    {
        ans1.setText("");
        ans2.setText("");
        ans3.setText("");
        ans4.setText("");
        ans5.setText("");
        ans6.setText("");
        ans7.setText("");
        ans8.setText("");
        q1.setText(question_jp.question[currquiz][0]);
        q2.setText(question_jp.question[currquiz][1]);
        q3.setText(question_jp.question[currquiz][2]);
        q4.setText(question_jp.question[currquiz][3]);
        q5.setText(question_jp.question[currquiz][4]);
        q6.setText(question_jp.question[currquiz][5]);
        q7.setText(question_jp.question[currquiz][6]);
        q8.setText(question_jp.question[currquiz][7]);

    }
    void goBack(){
        finish();
//        Intent move = new Intent(getBaseContext(), Home.class);
//        startActivityForResult(move, 0);
    }

    void goHome(){
        Intent move = new Intent(getBaseContext(), Home.class);
        startActivityForResult(move, 0);
    }
}