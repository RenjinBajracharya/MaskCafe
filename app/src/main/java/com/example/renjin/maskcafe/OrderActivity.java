package com.example.renjin.maskcafe;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.renjin.maskcafe.helper.Constant;
import com.example.renjin.maskcafe.helper.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Renjin on 11/21/2017.
 */

public class OrderActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JsonParser jsonParser=new JsonParser();
    ProgressDialog mProgress;
    AutoCompleteTextView mItem,mDetail,mUserid,mUsername;
    EditText mTime,mDate;
    Button mOrder;
    Spinner mQuantity;
    Calendar calendar;
    String selectedQuantity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        calendar = Calendar.getInstance();
        mItem=(AutoCompleteTextView)findViewById(R.id.activity_order_item);
        mQuantity=(Spinner) findViewById(R.id.activity_order_quantity);
        mDetail=(AutoCompleteTextView)findViewById(R.id.activity_order_detail);
        mUserid=(AutoCompleteTextView)findViewById(R.id.activity_order_userid);
        mUsername=(AutoCompleteTextView)findViewById(R.id.activity_order_username);
        mTime=(EditText)findViewById(R.id.activity_order_time);
        mDate=(EditText)findViewById(R.id.activity_order_date);
        mOrder=(Button)findViewById(R.id.activity_order_button);
        //Creating adapter for spinner
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(this, R.array.quantity_array, android.R.layout.simple_list_item_1);

        //DropDown layout style
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mQuantity.setAdapter(quantityAdapter);
        mQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedQuantity= mQuantity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItem.getText().length()>2||mTime.getText().length()>2||mDate.getText().length()>2||mDetail.getText().length()>2||mUserid.getText().length()!=0||mUsername.getText().length()>2) {
                    new PerformOrder().execute();
                }
                else {
                    Toast.makeText(OrderActivity.this, "Provide proper Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(OrderActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDate.setText(dayOfMonth+ "-"+(month +1)+"-" +year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //!End of  datePicker

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mtime= Calendar.getInstance();
                int mHour=mtime.get(Calendar.HOUR_OF_DAY);
                int mMin=mtime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker= new TimePickerDialog(OrderActivity.this,new TimePickerDialog.OnTimeSetListener(){
                    public void onTimeSet(TimePicker timepicker, int selectedhour, int selectedmin) {

                        mTime.setText(String.valueOf(selectedhour)+"/"+ String.valueOf(selectedmin));

                    }},mHour, mMin,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });



    }

    class PerformOrder extends AsyncTask<String, String, String> {
        String name=mItem.getText().toString();
        //String quantity=mQuantity.getText().toString();
        String time=mTime.getText().toString();
        String date=mDate.getText().toString();
        String detail=mDetail.getText().toString();
        String userid=mUserid.getText().toString();
        String username=mUsername.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            mProgress=new ProgressDialog(OrderActivity.this);
            mProgress.setMessage("Loading");
            mProgress.setCancelable(false);
            mProgress.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap=new HashMap<>();
            hashMap.put("name",name);
            hashMap.put("quantity",selectedQuantity);
            hashMap.put("time",time);
            hashMap.put("date",date);
            hashMap.put("detail",detail);
            hashMap.put("userid",userid);
            hashMap.put("username",username);
            String url= Constant.BASE_URL+"api/orderfood";
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
                    Toast.makeText(OrderActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Constant.flag==1){
                Toast.makeText(OrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            } else if (Constant.flag==2){
                Toast.makeText(OrderActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OrderActivity.this,MainActivity.class));
            } else if (Constant.flag==3){
                Toast.makeText(OrderActivity.this, "Wrong data", Toast.LENGTH_SHORT).show();
            }
            mProgress.dismiss();
        }
    }
}
