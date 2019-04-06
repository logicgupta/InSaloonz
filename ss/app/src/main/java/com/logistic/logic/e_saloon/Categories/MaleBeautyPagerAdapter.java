package com.logistic.logic.e_saloon.Categories;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MaleBeautyPagerAdapter extends FragmentStatePagerAdapter {

    public MaleBeautyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
               return new MaleBeautyFacialFragment();
            case 1:
              return  new MaleBeautyThreadingFragment();


        }
        return null;
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
                return "Facial";
            case 1:
                return "Threading";
                default:
                    return null;

        }
    }
}
