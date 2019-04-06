package com.logistic.logic.e_saloon.Categories;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public class MaleHairPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public MaleHairPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
               return new MaleHairCutFragment();
            case 1:
               return new MaleHairStraightFragment();

            case 2:
               return new MaleHairColorFragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
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
                return "Hair Colour & Touch Up";
                default:
                    return null;
        }
    }
}
