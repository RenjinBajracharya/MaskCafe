package com.example.renjin.maskcafe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renjin.maskcafe.pojo.Menulist;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by Renjin on 11/20/2017.
 */

public class MenuDetailActivity extends AppCompatActivity {
    Menulist menuList;
    CircularImageView mMenuImage;
    TextView mName,mPrice,mMaterials,mDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudetails);

        mMenuImage=(CircularImageView)findViewById(R.id.activity_menudetails_image);
        mName=(TextView)findViewById(R.id.activity_menudetails_text1);
        mPrice=(TextView)findViewById(R.id.activity_menudetails_text2);
        mMaterials=(TextView)findViewById(R.id.activity_menudetails_text3);
        mDetail=(TextView)findViewById(R.id.activity_menudetails_text4);

        menuList= (Menulist)getIntent().getSerializableExtra("key");

        mName.setText(menuList.getName().toString());
        mPrice.setText("Rs."+menuList.getPrice().toString());
        mMaterials.setText(menuList.getMaterials().toString());
        mDetail.setText(menuList.getDetails().toString());

        Glide.with(MenuDetailActivity.this).load(menuList.getImage()).into(mMenuImage);

    }
}
