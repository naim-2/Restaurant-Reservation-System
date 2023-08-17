package com.example.partaproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoggingIn extends AppCompatActivity{//implements View.OnClickListener{
    private EditText userName, password;
    private Button submit;
    private TextView sign_up, topic, waiter;

    DatabaseHelper1 DB;
    DatabaseHelper4 DB4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_in);

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        sign_up = findViewById(R.id.sign_up);
        submit = findViewById(R.id.submit);
        topic = findViewById(R.id.topic);
        waiter = findViewById(R.id.waiter);

        DB=new DatabaseHelper1(this);
        DB4=new DatabaseHelper4(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName1 = userName.getText().toString();
                String password1 = password.getText().toString();

                Cursor res=DB.getdata(userName1);
                if(res.getCount()==0){
                    Cursor res2=DB4.getdata(userName1);
                    {
                        if(res2.getCount()!=0){
                            String db_password="";
                            while(res2.moveToNext()) {
                                db_password = res2.getString(7);
                            }
                            if(db_password.equals(password1)){
                                Toast.makeText(LoggingIn.this, "Welcome Waiter!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Waiter2.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(LoggingIn.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                        else{
                            Toast.makeText(LoggingIn.this, "No such user exists!", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                }
                String db_privilege="", db_password="";
                while(res.moveToNext()) {
                    db_privilege = res.getString(1);
                    db_password = res.getString(7);
                }
                if(db_password.equals(password1)){
                    if(db_privilege.equals("admin")){
                        Intent intent = new Intent(getApplicationContext(), Admin.class);
                        startActivity(intent);
                    }
                    else if(db_privilege.equals("customer")){
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra("key1", userName1);
                        startActivity(intent2);
                    }
                }
                else if(!db_password.equals(password1)){
                    Toast.makeText(getApplicationContext(),"Wrong password entered!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });

        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Admin.class);
                startActivity(intent);
            }
        });
        waiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Waiter.class);
                startActivity(intent);
            }
        });
    }
}