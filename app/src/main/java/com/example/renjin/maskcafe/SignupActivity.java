package com.example.renjin.maskcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class SignupActivity extends AppCompatActivity {

    JSONObject jsonObject;

    JsonParser jsonParser=new JsonParser();

    EditText mName,mEmail,mPassword,mAddress,mPhone;
    Button mSignUp;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mName=(EditText)findViewById(R.id.activity_signup_name_edit);
        mEmail=(EditText)findViewById(R.id.activity_signup_email_edit);
        mPassword=(EditText)findViewById(R.id.activity_signup_password_edit);
        mAddress=(EditText)findViewById(R.id.activity_signup_address_edit);
        mPhone=(EditText)findViewById(R.id.activity_signup_phone_edit);
        mSignUp=(Button)findViewById(R.id.activity_signup_SignUp_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mName.length()<2|| mEmail.length()<2|| mPassword.length()<2|| mAddress.length()<2 || mPhone.length()<2) {
                    Toast.makeText(SignupActivity.this, "Provide proper Information", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this,SignupActivity.class));
                }
                new PerformRegister().execute();


            }
        });

    }

    class PerformRegister extends AsyncTask<String, String, String> {

        String name=mName.getText().toString();
        String email=mEmail.getText().toString();
        String password=mPassword.getText().toString();
        String address=mAddress.getText().toString();
        String phone=mPhone.getText().toString();



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(SignupActivity.this);
            mProgress.setMessage("Loading");
            mProgress.setCancelable(false);
            mProgress.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap=new HashMap<>();
            hashMap.put("name",name);
            hashMap.put("email",email);
            hashMap.put("password",password);
            hashMap.put("address",address);
            hashMap.put("phone",phone);

            String url= Constant.BASE_URL+"api/register";

            if (name.length()<2|| email.length()<2|| password.length()<2|| address.length()<2 || phone.length()<2) {
                // Toast.makeText(SignupActivity.this, "Provide proper Information", Toast.LENGTH_SHORT).show();
            }
            else {
                jsonObject= jsonParser.performPostCI(url, hashMap);
                if (jsonObject==null){
                    Constant.flag=1;
                } else{
                    try{
                        if (jsonObject.getString("status").equals("success")){
                            //  mProgress.dismiss();
                            Constant.flag=2;

                        }

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            if (Constant.flag==1){
                Toast.makeText(SignupActivity.this, Constant.NETWORK_ISSUE, Toast.LENGTH_SHORT).show();
            }

            else if(Constant.flag==2){
                Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
            mProgress.dismiss();

        }
    }
}
