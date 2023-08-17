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

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Reserve extends AppCompatActivity{

    private EditText username, nop, date_text, time, calculate, table;
    private TextView bCalculate;
    private Button dialog, Sign_Up;
    private double nop_i, time_i, price;

    private DatePickerDialog datePickerDialog;

    DatabaseHelper2 DB;
    DatabaseHelper3 DB3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        username = findViewById(R.id.username);
        calculate = findViewById(R.id.calculate);
        Sign_Up=findViewById(R.id.Sign_Up);
        bCalculate=findViewById(R.id.bCalculate);
        nop = findViewById(R.id.nop);
        date_text=findViewById(R.id.date_text);
        time = findViewById(R.id.time);
        table = findViewById(R.id.table);

        DB=new DatabaseHelper2(this);
        DB3=new DatabaseHelper3(this);

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1 = username.getText().toString();
                String nop1 = nop.getText().toString();
                String time1 = time.getText().toString();
                String date_text1 = date_text.getText().toString();
                String table1 = table.getText().toString();

                if (Integer.valueOf(table1) > 20) {
                    Toast.makeText(Reserve.this, "This hotel can only support 20 tables!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Integer.valueOf(nop1)>5){
                    Toast.makeText(Reserve.this, "This table can only support a max of 4 people!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent100 = getIntent();
                String hotel1 = intent100.getStringExtra("key1");

                String tableno1 = hotel1 + table1;

                Cursor res = DB.getdata(username1);
                if (res.getCount() != 0) {
                    Boolean checkupdatedata = DB.updateuserdata(username1, nop1, time1, date_text1, tableno1);
                    if (checkupdatedata == true) {
                        Cursor res2 = DB3.getdata(tableno1);
                        int count = 0;
                        while (res2.moveToNext()) {
                            String occupied1 = res2.getString(3);
                            if (occupied1.equals("No"))
                                count = -1;
                        }
                        if (count == -1) {
                            String occupied1 = "Yes";
                            Boolean checkinsertdata2 = DB3.updateuserdata2(tableno1, occupied1);
                            if (checkinsertdata2 == true) {
                                Toast.makeText(Reserve.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent5 = new Intent(getApplicationContext(), SMS.class);
                                intent5.putExtra("key1", username1);
                                startActivity(intent5);
                            } else {
                                Toast.makeText(Reserve.this, "Table Taken!", Toast.LENGTH_SHORT).show();
                                Intent intent5 = new Intent(getApplicationContext(), SMS.class);
                                intent5.putExtra("key1", username1);
                                startActivity(intent5);
                            }
                            return;
                        }
                    } else {
                        Toast.makeText(Reserve.this, "Entry Not Updated!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Boolean checkinsertdata=DB.insertuserdata(username1, nop1, time1, date_text1, tableno1);
                    if(checkinsertdata==true){
                        Cursor res2 = DB3.getdata(tableno1);
                        int count = 0;
                        while (res2.moveToNext()) {
                            String occupied1 = res2.getString(3);
                            if (occupied1.equals("No"))
                                count = -1;
                        }
                        if (count == -1) {
                            String occupied1 = "Yes";
                            Boolean checkinsertdata2 = DB3.updateuserdata2(tableno1, occupied1);
                            if (checkinsertdata2 == true) {
                                Toast.makeText(Reserve.this,"Successfully Reserved!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SMS.class);
                                intent.putExtra("key1", username1);
                                startActivity(intent);}
                            } else {
                                Toast.makeText(Reserve.this, "Table is Taken!", Toast.LENGTH_SHORT).show();
                            }
                    }
                    else {
                        Toast.makeText(Reserve.this, "Not Reserved!", Toast.LENGTH_SHORT).show();
                    }
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
                datePickerDialog = new DatePickerDialog(Reserve.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date_text.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        bCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nop1 = nop.getText().toString();
                String time1 = time.getText().toString();
                double nop_i = Integer.valueOf(nop1);
                double time_i = Integer.valueOf(time1);
                double price =  1000;
                if(time_i>600 && time_i<900){
                    price = price * nop_i * 0.9;
                }
                else if(time_i>1200 && time_i<1500){
                    price = price * nop_i * 1.1;
                }
                else if(time_i>1800 && time_i<2200){
                    price = price * nop_i * 1.4;
                }
                else{
                    price = price * nop_i * 2;
                }
                calculate.setText(String.valueOf(price));
            }
        });
    }
}

