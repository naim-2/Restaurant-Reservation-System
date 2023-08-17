package com.example.partaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    public DatabaseHelper2(Context context) {

        super(context, "Userreserves.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table Userreserves(username TEXT primary key, nop TEXT, time TEXT, date TEXT, tableno TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Userreserves");
    }

    //CRUD OPERATIONS

    public Boolean insertuserdata(String username, String nop, String time, String date, String tableno){
        SQLiteDatabase DB=this.getWritableDatabase(); //set the mode to receive values
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("nop", nop);
        contentValues.put("time",time);
        contentValues.put("date",date);
        contentValues.put("tableno",tableno);

        long result=DB.insert("Userreserves",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean updateuserdata(String username, String nop, String time, String date, String tableno){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("nop", nop);
        contentValues.put("time",time);
        contentValues.put("date",date);
        contentValues.put("tableno",tableno);

        Cursor cursor=DB.rawQuery("Select * from Userreserves where username=?", new String[]{username});
        if(cursor.getCount()>0) {
            long result = DB.update("Userreserves", contentValues, "username=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }



    public Boolean deleteuserdata(String username){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Userreservess where username=?", new String[]{username});
        if(cursor.getCount()>0) {
            long result = DB.delete("Userreserves", "username=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Userreserves", null);
        return cursor;


    }
    public Cursor getdata(String username){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Userreserves where username=?", new String[]{username});
        return cursor;


    }
}
