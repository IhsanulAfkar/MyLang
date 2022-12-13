package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LvFinish extends AppCompatActivity {
    public String mytext[] = {
            "Selamat!\nnAnda berhasil melewati level ini!\nnBerjuanglah di level selanjutnya",
            "Sayang sekali\nSedikit lagi Anda berhasil\nCoba lagi!",
            "Sayang sekali\nAnda masih harus banyak belajar\nCoba lagi!"
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_finish);

        TextView txtCongrats = (TextView) findViewById(R.id.txtCongrats);
        txtCongrats.setText(mytext[0]);
    }
}