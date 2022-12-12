package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private SQLiteOpenHelper Opendb;
    private SQLiteDatabase mydb;
    private TextView username, password;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView txtRegis = (TextView) findViewById(R.id.txtRegis);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1){
            @Override
            public void onCreate(SQLiteDatabase db){ };
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){};
        };
        mydb = Opendb.getWritableDatabase();
        mydb.execSQL("CREATE TABLE IF NOT EXISTS user(username TEXT, password TEXT, level_jp TEXT, level_en TEXT, quiz_jp TEXT, quiz_en TEXT);");

        btnLogin.setOnClickListener(operasi);
        txtRegis.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnLogin:login();break;
                case R.id.txtRegis:goRegis();break;
            }
        }
    };
    void login(){
        String user = username.getText().toString();
        String pass = password.getText().toString();

        Cursor cur = mydb.rawQuery("SELECT * FROM user WHERE username='" + user + "'", null);
        if (cur.getCount()>0){
            cur.moveToFirst();
//            level.setText(cur.getString(cur.getColumnIndex("level")));
            String dbpass = cur.getString(cur.getColumnIndex("password"));
            if(!dbpass.equals(pass)){
                Toast.makeText(this, "Wrong password", Toast.LENGTH_LONG).show();
            } else {
//                success
                String username = cur.getString(cur.getColumnIndex("username"));
                String level_jp = cur.getString(cur.getColumnIndex("level_jp"));
                String level_en = cur.getString(cur.getColumnIndex("level_en"));
                String quiz_jp = cur.getString(cur.getColumnIndex("quiz_jp"));
                String quiz_en = cur.getString(cur.getColumnIndex("quiz_en"));
                sessionManager.createLoginSession(username, level_jp, level_en, quiz_jp, quiz_en);
                goHome();
            }
        } else {
            Toast.makeText(this, "Username not found", Toast.LENGTH_LONG).show();
        }

    }
    void goHome(){
        Intent move = new Intent(getBaseContext(), Home.class);
        startActivityForResult(move, 0);
    }

    void goRegis(){
        Intent move = new Intent(getBaseContext(), Register.class);
        startActivityForResult(move, 0);
    }
}