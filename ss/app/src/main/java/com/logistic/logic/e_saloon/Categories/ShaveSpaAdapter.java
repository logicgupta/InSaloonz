package com.logistic.logic.e_saloon.Categories;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShaveSpaAdapter extends FragmentStatePagerAdapter {

    public ShaveSpaAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch (i){
            case 0:
                fragment=new MenTrimmingFragment();
                break;
            case 1:
                fragment=new MenShaveFragment();
                break;
            case 2:
                fragment=new MenMustacheFragmnet();
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
                return "Trimming";
            case 1:
                return "Beared";
            case 2:
                return "Mustache";
            default:
                return null;
        }
    }
}
