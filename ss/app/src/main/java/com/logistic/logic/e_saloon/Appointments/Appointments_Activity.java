package com.logistic.logic.e_saloon.Appointments;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.logistic.logic.e_saloon.R;

public class Appointments_Activity extends AppCompatActivity
                    implements BookedAppointment.OnFragmentInteractionListener
                    ,CancelledAppointment.OnFragmentInteractionListener {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_);


        viewPager=findViewById(R.id.appointmentViewPager);
        MyFragmentAdapter myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentAdapter);

        tabLayout=findViewById(R.id.appointmentTabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
