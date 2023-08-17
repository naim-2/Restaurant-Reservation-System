package com.example.partaproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Admin extends AppCompatActivity {
    private EditText firstName, lastName, userName, privilege1, phoneNumber, email, gender, password, confirm_password;
    private TextView LogInPage, topic;
    private Button Sign_Up, update, delete;

    DatabaseHelper1 DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        userName=findViewById(R.id.userName);
        privilege1=findViewById(R.id.privilege1);
        phoneNumber=findViewById(R.id.phoneNumber);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        Sign_Up=findViewById(R.id.Sign_Up);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        LogInPage=findViewById(R.id.LogInPage);
        topic=findViewById(R.id.topic);

        DB=new DatabaseHelper1(this);

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName1 = firstName.getText().toString();
                String lastName1 = lastName.getText().toString();
                String userName1 = userName.getText().toString();
                String privilege2 = privilege1.getText().toString();
                String phoneNumber1=phoneNumber.getText().toString();
                String email1=email.getText().toString();
                String gender1=gender.getText().toString();
                String password1 = password.getText().toString();
                String confirm_password1=confirm_password.getText().toString();

                Cursor res=DB.getdata(userName1);
                if (res.getCount()!=0){
                    Toast.makeText(Admin.this,"Username is taken!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password1.equals(confirm_password1)) {
                    Toast.makeText(getApplicationContext(), "The password match is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Boolean checkinsertdata=DB.insertuserdata(userName1, privilege2, firstName1, lastName1, phoneNumber1, email1, gender1, password1);
                    if(checkinsertdata==true){
                        Toast.makeText(Admin.this,"Successfully Registered New User!", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), LoggingIn.class);
                        startActivity(intent2);}
                    else{
                        Toast.makeText(Admin.this,"Not Registered!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName1 = firstName.getText().toString();
                String lastName1 = lastName.getText().toString();
                String userName1 = userName.getText().toString();
                String privilege2 = privilege1.getText().toString();
                String phoneNumber1=phoneNumber.getText().toString();
                String email1=email.getText().toString();
                String gender1=gender.getText().toString();
                String password1 = password.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(userName1, privilege2, firstName1, lastName1, phoneNumber1, email1, gender1, password1);
                if (checkupdatedata==true){
                    Toast.makeText(Admin.this,"Entry Updated!",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(getApplicationContext(), LoggingIn.class);
                    startActivity(intent2);
                }
                else{
                    Toast.makeText(Admin.this,"Entry Not Updated!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName1 = userName.getText().toString();

                Boolean checkdeletedata = DB.deleteuserdata(userName1);
                if (checkdeletedata==true){
                    Toast.makeText(Admin.this, "Entry Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoggingIn.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Admin.this, "Entry Not Deleted!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        LogInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=DB.getdata();
                if (res.getCount()==0){
                    Toast.makeText(Admin.this,"There are no Users' Details",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){ //loop through the records
                    buffer.append("Username : "+res.getString(0)+"\n");
                    buffer.append("Privilege : "+res.getString(1)+"\n");
                    buffer.append("First Name : "+res.getString(2)+"\n");
                    buffer.append("Last Name : "+res.getString(3)+"\n");
                    buffer.append("Phone Number : "+res.getString(4)+"\n");
                    buffer.append("Email: "+res.getString(5)+"\n");
                    buffer.append("Gender: "+res.getString(6)+"\n\n");
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(Admin.this);
                builder.setCancelable(true);
                builder.setTitle("Current Users:");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FillTables.class);
                startActivity(intent);
            }
        });
    }
}