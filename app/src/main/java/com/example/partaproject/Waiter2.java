package com.example.partaproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Waiter2 extends AppCompatActivity{

    private EditText username, hotel, date_text, table;
    private Button dialog, Sign_Up;
    private TextView all;

    private DatePickerDialog datePickerDialog;

    DatabaseHelper3 DB3;
    DatabaseHelper4 DB4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter2);
        username = findViewById(R.id.username);
        Sign_Up=findViewById(R.id.Sign_Up);
        hotel = findViewById(R.id.hotel);
        date_text=findViewById(R.id.date_text);
        all = findViewById(R.id.all);
        table = findViewById(R.id.table);

        DB3=new DatabaseHelper3(this);
        DB4=new DatabaseHelper4(this);

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1 = username.getText().toString();
                String hotel1 = hotel.getText().toString();
                String date_text1 = date_text.getText().toString();
                String table1 = table.getText().toString();

                if (Integer.valueOf(table1) > 20) {
                    Toast.makeText(Waiter2.this, "This hotel can only support 20 tables!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tableno1 = hotel1 + table1;

                Cursor res = DB3.getdata(tableno1);
                if (res.getCount() != 0) {
                    Boolean checkupdatedata = DB3.updateuserdata22(tableno1, "Yes", username1);
                    if (checkupdatedata == true) {
                        Toast.makeText(Waiter2.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Waiter2.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    return;
                } else {
                    Toast.makeText(Waiter2.this, "This table is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Waiter2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date_text.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=DB3.getdata3();
                if (res.getCount()==0){
                    Toast.makeText(Waiter2.this,"There are no seats without waiters!",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){ //loop through the records
                    String split = res.getString(0);
                    buffer.append("Hotel : "+split.substring(0, 1)+"\n");
                    buffer.append("Table Number : "+split.substring(1)+"\n");
                    buffer.append("Date : "+res.getString(1)+"\n");
                    buffer.append("Seats : "+res.getString(2)+"\n");
                    buffer.append("Occupied : "+res.getString(3)+"\n");
                    buffer.append("Waiter : "+res.getString(4)+"\n");
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(Waiter2.this);
                builder.setCancelable(true);
                builder.setTitle("Current Users:");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}

