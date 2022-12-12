package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private SQLiteOpenHelper Opendb;
    private SQLiteDatabase mydb;
    public TextView username;
    public TextView password;
    public TextView confpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView txtLogin = (TextView) findViewById(R.id.txtLogin);
        Button btnRegist = (Button)findViewById(R.id.btnRegister);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        confpass = (TextView) findViewById(R.id.confpassword);
        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1){
            @Override
            public void onCreate(SQLiteDatabase db){ };
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){};
        };
        mydb = Opendb.getWritableDatabase();
        mydb.execSQL("CREATE TABLE IF NOT EXISTS user(username TEXT, password TEXT, level_jp TEXT, level_en TEXT, quiz_jp TEXT, quiz_en TEXT);");

        btnRegist.setOnClickListener(operasi);
        txtLogin.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.txtLogin:goLogin();break;
                case R.id.btnRegister:register();break;
            }
        }
    };

    void goLogin(){
        Intent move = new Intent(getBaseContext(), Login.class);
        startActivityForResult(move, 0);
    }
    void register(){
        String usr = username.getText().toString();
        String pass = password.getText().toString();
        String conf = confpass.getText().toString();

        if(!pass.equals(conf)){
            Toast.makeText(this, "Invalid password & confirmation password",Toast.LENGTH_LONG).show();
        } else {
            Cursor cur = mydb.rawQuery("SELECT * FROM user WHERE username='" + usr + "'", null);
            if (cur.getCount()>0){
                Toast.makeText(this, "Username already exist!",Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Success",Toast.LENGTH_LONG).show();
                ContentValues userdata = new ContentValues();

                userdata.put("username", username.getText().toString());
                userdata.put("password", pass);
                userdata.put("level_jp", "0");
                userdata.put("level_en", "0");
                userdata.put("quiz_jp", "0");
                userdata.put("quiz_en", "0");
                long status = mydb.insert("user",null, userdata);
                if (status == -1){
                    Toast.makeText(this, "User registration failed",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "User register success", Toast.LENGTH_LONG).show();
                    goLogin();
                }
            }
//
        }
    }
}