package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LearnJp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_jp);

        CardView lvJp0 = (CardView) findViewById(R.id.lvJp0);
        lvJp0.setOnClickListener(operasi);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        btnBack.setOnClickListener(operasi);
        logo.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lvJp0:goLvJp0();break;
                case R.id.btnBack:goBack();break;
                case R.id.logo:goHome();break;
            }
        }
    };

    void goLvJp0(){
        Intent move = new Intent(getBaseContext(), LearnJp0_1.class);
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