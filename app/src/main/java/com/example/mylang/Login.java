package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView txtRegis = (TextView) findViewById(R.id.txtRegis);

        btnLogin.setOnClickListener(operasi);
        txtRegis.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnLogin:goHome();break;
                case R.id.txtRegis:goRegis();break;
            }
        }
    };

    void goHome(){
        Intent move = new Intent(getBaseContext(), Home.class);
        startActivityForResult(move, 0);
    }

    void goRegis(){
        Intent move = new Intent(getBaseContext(), Register.class);
        startActivityForResult(move, 0);
    }
}