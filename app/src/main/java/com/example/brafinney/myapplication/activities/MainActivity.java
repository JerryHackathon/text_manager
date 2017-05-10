package com.example.brafinney.myapplication.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brafinney.myapplication.R;
import com.example.brafinney.myapplication.models.Contestant;
import com.example.brafinney.myapplication.receiver.AlarmReceiver;
import com.example.brafinney.myapplication.utils.Globall;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    EditText etPhoneNumber;
    TextView tvContestant;
    TextView btnSendReport;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Contestant contestant;
    String shortCodeBb, timesBb;
    Spinner spinner;

    public static final String SHORT_CODE = "SHORTCODE";
    public static final String TIMES = "TIMES";
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor = sp.edit();

        contestant  = (Contestant)getIntent().getSerializableExtra(ChooseContestant.SELECTED_CONTESTANT);
        shortCodeBb = getIntent().getStringExtra(ChooseContestant.SHORTCODE_BROUGHT_BACK);
        timesBb     = getIntent().getStringExtra(ChooseContestant.TIME_BROUGHT_BACK);



        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        tvContestant = (TextView)findViewById(R.id.etMessage);
        btnSendReport = (TextView) findViewById(R.id.btnSendReport);
        spinner = (Spinner)findViewById(R.id.spinner2);


        //Initialize Spinner and set things up
        spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Globall.list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(dataAdapter);





        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (contestant == null){
                    Globall.toast(MainActivity.this, "Select A Contestant", true);
                } else {
                    String contestantName = contestant.getName();
                    String shortCode = etPhoneNumber.getText().toString().trim();
                    String votesString = (String) spinner.getSelectedItem();
                    int votes = Integer.parseInt(votesString);

                    Globall.shortCode = contestantName;
                    Globall.contestantName = shortCode;

                    sendMessages(votes, Globall.contestantName, Globall.shortCode);

                }


            }
        });

        tvContestant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChooseContestant.class)
                .putExtra(SHORT_CODE, etPhoneNumber.getText().toString().trim())
                .putExtra(TIMES, String.valueOf(spinner.getSelectedItemPosition())));
            }
        });


        //Check for permission

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }





    }


    private void sendMessages(int times, String phone, String msg) {
        for(int i  = 0; i < times; i++)

            if(i != times - 1) {
                try {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(phone, null, msg, null, null);
                    //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();


                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "It is either you do not have enough credit to send these texts or there " +
                            "is no service at your current location or you possibly entered" +
                            " a wrong number. Please check and try again", Toast.LENGTH_LONG).show();


                }

        } else {

                try {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(phone, null, msg, null, null);
                    Toast.makeText(getApplicationContext(), "Votes have been Sent", Toast.LENGTH_SHORT).show();


                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "It is either you do not have enough credit to send these texts or there " +
                            "is no service at your current location or you possibly entered" +
                            " a wrong number. Please check and try again", Toast.LENGTH_LONG).show();


                }
            }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(contestant != null){
         tvContestant.setText(contestant.getName());
         etPhoneNumber.setText(shortCodeBb);
         spinner.setSelection(Integer.parseInt(timesBb));
        }
    }


    public  void scheduleMessages(View v){
        startActivity(new Intent(getApplicationContext(), ScheduleActivity.class));
    }
}
