package com.example.partaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper3 extends SQLiteOpenHelper {

    public DatabaseHelper3(Context context) {

        super(context, "Tables.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table Tables(tableno TEXT primary key, date TEXT, seats TEXT, occupied TEXT, waiter TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Tables");
    }

    //CRUD OPERATIONS

    public Boolean insertuserdata(String tableno, String date, String seats, String occupied, String waiter){
        SQLiteDatabase DB=this.getWritableDatabase(); //set the mode to receive values
        ContentValues contentValues=new ContentValues();
        contentValues.put("tableno",tableno);
        contentValues.put("date", date);
        contentValues.put("seats",seats);
        contentValues.put("occupied",occupied);
        contentValues.put("waiter",waiter);

        long result=DB.insert("Tables",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean updateuserdata(String tableno, String date, String seats, String occupied, String waiter){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tableno",tableno);
        contentValues.put("date", date);
        contentValues.put("seats",seats);
        contentValues.put("occupied",occupied);
        contentValues.put("waiter",waiter);

        Cursor cursor=DB.rawQuery("Select * from Tables where tableno=?", new String[]{tableno});
        if(cursor.getCount()>0) {
            long result = DB.update("Tables", contentValues, "tableno=?", new String[]{tableno});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean updateuserdata2(String tableno, String occupied){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tableno",tableno);
        contentValues.put("occupied",occupied);

        Cursor cursor=DB.rawQuery("Select * from Tables where tableno=?", new String[]{tableno});
        if(cursor.getCount()>0) {
            long result = DB.update("Tables", contentValues, "tableno=?", new String[]{tableno});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean updateuserdata22(String tableno, String occupied, String waiter){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tableno",tableno);
        contentValues.put("occupied",occupied);
        contentValues.put("waiter", waiter);

        Cursor cursor=DB.rawQuery("Select * from Tables where tableno=?", new String[]{tableno});
        if(cursor.getCount()>0) {
            long result = DB.update("Tables", contentValues, "tableno=?", new String[]{tableno});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean deleteuserdata(String tableno){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Tables where tableno=?", new String[]{tableno});
        if(cursor.getCount()>0) {
            long result = DB.delete("Tables", "tableno=?", new String[]{tableno});
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

        Cursor cursor=DB.rawQuery("Select * from Tables", null);
        return cursor;


    }
    public Cursor getdata(String tableno){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Tables where tableno=?", new String[]{tableno});
        return cursor;


    }
    public Cursor getdata3(){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Tables where waiter=?", new String[]{"None"});
        return cursor;


    }
}
