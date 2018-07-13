package com.example.renjin.maskcafe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.renjin.maskcafe.fragments.GallaryFragments;
import com.example.renjin.maskcafe.fragments.MainFragments;

/**
 * Created by Renjin on 3/17/2018.
 */

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    int numberOfTab;
    //Constructors defined with 2 parameters
    public ViewPageAdapter (FragmentManager fm, int numberOfTab) {
        super(fm);
        this.numberOfTab= numberOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        //use of switch case to call different fragments
        switch (position){
            case 0:
                MainFragments mainFragments=new MainFragments();
                return mainFragments;
            case 1:
                GallaryFragments gallaryFragments=new GallaryFragments();
                return gallaryFragments;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTab;
    }
}
