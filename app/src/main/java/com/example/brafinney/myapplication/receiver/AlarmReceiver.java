package com.example.brafinney.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.brafinney.myapplication.utils.Globall;

/**
 * Created by pianoafrik on 5/9/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String phone = Globall.herNumber;
        String msg =   Globall.herMessage;
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, msg, null, null);


        }catch (Exception e){

            Toast.makeText(context, "It is either you do not have enough credit to send these texts or there " +
                    "is no service at your current location or you possibly entered" +
                    " a wrong number. Please check and try again", Toast.LENGTH_LONG).show();


        }
        Toast.makeText(context, "SMS Sent", Toast.LENGTH_LONG).show();

    }


}
