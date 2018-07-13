package com.example.renjin.maskcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.renjin.maskcafe.helper.Constant;
import com.example.renjin.maskcafe.helper.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Renjin on 3/17/2018.
 */

public class LoginActivity extends AppCompatActivity {

    JSONObject jsonObject;

    JsonParser jsonParser=new JsonParser();

    AutoCompleteTextView mEmail;
    Button mLogin,mSignup;
    EditText mPassword;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail=(AutoCompleteTextView)findViewById(R.id.activity_login_email_auto);
        mPassword=(EditText) findViewById(R.id.activity_login_passwordedit);
        mLogin=(Button)findViewById(R.id.activity_login_login_button);
        mSignup=(Button)findViewById(R.id.activity_login_SignUp_button);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent I=new Intent(LoginActivity.this,PrimeActivity.class);
                startActivity(I);*/

                if (mPassword.getText().length()>2|| mEmail.getText().length()>2) {
                    new PerformLogin().execute();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Provide proper Information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent J=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(J);
            }
        });


    }
    class PerformLogin extends AsyncTask<String, String, String> {
        String email=mEmail.getText().toString();
        String password=mPassword.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(LoginActivity.this);
            mProgress.setMessage("Loading");
            mProgress.setCancelable(false);
            mProgress.show();

        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap=new HashMap<>();
            hashMap.put("email",email);
            hashMap.put("password",password);

            String url= Constant.BASE_URL+"api/login";
            jsonObject= jsonParser.performPostCI(url, hashMap);
            mProgress.dismiss();

            if (jsonObject==null){
                Constant.flag=1;
            } else{
                try{
                    if (jsonObject.getString("status").equals("success")){
                        //  mProgress.dismiss();
                        Constant.flag=2;

                    }else{
                        Constant.flag=3;
                    }

                }
                catch (JSONException e){
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (Constant.flag==1){
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            } else if (Constant.flag==2){
                Toast.makeText(LoginActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            } else if (Constant.flag==3){
                Toast.makeText(LoginActivity.this, "Wrong data", Toast.LENGTH_SHORT).show();
            }
            mProgress.dismiss();
        }
    }
}
