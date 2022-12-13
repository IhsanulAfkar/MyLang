package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LearnEn extends AppCompatActivity {

    private ListView listviewEn;
    private String [] names = {"Alif", "Budi", "Rusa"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_en);

        CardView lvEn0 = (CardView) findViewById(R.id.lvEn0);
        lvEn0.setOnClickListener(operasi);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        btnBack.setOnClickListener(operasi);
        logo.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lvEn0:goLvEn0();break;
                case R.id.btnBack:goBack();break;
                case R.id.logo:goHome();break;
            }
        }
    };

    void goLvEn0(){
        Intent move = new Intent(getBaseContext(), LearnEn0_1.class);
        startActivityForResult(move, 0);
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