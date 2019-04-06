package com.logistic.logic.e_saloon.Categories;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FeamleSpaPagerAdapter extends FragmentStatePagerAdapter {

    public FeamleSpaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch (i){
            case 0:
                fragment=new FemaleSpaExpressFragment();
                break;
            case 1:
                fragment=new FemaleSpaMassageFrgament();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Express Spa";
            case 1:
                return "Body Massage";
                default:
                    return null;
        }
    }
}
