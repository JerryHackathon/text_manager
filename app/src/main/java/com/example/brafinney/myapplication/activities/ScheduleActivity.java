package com.example.brafinney.myapplication.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brafinney.myapplication.R;
import com.example.brafinney.myapplication.receiver.AlarmReceiver;
import com.example.brafinney.myapplication.utils.Globall;

import java.util.GregorianCalendar;

public class ScheduleActivity extends AppCompatActivity {

    Spinner spinner;
    EditText etHerPhoneNumber, etHerMessage;
    TextView  tvSelectContacts;
    TextView btnScheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        spinner = (Spinner)findViewById(R.id.spinnerSchedule);
        tvSelectContacts = (TextView)findViewById(R.id.tvSelectContacts);
        etHerPhoneNumber= (EditText)findViewById(R.id.etHerPhoneNumber);
        etHerMessage  = (EditText)findViewById(R.id.etHerMessage);
        btnScheduler = (TextView) findViewById(R.id.btnScheduleMessagesTwo);

        //Initialize Spinner and set things up
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_row, Globall.listSchedule);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(dataAdapter);

        tvSelectContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromContact();
            }
        });

        btnScheduler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Globall.herNumber = etHerPhoneNumber.getText().toString().trim();
                Globall.herMessage = etHerMessage.getText().toString().trim();
                //TODO: Handle empty errors
                if(validateSchedule()){
                    scheduleAlarm(Integer.parseInt((String) spinner.getSelectedItem()));
                } else {
                    Globall.toast(ScheduleActivity.this, "Error: Some fields appear to be empty", true);
                }
            }
        });

    }


    public void scheduleAlarm(int hoursFromNow)
    {
        Long time = new GregorianCalendar().getTimeInMillis()+ hoursFromNow*1000;
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this,1,
                intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Scheduling..", Toast.LENGTH_SHORT).show();

    }


    public void pickFromContact () {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(cursor.
                        getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                etHerPhoneNumber.setText(number);
            }
        }

    }

    public boolean validateSchedule(){

        if(spinner.getSelectedItem().equals("") || spinner.getSelectedItem().equals(Globall.listSchedule.get(0)) ||  Globall.herMessage.length() < 1 || Globall.herNumber.length() < 1){
            return false;
        } else {
            return true;
        }
    }
}
