package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LearnEn0_2 extends AppCompatActivity implements View.OnClickListener {
    private SQLiteOpenHelper Opendb;
    private SQLiteDatabase mydb;
    SessionManager sm;
    HashMap userdata;

    TextView questView;
    Button ansA, ansB, ansC, ansD;
    Button btnNext;

    int score = 0;
    int totquest = question_en.question.length;
    int currquest = 0;
    String selectedAns = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_en02);

        questView = findViewById(R.id.question);
        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);
        btnNext = findViewById(R.id.btnNext);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        quiz();

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        btnBack.setOnClickListener(operasi);
        logo.setOnClickListener(operasi);
        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1){
            @Override
            public void onCreate(SQLiteDatabase db){ };
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){};
        };
        mydb = Opendb.getWritableDatabase();
        sm = new SessionManager(getBaseContext());
        userdata = sm.getUserDetails();
    }
    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnBack:goBack();break;
                case R.id.logo:goHome();break;
            }
        }
    };
    void goBack(){
        finish();
//        Intent move = new Intent(getBaseContext(), Home.class);
//        startActivityForResult(move, 0);
    }

    void goHome(){
        Intent move = new Intent(getBaseContext(), Home.class);
        startActivityForResult(move, 0);
    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.GRAY);
        ansB.setBackgroundColor(Color.GRAY);
        ansC.setBackgroundColor(Color.GRAY);
        ansD.setBackgroundColor(Color.GRAY);

        Button clickedBtn = (Button) view;
        if (clickedBtn.getId()==R.id.btnNext){
            if(selectedAns.equals(question_en.correctAns[currquest])){
                score++;
            }
            currquest++;
            quiz();
        } else {
            selectedAns = clickedBtn.getText().toString();
            clickedBtn.setBackgroundColor(Color.CYAN);
        }
    }
    void quiz(){
        if(currquest == totquest){
            endquix();
            return;
        }

        questView.setText(question_en.question[currquest]);
        ansA.setText(question_en.choice[currquest][0]);
        ansB.setText(question_en.choice[currquest][1]);
        ansC.setText(question_en.choice[currquest][2]);
        ansD.setText(question_en.choice[currquest][3]);
    }

    void endquix(){
        Toast.makeText(this, "hewwo scorenya " + score, Toast.LENGTH_SHORT).show();
        ContentValues cv = new ContentValues();
        cv.put("quiz_en", Integer.toString(score));
        cv.put("level_en", "1");

        mydb.update("user", cv, "username = ?", new String[]{userdata.get("username").toString()});
        Toast.makeText(getBaseContext(), "test", Toast.LENGTH_LONG).show();
        sm.updateLevelEn("1", Integer.toString(score));
        int sts = 0;
        if(score > 7){
            sts = 0;
        } else if(score > 3 && score <=7){
            sts = 1;
        } else {
            sts = 2;
        }
        sm.createTemp(Integer.toString(sts));
        Intent move = new Intent(getBaseContext(), LvFinish.class);
        startActivityForResult(move, 0);

    }
}