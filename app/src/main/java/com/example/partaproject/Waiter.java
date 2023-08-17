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

public class Waiter extends AppCompatActivity {
    private EditText firstName, lastName, userName, phoneNumber, email, gender, password, confirm_password;
    private TextView LogInPage;
    private Button Sign_Up;
    private String privilege;

    DatabaseHelper4 DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        userName=findViewById(R.id.userName);
        phoneNumber=findViewById(R.id.phoneNumber);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        Sign_Up=findViewById(R.id.Sign_Up);
        LogInPage=findViewById(R.id.LogInPage);

        DB=new DatabaseHelper4(this);

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName1 = firstName.getText().toString();
                String lastName1 = lastName.getText().toString();
                String userName1 = userName.getText().toString();
                String phoneNumber1=phoneNumber.getText().toString();
                String email1=email.getText().toString();
                String gender1=gender.getText().toString();
                String password1 = password.getText().toString();
                String confirm_password1=confirm_password.getText().toString();
                privilege="Waiter";

                Cursor res=DB.getdata(userName1);
                if (res.getCount()!=0){
                    Toast.makeText(Waiter.this,"Username is taken!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password1.equals(confirm_password1)) {
                    Toast.makeText(getApplicationContext(), "The password match is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Boolean checkinsertdata=DB.insertuserdata(userName1, privilege, firstName1, lastName1, phoneNumber1, email1, gender1, password1);
                    if(checkinsertdata==true){
                        Toast.makeText(Waiter.this,"Successfully Registered!", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), Waiter2.class);
                        intent2.putExtra("key1", userName1);
                        startActivity(intent2);}
                    else{
                        Toast.makeText(Waiter.this,"Not Registered!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        LogInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoggingIn.class);
                startActivity(intent);
            }
        });
    }
}