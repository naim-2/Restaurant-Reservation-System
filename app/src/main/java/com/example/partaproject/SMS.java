package com.example.partaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMS extends AppCompatActivity {
    private EditText hpn, message;
    private Button send, change;
    DatabaseHelper2 DB;
    private String nop, time, date;
    private String message1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        hpn=findViewById(R.id.hpn);
        message=findViewById(R.id.message);
        send=findViewById(R.id.send);
        change=findViewById(R.id.change);

        hpn.setText("0701020304");
        Intent intent5=getIntent();
        String username1=intent5.getStringExtra("key1");

        DB=new DatabaseHelper2(this);

        Cursor res=DB.getdata(username1);
        if(res.getCount()==0){
            Toast.makeText(SMS.this, "No such user exists!", Toast.LENGTH_SHORT).show();
            return;
        }
        String db_nop="", db_time="", db_date="";
        while(res.moveToNext()) {
            db_nop = res.getString(1);
            db_time = res.getString(2);
            db_date = res.getString(3);
        }
        String message1 = "The following username "+username1+" would like to reserve a table of "+db_nop+" people at "+db_time+"h on "+db_date+".";
        message.setText(message1);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        SendSMS();;
                    }else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }

                }

            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reserve.class);
                startActivity(intent);
            }
        });
    }

    private void SendSMS(){
        String number=hpn.getText().toString().trim();
        String message1=message.getText().toString().trim();

        try{
            //call manager
            SmsManager smsManager=SmsManager.getDefault();

            //send the message
            smsManager.sendTextMessage(number,null,message1,null,null);
            Toast.makeText(this,"Message is sent",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Message is Not Sent", Toast.LENGTH_SHORT).show();
        }
    }
}