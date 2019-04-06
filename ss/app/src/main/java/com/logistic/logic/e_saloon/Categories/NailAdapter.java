package com.logistic.logic.e_saloon.Categories;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class NailAdapter extends FragmentStatePagerAdapter {
    public NailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch (i){
            case 0:
                fragment=new ManicuresFragmnet();
                break;
            case 1:
                fragment=new PedicuresFragment();
                break;
            case 2:
                fragment=new SculturedNailFragmnet();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Manicures";
            case 1:
                return "Pedicures";
            case 2:
                return "Sculptured Nails";
            default:
                return null;
        }
    }
}
