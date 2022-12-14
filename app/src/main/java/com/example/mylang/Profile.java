package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    Button btnlogout;
    SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sm = new SessionManager(getBaseContext());
        HashMap userdata = sm.getUserDetails();
        btnlogout = (Button) findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(op);

        TextView txtUname = (TextView) findViewById(R.id.txtUname);
        txtUname.setText(userdata.get("username").toString());
    }
    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnLogout:logout();break;
            }
        }
    };
    void logout(){
        sm.logoutUser();
//        Intent move = new Intent(getBaseContext(), Login.class);
//        startActivityForResult(move, 0);
    }
}