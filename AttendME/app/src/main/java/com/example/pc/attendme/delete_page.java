package com.example.pc.attendme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class delete_page extends AppCompatActivity {

    int x;
    EditText text;
    int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_page);
       text = (EditText)findViewById(R.id.delete_5);

        Intent add = getIntent();
        x=add.getExtras().getInt("qq");
    }

    public void btn_del(View view) {
        Database_menu1 data = new Database_menu1(this);

        String  s_id=text.getText().toString();

        if(s_id.equals("") )
        {
            Toast.makeText(this, "ادخل id الطالب صحيح", Toast.LENGTH_SHORT).show();

        }
        else {
            try {
                {
                    if (x == 0) {
                        String tablename = "menu1";
                        result = data.delete_data(s_id, tablename);
                    } else if (x == 1) {
                        String tablename = "menu2";
                        result = data.delete_data(s_id, tablename);
                    } else if (x == 2) {
                        String tablename = "menu3";
                        result = data.delete_data(s_id, tablename);
                    } else if (x == 3) {
                        String tablename = "menu4";
                        result = data.delete_data(s_id, tablename);
                    }
                }

                if (result > 0) {
                    Toast.makeText(this, "تم المسح", Toast.LENGTH_SHORT).show();
                    text.setText("");

                } else {
                    Toast.makeText(this, " لم يتم المسح", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, " NOT connection", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
