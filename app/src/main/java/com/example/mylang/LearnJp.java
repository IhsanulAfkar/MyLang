package com.example.mylang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LearnJp extends AppCompatActivity {

    private ListView listviewJp;
    private String [] lvNum = {"第０課", "第１課", "第２課"};
    private String [] lvNamearr = {"ひらがなひらがなの\n" +
            "よみかた。", "こんにちわ。わたしー", "おはよう！"};
    private String [] lvTopic = {"Pengenalan hiragana", "Perkenalan diri", "あいさつ"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_jp);
        listviewJp = findViewById(R.id.listviewJp);

        LvAdapter adapter = new LvAdapter();
        listviewJp.setAdapter(adapter);
    }

    public class LvAdapter extends BaseAdapter{

        @Override
        public int getCount(){
            return lvNamearr.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.cardlevel, viewGroup, false);
            TextView lvName = view.findViewById(R.id.lvName);
            lvName.setText(lvNamearr[i]);

            return view;
        }
    }
}