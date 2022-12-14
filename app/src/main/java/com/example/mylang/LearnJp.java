package com.example.mylang;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.HashMap;

public class LearnJp extends AppCompatActivity {
    SessionManager sessionManager;
    private Translator tljp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_jp);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap userdata = sessionManager.getUserDetails();

        CardView lvJp0 = (CardView) findViewById(R.id.lvJp0);
        lvJp0.setOnClickListener(operasi);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        btnBack.setOnClickListener(operasi);
        logo.setOnClickListener(operasi);

        TextView lvJp = (TextView) findViewById(R.id.lvJp);
        lvJp.setText(userdata.get("quiz_jp")+"/24 - Lv 0");
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lvJp0:goLvJp0();break;
                case R.id.btnBack:goBack();break;
                case R.id.logo:goHome();break;
//                case R.id.logo:translate();break;
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