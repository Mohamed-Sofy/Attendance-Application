package com.example.pc.attendme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class search extends AppCompatActivity {


    EditText id_s;

    Database_menu1 Data = new Database_menu1(this);
    int x;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        id_s = (EditText)findViewById(R.id.student_idsearch);
        t=(TextView) findViewById(R.id.vie);



        Intent ser=getIntent();
        x=ser.getExtras().getInt("pos");


    }
    String tablename;
    String ss , name ,attend ;

    public void sear(View view)
    {
        ss =id_s.getText().toString();  // id الطالب
        if(x==0)
        {
            tablename="menu1";
            name= Data.re_name(ss,tablename);
            attend=Data.re_nattend(ss,tablename);

            String info=" "+ss +"           "+name  +"        "+attend+"     ";
            t.setText(info);

        }
        else if(x==1)
        {
            tablename="menu2";
            name= Data.re_name(ss,tablename);
            attend=Data.re_nattend(ss,tablename);

            String info=" "+ss +"           "+name  +"        "+attend+"     ";
            t.setText(info);

        }
        else if(x==2)
        {
            tablename="menu3";
            name= Data.re_name(ss,tablename);
            attend=Data.re_nattend(ss,tablename);

            String info=" "+ss +"           "+name  +"        "+attend +"     ";
            t.setText(info);

        }
        else if(x==3)
        {
            tablename="menu4";
            name= Data.re_name(ss,tablename);
            attend=Data.re_nattend(ss,tablename);

            String info=" "+ss +"           "+name  +"        "+attend+"     ";
            t.setText(info);

        }

            id_s.setText("");
    }

    public void btn_sanwy(View view) {

        Intent w=new Intent(this,search2.class);
        w.putExtra("gg",x);
        startActivity(w);

    }
}
