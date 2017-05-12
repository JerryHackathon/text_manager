package com.example.brafinney.myapplication.initializers;

import android.app.Application;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.example.brafinney.myapplication.interfaces.ContestantResponse;
import com.example.brafinney.myapplication.models.Contestant;
import com.example.brafinney.myapplication.utils.Globall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidsdk.devless.io.devless.main.Devless;

/**
 * Created by pianoafrik on 5/11/17.
 */

public class MyActivity extends Application {


    Devless devless;

    @Override
    public void onCreate() {
        super.onCreate();

        devless = Globall.initializeDevless(this);
    }


    public Devless getDevless() {
        return devless;
    }






}
