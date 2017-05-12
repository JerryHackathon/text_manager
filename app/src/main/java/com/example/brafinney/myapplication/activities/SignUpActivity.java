package com.example.brafinney.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.brafinney.myapplication.R;
import com.example.brafinney.myapplication.initializers.MyActivity;
import com.example.brafinney.myapplication.utils.Globall;

import androidsdk.devless.io.devless.main.Devless;

public class SignUpActivity extends AppCompatActivity {

    TextView btnSignUp,  btnAlreadyHaveAnAccountYh;
    EditText etSignUpEmail, etSignUpPassword;
    MyActivity app;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Devless devless;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor = sp.edit();
        app = (MyActivity)getApplication();
        btnSignUp = (TextView)findViewById(R.id.btnSignUp);
        btnAlreadyHaveAnAccountYh = (TextView)findViewById(R.id.tvAlreadyHaveAnAccountYh);
        etSignUpEmail = (EditText)findViewById(R.id.etSignUpEmail);
        etSignUpPassword = (EditText)findViewById(R.id.etSignUpPassword);
        devless = app.getDevless();
        devless.addUserToken(sp);

        //Check whether signed in
        if(sp.contains(Globall.SIGN_UP_KEY)){
            startActivity(new Intent(this, LoginActivity.class));

        }

        btnAlreadyHaveAnAccountYh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: sign users up

                String email = Globall.getValue(etSignUpEmail);
                String password = Globall.getValue(etSignUpPassword);
                signUp(email, password);
            }
        });

    }

    private void signUp(String email, String password) {

        Globall.signUp(devless, email, password, sp, new Devless.SignUpResponse() {
            @Override
            public void OnSignUpSuccess(String s) {
                //TODO:redirect to main after successful signup
                Log.e("fail sign up==", s);
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                editor.putString(Globall.SIGN_UP_KEY, "signedUp");
                editor.commit();
                Globall.toast(SignUpActivity.this, "Log in was a success", true);
            }

            @Override
            public void OnSignUpFailed(String s) {
                //TODO: return error message
                Globall.toast(SignUpActivity.this, s, true);
                Log.e("fail sign up==", s);
            }
        });
    }
}
