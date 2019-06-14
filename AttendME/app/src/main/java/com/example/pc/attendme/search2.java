package com.example.pc.attendme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class search2 extends AppCompatActivity {

    EditText num;
    int x;
    ListView listView;

    Database_menu1 data =new Database_menu1(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        num = (EditText)findViewById(R.id.number);
        listView = (ListView)findViewById(R.id.vview) ;


        Intent ser=getIntent();
        x=ser.getExtras().getInt("gg");

    }

    String tablename;

    public void btn_done(View view) {

        if(x==0)
        {
            tablename="menu1";
                ArrayList<String> listv =data.re_max_attend(Integer.parseInt(num.getText().toString()),tablename);
                 ArrayAdapter arrayAdapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,listv);
                 listView.setAdapter(arrayAdapter);


        }
        else if(x==1)
        {
            tablename="menu2";
            ArrayList<String> listv =data.re_max_attend(Integer.parseInt(num.getText().toString()),tablename);
            ArrayAdapter arrayAdapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,listv);
            listView.setAdapter(arrayAdapter);
        }
        else if(x==2)
        {
            tablename="menu3";
            ArrayList<String> listv =data.re_max_attend(Integer.parseInt(num.getText().toString()),tablename);
            ArrayAdapter arrayAdapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,listv);
            listView.setAdapter(arrayAdapter);
        }
        else if(x==3)
        {
            tablename="menu4";
            ArrayList<String> listv =data.re_max_attend(Integer.parseInt(num.getText().toString()),tablename);
            ArrayAdapter arrayAdapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,listv);
            listView.setAdapter(arrayAdapter);
        }

        num.setText("");

    }
    }

