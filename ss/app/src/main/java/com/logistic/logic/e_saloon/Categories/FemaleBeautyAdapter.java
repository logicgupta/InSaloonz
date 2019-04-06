package com.logistic.logic.e_saloon.Categories;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FemaleBeautyAdapter extends FragmentStatePagerAdapter {

    public FemaleBeautyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment=new FemaleBeautyBleachFragment();
                break;
            case 1:
                fragment=new FemaleBeautyFacialFragment();
                break;
            case 2:
                fragment=new FemaleBeautyThreadFragment();
                break;
            case 3:
                fragment=new FemaleBeautyWaxFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Bleach";
            case 1:
                return "Facial";
            case 2:
                return "Threading";
            case 3:
                return "Body Wax";
                default:
                    return null;
        }
    }
}
