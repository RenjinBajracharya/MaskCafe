package com.example.renjin.maskcafe.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renjin.maskcafe.R;
import com.example.renjin.maskcafe.pojo.Menulist;

import java.util.ArrayList;


/**
 * Created by Renjin on 3/17/2018.
 */

public class MenulistAdapter extends ArrayAdapter<Menulist> {

    ArrayList<Menulist> menuList;
    Context context;
    TextView mName,mPrice;
    ImageView mImage;

    public MenulistAdapter( Context context1, ArrayList<Menulist> menuList){
        super(context1, 0, menuList);
        this.menuList= menuList;
        this.context= context1;
    }

    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Menulist item) {
        return super.getPosition(item);
    }
    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Menulist menuList=getItem(position);

        if(convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_menulist,parent,false);
        }
        mName=(TextView)convertView.findViewById(R.id.adapter_menulist_name);
        mPrice=(TextView)convertView.findViewById(R.id.adapter_menulist_price);
        mImage=(ImageView)convertView.findViewById(R.id.adapter_menulist_image);

        mName.setText(menuList.getName());
        mPrice.setText("Rs. "+ menuList.getPrice());
        Glide.with(getContext()).load(menuList.getImage()).placeholder(R.drawable.masklogo).into(mImage);

        return convertView;
    }
}
