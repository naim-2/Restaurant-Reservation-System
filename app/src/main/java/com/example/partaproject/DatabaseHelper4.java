package com.example.partaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper4 extends SQLiteOpenHelper {

    public DatabaseHelper4(Context context) {

        super(context, "Waiter.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table Waiter(username TEXT primary key, privilege TEXT, firstname TEXT, lastname TEXT, phonenumber TEXT, email TEXT, gender TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Waiter");
    }

    //CRUD OPERATIONS

    public Boolean insertuserdata(String username, String privilege, String firstname, String lastname, String phonenumber, String email, String gender, String password){
        SQLiteDatabase DB=this.getWritableDatabase(); //set the mode to receive values
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("privilege", privilege);
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("email",email);
        contentValues.put("gender",gender);
        contentValues.put("password",password);

        long result=DB.insert("Waiter",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean updateuserdata(String username, String privilege, String firstname, String lastname, String phonenumber, String email, String gender, String password){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("privilege", privilege);
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("email",email);
        contentValues.put("gender",gender);
        contentValues.put("password",password);

        Cursor cursor=DB.rawQuery("Select * from Waiter where username=?", new String[]{username});
        if(cursor.getCount()>0) {
            long result = DB.update("Waiter", contentValues, "username=?", new String[]{username});
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

        Cursor cursor=DB.rawQuery("Select * from Waiter where username=?", new String[]{username});
        if(cursor.getCount()>0) {
            long result = DB.delete("Waiter", "username=?", new String[]{username});
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

        Cursor cursor=DB.rawQuery("Select * from Waiter", null);
        return cursor;


    }
    public Cursor getdata(String username){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Waiter where username=?", new String[]{username});
        return cursor;


    }
}
