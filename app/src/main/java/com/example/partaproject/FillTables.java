package com.example.partaproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Calendar;
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

public class FillTables extends AppCompatActivity {

    private EditText hotel, table, nop, date;
    private Button insert, delete;
    private DatePickerDialog datePickerDialog;
    private TextView all;

    DatabaseHelper3 DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_tables);
        hotel = findViewById(R.id.hotel);
        table = findViewById(R.id.table);
        date = findViewById(R.id.date);
        nop = findViewById(R.id.nop);
        insert = findViewById(R.id.insert);
        delete = findViewById(R.id.delete);
        all = findViewById(R.id.all);

        DB=new DatabaseHelper3(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotel1 = hotel.getText().toString();
                String table1 = table.getText().toString();
                String nop1 = nop.getText().toString();
                String date1 = date.getText().toString();
                String tableno1 = hotel1+table1;
                String occupied1 = "No";
                String waiter1 = "None";

                Cursor res = DB.getdata(tableno1);
                if (res.getCount() == 0) {
                    Boolean insertuserdata = DB.insertuserdata(tableno1, date1, nop1, occupied1, waiter1);
                    if (insertuserdata == true) {
                        Toast.makeText(FillTables.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(FillTables.this, "Entry Not Updated!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(FillTables.this, "Table had Already Been Registered!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(FillTables.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotel1 = hotel.getText().toString();
                String table1 = table.getText().toString();
                String nop1 = nop.getText().toString();
                String date1 = date.getText().toString();
                String tableno1 = hotel1+table1;
                String occupied1 = "No";
                String waiter = "None";

                Cursor res = DB.getdata(tableno1);
                if (res.getCount() != 0) {
                    Boolean deleteuserdata = DB.deleteuserdata(tableno1);
                    if (deleteuserdata == true) {
                        Toast.makeText(FillTables.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(FillTables.this, "Failed To Delete!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(FillTables.this, "There is no such a table!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=DB.getdata();
                if (res.getCount()==0){
                    Toast.makeText(FillTables.this,"There are no Tables' Details",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){ //loop through the records
                    String split = res.getString(0);
                    buffer.append("Hotel : "+split.substring(0, 1)+"\n");
                    buffer.append("Table : "+split.substring(1)+"\n");
                    buffer.append("Date : "+res.getString(1)+"\n");
                    buffer.append("Max Number of People : "+res.getString(2)+"\n");
                    buffer.append("Occupied : "+res.getString(3)+"\n");
                    buffer.append("Wait(er/ress): "+res.getString(4)+"\n\n");
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(FillTables.this);
                builder.setCancelable(true);
                builder.setTitle("Current Tables:");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}

