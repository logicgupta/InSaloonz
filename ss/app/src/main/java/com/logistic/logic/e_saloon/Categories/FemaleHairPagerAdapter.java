package com.logistic.logic.e_saloon.Categories;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FemaleHairPagerAdapter extends FragmentStatePagerAdapter {

    public FemaleHairPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new HairStyleFemaleFragment();
            case 1:
                return new HairStraightFemaleFragment();
            case 2:
                return new HairColorStyleFragment();

        }
        return null;
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
                return "Hair Cut & Styling";
            case 1:
                return "Hair Straightening";
            case 2:
                return "Hair Coloring";
                default:
                    return null;
        }
    }
}
