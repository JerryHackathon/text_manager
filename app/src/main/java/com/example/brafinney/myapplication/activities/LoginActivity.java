package com.example.brafinney.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.brafinney.myapplication.R;
import com.example.brafinney.myapplication.initializers.MyActivity;
import com.example.brafinney.myapplication.utils.Globall;

import org.w3c.dom.Text;

import androidsdk.devless.io.devless.main.Devless;

public class LoginActivity extends AppCompatActivity {

    TextView btnLogin, btnForgotPassword,  btnAlreadyHaveAnAccount;
    EditText etSignInEmail, etSignInPassword;
    MyActivity app;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Devless devless;
    ProgressDialog pd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor = sp.edit();
        app = (MyActivity)getApplication();
        btnLogin = (TextView)findViewById(R.id.btnLogin);
        btnForgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        btnAlreadyHaveAnAccount = (TextView)findViewById(R.id.tvAlreadyHaveAnAccount);
        etSignInEmail = (EditText)findViewById(R.id.etSignInEmail);
        etSignInPassword = (EditText)findViewById(R.id.etSignInPassword);
        devless = app.getDevless();
        devless.addUserToken(sp);
        pd = new ProgressDialog(this);

        //Check whether signed in
        if(sp.contains(Globall.LOGIN_KEY)){
            startActivity(new Intent(this, MainActivity.class));

        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Globall.getValue(etSignInEmail);
                String password = Globall.getValue(etSignInPassword);
                //Log user in
                login(email, password);

            }
        });


        btnAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void login(String email, String password) {
        pd.setTitle("Authenticating...");
        pd.show();
        Globall.login(devless, email, password, sp, new Devless.LoginResponse() {
            @Override
            public void OnLogInSuccess(String s) {
                //TODO:open main activity
                pd.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                editor.putString(Globall.LOGIN_KEY, "loggedIn");
                editor.commit();
                Globall.toast(LoginActivity.this, "Log in was a success", true);
            }

            @Override
            public void OnLogInFailed(String s) {
                //TODO: return error message
                pd.dismiss();
                Globall.toast(LoginActivity.this, s, true);
            }
        });
    }


}
