package com.example.pc.attendme;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class navigation extends AppCompatActivity {

    int positionn;
    String tablename;


    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    Database_menu1 Data=new Database_menu1(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        final Intent i=getIntent();
        positionn=i.getExtras().getInt("oo");

        // call the navigation layout
        mdrawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mtoggle = new ActionBarDrawerToggle(this,mdrawerLayout,R.string.open,R.string.close);

        mdrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.main_contuiner,new add_student());
        fragmentTransaction.commit();
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.search:
                    {
                       // fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        Intent ser=new Intent(navigation.this,search.class);
                        ser.putExtra("pos",positionn);
                        startActivity(ser);
                        item.setChecked(true);
                      //  mdrawerLayout.closeDrawers();
                        break;

                    }

                    case R.id.add:
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Intent add=new Intent(navigation.this,add_student.class);
                        add.putExtra("pp",positionn);
                        startActivity(add);
                        item.setChecked(true);
                      //  mdrawerLayout.closeDrawers();
                        break;

                    }

                    case R.id.delete:
                    {
                       fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Intent add=new Intent(navigation.this,delete_page.class);
                        add.putExtra("qq",positionn);
                        startActivity(add);
                        item.setChecked(true);
                       // mdrawerLayout.closeDrawers();
                        break;

                    }


                    case R.id.delete_all_student:

                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        final Dialog dialog =new Dialog(navigation.this);
                        dialog.setContentView(R.layout.dialog_delete);
                        Button btn=(Button) dialog.findViewById(R.id.delete_alll);
                        Button cansel=(Button) dialog.findViewById(R.id.cansel);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(positionn==0) {
                                    tablename="menu1";
                                    Data.delete_table(tablename);
                                }
                                else if(positionn==1)
                                {
                                    tablename="menu2";
                                    Data.delete_table(tablename);
                                }
                                else if(positionn==2)
                                {
                                    tablename="menu3";
                                    Data.delete_table(tablename);
                                }
                                else if(positionn==3)
                                {
                                    tablename="menu4";
                                   Data.delete_table(tablename);
                                }

                                dialog.dismiss();

                            }
                        });

                        cansel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                              //  dialog.dismiss();
                            }
                        });

                        dialog.show();
                        mdrawerLayout.closeDrawers();
                        break;

                    }

                    case R.id.start:
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        Intent star = new Intent(navigation.this,bluetooth_device.class);
                        star.putExtra("pos",positionn);
                        startActivity(star);

                        item.setChecked(true);
                     //   mdrawerLayout.closeDrawers();
                        break;

                    }

                    case R.id.about:
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        Intent aboutt = new Intent(navigation.this,about.class);
                        startActivity(aboutt);

                        item.setChecked(true);
                        //   mdrawerLayout.closeDrawers();
                        break;

                    }

                }


                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
