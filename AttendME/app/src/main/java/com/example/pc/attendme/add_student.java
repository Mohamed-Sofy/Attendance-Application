package com.example.pc.attendme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.test.TouchUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_student extends AppCompatActivity {

    String tablename;
    EditText n , i ;
        int x;

    Database_menu1 data1 = new Database_menu1(this);
    boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        n =(EditText) findViewById(R.id.studentt_name);
        i =(EditText) findViewById(R.id.studentt_id);


        // اخذ اسم التابل من select_menu
        Intent add = getIntent();
        x=add.getExtras().getInt("pp");

    }


    public void save_student(View view) {

        String s_name ;
        String s_id;
        s_name=n.getText().toString();
        s_id=i.getText().toString();

        if(s_id.equals("") || s_name.equals(""))
        {
            Toast.makeText(this, "ادخل بيانات الطالب صحيحه", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                {
                    if (x == 0) {
                        tablename = "menu1";
                        result = data1.insert_data(s_name, s_id, tablename);
                    } else if (x == 1) {
                        tablename = "menu2";
                        result = data1.insert_data(s_name, s_id, tablename);
                    } else if (x == 2) {
                        tablename = "menu3";
                        result = data1.insert_data(s_name, s_id, tablename);
                    } else if (x == 3) {
                        tablename = "menu4";
                        result = data1.insert_data(s_name, s_id, tablename);
                    }
                }
                if (result == true) {
                    Toast.makeText(this, "تم الاضافه", Toast.LENGTH_SHORT).show();
                    n.setText("");
                    i.setText("");

                } else if (result == false) {
                    Toast.makeText(this, " لم يتم الاضافه", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, " NOT connection", Toast.LENGTH_SHORT).show();

            }
        }
    }



}
