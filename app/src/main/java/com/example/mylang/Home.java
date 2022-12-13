package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Home extends AppCompatActivity {
    SessionManager sessionManager;
    public int level = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap userdata = sessionManager.getUserDetails();

        CardView card2 = (CardView) findViewById(R.id.card2);
        CardView card3 = (CardView) findViewById(R.id.card3);
        Button btnCam = (Button) findViewById(R.id.btnCam);
        ImageButton btnProf = (ImageButton) findViewById(R.id.btnProf);
        TextView txt1 = (TextView)findViewById(R.id.txt1);
        TextView txt2 = (TextView)findViewById(R.id.txt2);
        TextView lvEn = (TextView)findViewById(R.id.lvEn);
        TextView lvJp = (TextView)findViewById(R.id.lvJp);

        txt1.setText("Selamat datang, "+userdata.get("username").toString());
        txt2.setText("Welcome, "+userdata.get("username").toString());
        lvEn.setText(userdata.get("level_en")+"/"+level);
        lvJp.setText(userdata.get("level_jp")+"/"+level);
//        Toast.makeText(this, userdata.get("username").toString(), Toast.LENGTH_LONG).show();
        card2.setOnClickListener(operasi);
        card3.setOnClickListener(operasi);
        btnCam.setOnClickListener(operasi);
        btnProf.setOnClickListener(operasi);

        Button btnTemp = (Button) findViewById(R.id.btnTemp);
        btnTemp.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.card2:goLvEn();break;
                case R.id.card3:goLvJp();break;
                case R.id.btnCam:goCam();break;
                case R.id.btnProf:goProf();break;
                case R.id.btnTemp:goTemp();break;
            }
        }
    };

    void goLvEn(){
        Intent move = new Intent(getBaseContext(), LearnEn.class);
        startActivityForResult(move, 0);
    }
    void goLvJp(){
        Intent move = new Intent(getBaseContext(), LearnJp.class);
        startActivityForResult(move, 0);
    }

    void goCam(){
        Intent move = new Intent(getBaseContext(), Camera.class);
        startActivityForResult(move, 0);
    }

    void goProf(){
        Intent move = new Intent(getBaseContext(), Profile.class);
        startActivityForResult(move, 0);
    }

    void goTemp(){
        Intent move = new Intent(getBaseContext(), LearnJp0_1.class);
        startActivityForResult(move, 0);
    }
}