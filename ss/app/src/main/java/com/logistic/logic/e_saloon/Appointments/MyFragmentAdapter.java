package com.logistic.logic.e_saloon.Appointments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyFragmentAdapter extends FragmentStatePagerAdapter {

    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        if (i==0){
            fragment=new BookedAppointment();
        }
        else {
            fragment=new CancelledAppointment();
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
        if (position==0){
            return "Booked";

        }
        else {
            return "Cancelled";
        }
    }
}
