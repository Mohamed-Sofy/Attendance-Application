package com.example.pc.attendme;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.Touch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by PC on 8/9/2017.
 */
public class Database_menu1 extends SQLiteOpenHelper {
    private static final String database_name = "data_menu.db";



    private static final String table_name_menu1 = "menu1";
    private static final String table_name_menu2 = "menu2";
    private static final String table_name_menu3 = "menu3";
    private static final String table_name_menu4 = "menu4";


    private static final String create_menu2="CREATE TABLE "+table_name_menu2+"( studentid TEXT PRIMARY KEY, studentname TEXT, ip TEXT, attend INTEGER, nattend INTEGER)";
    private static final String create_menu3="CREATE TABLE "+table_name_menu3+"( studentid TEXT PRIMARY KEY, studentname TEXT, ip TEXT, attend INTEGER, nattend INTEGER)";
    private static final String create_menu4="CREATE TABLE "+table_name_menu4+"( studentid TEXT PRIMARY KEY, studentname TEXT, ip TEXT, attend INTEGER, nattend INTEGER)";



    private  Context mycontext;
    SQLiteDatabase db;


   // String sql = "create table menu1 (studentid INTEGER PRIMARY KYE, id INTEGER, studentname TEXT, ip TEXT, attend INTEGER)";

    //private static final String create_menu1="CREATE TABLE "+table_name_menu1+"(studentid INTEGER PRIMARY KYE, id INTEGER, studentname TEXT, ip TEXT, attend INTEGER)";
    public Database_menu1(Context context) {
        super(context, database_name, null, 1);
        mycontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table menu1 (studentid TEXT PRIMARY KEY, studentname TEXT, ip TEXT, attend INTEGER, nattend INTEGER)");
            db.execSQL(create_menu2);
            db.execSQL(create_menu3);
            db.execSQL(create_menu4);

           // Toast.makeText(mycontext, "created", Toast.LENGTH_LONG).show();
        }catch(Exception e) {
            Toast.makeText(mycontext, ""+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name_menu1);
        db.execSQL("DROP TABLE IF EXISTS " + table_name_menu2);
        db.execSQL("DROP TABLE IF EXISTS " + table_name_menu3);
        db.execSQL("DROP TABLE IF EXISTS " + table_name_menu4);

        onCreate(db);
    }

  //  int temp ;
    public boolean insert_data(String name,String id,String table_name)
    {

        String ip="0";
        SQLiteDatabase ss = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("studentid",id);
        contentValues.put("studentname",name);
        contentValues.put("ip",ip);
        contentValues.put("attend",0);
        contentValues.put("nattend",0);

        long result = ss.insert(table_name,null,contentValues);
        if (result == -1)
            return false;
        else {
            return true;
        }
    }

    public void delete_table(String tablename)
    {
        SQLiteDatabase SS=this.getWritableDatabase();
       // SS.execSQL("DELETE * FROM " + tablename);
        SS.delete(tablename,null,null);
        Toast.makeText(mycontext," تم مسح كل الاعضاء ",Toast.LENGTH_SHORT).show();
    }

    public int delete_data(String id,String table_name)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(table_name,"studentid = ? ",new String[]{id});
    }

//تحويل ال ip and id الى صفر بعد الضغط على اغلاق التسجيل
    public boolean update_to_zreo(String table_name)
    {
        SQLiteDatabase aa = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

      int num_row= re_numberOfRow(table_name);  // عدد row  ف table

    int rr=1;

        for(int i=1;num_row >=i;i++)
        {
            contentValues.put("ip",0);
            contentValues.put("attend",0);
            //aa.update(table_name, contentValues,"id="+i,null) ;
            aa.update(table_name, contentValues,"attend="+rr,null);
        }
        Toast.makeText(mycontext," database apdate",Toast.LENGTH_LONG).show();
        return true;

    }

    //String num_records;
    public int re_numberOfRow(String tablename)
    {
        SQLiteDatabase z =this.getReadableDatabase();
        String  sql="SELECT * FROM "+tablename;
        Cursor c= z.rawQuery(sql,null);
        int con = c.getCount();
        c.close();
        return con;
    }

    public boolean attend_student(String id,String ip,String table_name)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        String num_attend =re_nattend(id,table_name);  //بيرحع عدد مرات الحضور
        int increment=1;
        int number=Integer.parseInt(num_attend)+increment;  // بزود واحد

        contentValues.put("ip",ip);
        contentValues.put("attend",1);
        contentValues.put("nattend",number);

        sqLiteDatabase.update(table_name,contentValues,"studentid="+id,null);

       // Toast.makeText(mycontext," تم",Toast.LENGTH_LONG).show();
        return true;
    }

    String num_of_attends;

// تستخدم فى ارجاع عدد مرات حضور الطالب
    // استخدمت فى class search , search2
    public String re_nattend(String id,String tablename)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String[] col={"nattend"};
        Cursor re=sqLiteDatabase.query(tablename,col,"studentid ='"+id+"'",null,null,null,null);
        while (re.moveToNext())
        {
            int index1=re.getColumnIndex("nattend");
            num_of_attends= re.getString(index1);
        }
        return num_of_attends;
    }

    // بترجع الطلاب التى لم تتجاوز عدد معين من المحاضرات
    public ArrayList re_max_attend(int num , String tablename)
    {

        ArrayList arrayList = new ArrayList();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor re =sqLiteDatabase.rawQuery("select * from "+tablename+" where nattend >= "+num, null);
        re.moveToFirst();
        String name ,id ,att;
        while (re.isAfterLast()==false)
        {
            id=re.getString(0);
            name=re.getString(1);
            att=re.getString(4);
            arrayList.add(id +"                       "+name  +"                "+att+"     ");

            re.moveToNext();
        }
        return arrayList;
    }

    /*
    public ArrayList get_data(String table_name) {

        ArrayList arrayList = new ArrayList();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor result = sqLiteDatabase.rawQuery("select * from '"+table_name+"'", null);
        result.moveToFirst();
        String id, name,ip,att;

        while (result.isAfterLast() == false) {
            id = result.getString(0);
            name = result.getString(1);
            ip=result.getString(2);
            att=result.getString(3);
            arrayList.add(id + ":" + name+ ":" +ip+":" +att);
            result.moveToNext();
        }
        return arrayList;
    }
*/

    // بتششك اذا كان ip مستخدم قبل ذلك او لا
    public boolean check_ip_exists(String ip_check,String table_name)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor re =sqLiteDatabase.rawQuery("select * from "+table_name, null);
        re.moveToFirst();
        String ip ;

        while(re.isAfterLast()==false)
        {
            ip=re.getString(2);
            if(ip.equals(ip_check))
            {
               return true;
            }
            re.moveToNext();
        }
            return false;
    }



    ///////////////////////////////
    // بترجع عدد الطلاب التى تم تحضرهم
    public int re_numofattend(String tablename)
    {
        int count=0;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor re =sqLiteDatabase.rawQuery("select * from "+tablename, null);
        re.moveToFirst();
        int c;
        while(re.isAfterLast()==false)
        {
            c= re.getInt(3);
            if(c==1)
            {
                count++;
            }
            re.moveToNext();
        }

        return count;
    }

    //////////////////////////////////////////////////////////

    String var;

    public String re_name(String id , String tablename)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String[] col={"studentname"};
        Cursor re=sqLiteDatabase.query(tablename,col,"studentid ='"+id+"'",null,null,null,null);
        while (re.moveToNext())
        {
            int index1=re.getColumnIndex("studentname");
            var= re.getString(index1);
        }
        return var ;
    }


    /*
    public String re_attend(String id,String tablename)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String[] col={"nattend"};
        Cursor re=sqLiteDatabase.query(tablename,col,"studentid ='"+id+"'",null,null,null,null);
        while (re.moveToNext())
        {
            int index1=re.getColumnIndex("nattend");
            var= re.getString(index1);
        }
        return var ;
    }*/





}