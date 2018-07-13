package com.example.renjin.maskcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.renjin.maskcafe.adapter.MenulistAdapter;
import com.example.renjin.maskcafe.helper.Constant;
import com.example.renjin.maskcafe.helper.JsonParser;
import com.example.renjin.maskcafe.pojo.Menulist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Renjin on 11/15/2017.
 */

public class MenulistActivity extends AppCompatActivity {

    ListView mList1;
    JsonParser jsonParser= new JsonParser();
    JSONObject jsonObject;
    Menulist menuList;
    MenulistAdapter menuListAdapter;
    ArrayList<Menulist> arrayofMenuList=new ArrayList<>();
    ProgressDialog mProgress;
    int status;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulist);
        mList1=(ListView)findViewById(R.id.activity_menulist_list);
        new ShowMenuList().execute();
    }
    class ShowMenuList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(MenulistActivity.this);
            mProgress.setMessage("Loading Menu");
            mProgress.setCancelable(false);
            mProgress.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap=new HashMap<>();
            hashMap.put("id","1");
            String url= Constant.BASE_URL +"api/showmenulist";

            jsonObject=jsonParser.performPostCI(url, hashMap);
            if (jsonObject== null){
               status=1;
            } else {
                try {
                    if (jsonObject.getString("status").equals("success")){
                        status=2;
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length(); i++){
                            JSONObject menuObject=jsonArray.getJSONObject(i);
                            String name,price,details,image,materials;
                            name=menuObject.getString("name");

                            price=menuObject.getString("price");
                            details=menuObject.getString("details");
                            image=menuObject.getString("image");
                            materials=menuObject.getString("materials");

                            menuList=new Menulist(name, price, details, image, materials);
                            arrayofMenuList.add(menuList);
                        }
                    }else{
                        status=3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mProgress.dismiss();
            if (status==2){
                menuListAdapter= new MenulistAdapter(MenulistActivity.this,arrayofMenuList);
                mList1.setAdapter(menuListAdapter);
                mList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        menuList=(Menulist) parent.getItemAtPosition(position);
                        Intent intent=new Intent(MenulistActivity.this,MenuDetailActivity.class);
                        intent.putExtra("key",menuList);
                        startActivity(intent);
                    }
                });
            }else if (status==1){
                Toast.makeText(MenulistActivity.this, "Somthing went Wrong", Toast.LENGTH_SHORT).show();
            }else if (status==3){
                Toast.makeText(MenulistActivity.this, "Wrong data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
