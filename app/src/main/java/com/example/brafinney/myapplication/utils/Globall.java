package com.example.brafinney.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brafinney.myapplication.initializers.MyActivity;
import com.example.brafinney.myapplication.interfaces.ContestantResponse;
import com.example.brafinney.myapplication.models.Contestant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidsdk.devless.io.devless.main.Devless;

public class Globall {

    public static List<Contestant> contestants = new ArrayList<>(Arrays.asList(
         new Contestant("Zolile", "https://s23.postimg.org/mgrg37xfv/15232314_10211680954483901_2785968541291555517_n.jpg"),
         new Contestant("Peter", "https://s18.postimg.org/h6p24jhvt/ohana.jpg")
    ));

   public static List<String> list = new ArrayList<String>(Arrays.asList(
           "Select Number Of Votes",
           "5",
           "10",
           "20",
           "30"
   ));

    public static List<String> listSchedule = new ArrayList<String>(Arrays.asList(
            "Select Seconds From Now",
            "5",
            "10",
            "60",
            "120",
            "1800",
            "3600",
            "43200",
            "86400"
    ));



    public static void toast (Context context, String str ,boolean bool) {

        if(bool)
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

    }

    public static String shortCode, contestantName, herNumber, herMessage;

    public static String getValue (EditText editText) {

        return editText.getText().toString().trim();
    }

public static final String      APP_URL = "http://tukopamoja.herokuapp.com",
                                DEVLESSTOKEN="b53259a8db32af3fc165cf14e15d7e5f",
                                LOGIN_KEY = "loginKey" ,
                                SIGN_UP_KEY = "signInKey",
                                CONTESTANT_TABLE = "contestants",
                                VOTES_TABLE = "votes",
                                CONTESTANT_SERVICE = "contestants";



    public static Devless initializeDevless (Context context) {
        return  new Devless(context, APP_URL, DEVLESSTOKEN );
    }

    public static MyActivity app;

    public static void login(Devless devless, String email, String password, SharedPreferences sp, final Devless.LoginResponse loginResponse){
        devless.loginWithEmailAndPassword(email, password, sp, new Devless.LoginResponse() {
            @Override
            public void OnLogInSuccess(String s) {
                loginResponse.OnLogInSuccess(s);
            }

            @Override
            public void OnLogInFailed(String s) {
                loginResponse.OnLogInFailed(s);
            }
        });
    }




    public static void signUp(Devless devless, String email, String password, SharedPreferences sp, final Devless.SignUpResponse signUpResponse) {

        devless.signUpWithEmailAndPassword(email, password, sp, new Devless.SignUpResponse() {
            @Override
            public void OnSignUpSuccess(String s) {
                signUpResponse.OnSignUpSuccess(s);
            }

            @Override
            public void OnSignUpFailed(String s) {
                signUpResponse.OnSignUpFailed(s);
            }
        });

    }



    public static void getContestants(Devless devless, final ContestantResponse contestantResponse){
        devless.getData(CONTESTANT_SERVICE,CONTESTANT_TABLE, new Devless.RequestResponse() {
            @Override
            public void OnSuccess(String s) {
                try {
                    JSONObject JO = new JSONObject(s);
                    String payload = JO.getString("payload");
                    JSONObject payloadObject = new JSONObject(payload);
                    String results = payloadObject.getString("results");
                    JSONArray resultArray = new JSONArray(results);
                    List<Contestant> contestants =  new ArrayList<Contestant>();
                    for (int i = 0; i < resultArray.length(); i++){
                        String id, name, imageUrl, userId;
                        JSONObject contestantObject =  resultArray.getJSONObject(i);
                        id = contestantObject.getString("id");
                        userId = contestantObject.getString("devless_user_id");
                        name = contestantObject.getString("name");
                        imageUrl = contestantObject.getString("image_url");
                        Contestant doughnuts = new Contestant(id, userId, name, imageUrl);
                        contestants.add(0, doughnuts);
                    }
                    contestantResponse.OnSuccess(contestants);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public static void addContestant(Devless devless, String name, final Devless.RequestResponse requestResponse){
        Map<String, Object> details  = new HashMap<>();
        details.put("name", name);
        details.put("image_url", "https://s13.postimg.org/m4qxl4ctj/IMG_2381.jpg");
        devless.postData(CONTESTANT_SERVICE, CONTESTANT_TABLE, details, new Devless.RequestResponse() {
            @Override
            public void OnSuccess(String s) {
                requestResponse.OnSuccess(s);
            }
        });
    }

}
