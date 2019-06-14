package com.example.pc.attendme;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class show_menus extends AppCompatActivity {


    ListView list;
    ArrayAdapter<String>adapter;
    ArrayList<String>arrayList=new ArrayList<>();


    final public static String NAME_TAG = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menus);

        list =(ListView) findViewById(R.id.show_list);

        show();

    }
    String e1= ("menu1");
    String e2 =("menu2");
    String e3 =("menu3");
    String e4= ("menu4");

    public void show()
    {
        String[] items={e1,e2,e3,e4};
        arrayList =new ArrayList<>(Arrays.asList(items));  // اسماء listview
        adapter =new ArrayAdapter<String>(this,R.layout.lists,R.id.txtitem,arrayList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i=new Intent(show_menus.this,navigation.class);
                    i.putExtra("oo",position);
                    startActivity(i);

                   // Toast.makeText(show_menus.this,x,Toast.LENGTH_LONG).show();
            }
        });

    }


public  void change(final int pos)
{
    final Dialog dialog = new Dialog(show_menus.this);
    dialog.setContentView(R.layout.dialog_edit);  // لفتح dialog

    final EditText edit =(EditText)dialog.findViewById(R.id.ename);
    Button btn=(Button) dialog.findViewById(R.id.done);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            arrayList.set(pos,edit.getText().toString());
           // men.insert(edit.getText().toString(),pos);
            adapter.notifyDataSetChanged();
            dialog.dismiss();


        }
    });
    dialog.show();
}

    public void edit1(View view) {

        change(0);
    }

    public void edit2(View view) {
        change(1);
    }

    public void edit3(View view) {
        change(2);
    }

    public void edit4(View view) {
        change(3);
    }
}



