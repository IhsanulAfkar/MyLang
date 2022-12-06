package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView card2 = (CardView) findViewById(R.id.card2);

        card2.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.card2:goLvEn();break;
            }
        }
    };

    void goLvEn(){
        Intent move = new Intent(getBaseContext(), LearnEn.class);
        startActivityForResult(move, 0);
    }
}