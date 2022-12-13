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

public class LearnJp extends AppCompatActivity {

    private Translator tljp;
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
        TranslatorOptions opjp = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.JAPANESE)
                .build();

        tljp = Translation.getClient(opjp);

        DownloadConditions dc = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        tljp.downloadModelIfNeeded(dc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Fail to download model", Toast.LENGTH_LONG).show();
                    }
                });
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.lvJp0:goLvJp0();break;
                case R.id.btnBack:goBack();break;
//                case R.id.logo:goHome();break;
                case R.id.logo:translate();break;
            }
        }
    };
    void translate(){
        tljp.translate("China")
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Failed to translate" , Toast.LENGTH_LONG).show();
                    }
                });
    }
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