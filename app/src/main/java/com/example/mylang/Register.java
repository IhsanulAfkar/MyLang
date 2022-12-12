package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView txtLogin = (TextView) findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.txtLogin:goLogin();break;
            }
        }
    };

    void goLogin(){
        Intent move = new Intent(getBaseContext(), Login.class);
        startActivityForResult(move, 0);
    }
}