package com.logistic.logic.e_saloon.BookSchedule;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.Payment.PaymentOptions;
import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ScheduleTime extends AppCompatActivity  implements NightSchedule.OnFragmentInteractionListener
        ,MorningSchedule.OnFragmentInteractionListener,AfterNoonSchedule.OnFragmentInteractionListener ,
        EveningSchedule.OnFragmentInteractionListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    CalendarView calendarView;
    Calendar calenderDate;
    String getDate;
    private int[] tabIcons={
            R.drawable.sunrise,
            R.drawable.sun,
            R.drawable.sun_rise,
            R.drawable.moon
    };
    String saloonName;
    String totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_time);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        Intent intent=getIntent();
        totalPrice=intent.getStringExtra("totalPrice");
        saloonName=intent.getStringExtra("saloonName");

        Log.e("Schedule Activity","  "+totalPrice);


        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar =
                new HorizontalCalendar.Builder(ScheduleTime.this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
                calenderDate=date;
                Toast.makeText(ScheduleTime.this, ""+calenderDate.get(Calendar.DATE), Toast.LENGTH_SHORT).show();
            }
        });

        viewPager=findViewById(R.id.showscheduleTime);
        setupViewPager(viewPager);

        tabLayout=findViewById(R.id.scheduletabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons(){
        TextView tabOne= (TextView) LayoutInflater.from(this)
                .inflate(R.layout.custom_tab,null);
        tabOne.setText("9:00-12:00");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sunrise,0,0);
        tabLayout.getTabAt(0).setCustomView(tabOne);


        TextView tabTwo= (TextView) LayoutInflater.from(this)
                .inflate(R.layout.custom_tab,null);
        tabTwo.setText("12:00-3:00");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sun,0,0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree= (TextView) LayoutInflater.from(this)
                .inflate(R.layout.custom_tab,null);
        tabThree.setText("3:00-6:00");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sun_rise,0,0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour= (TextView) LayoutInflater.from(this)
                .inflate(R.layout.custom_tab,null);
        tabFour.setText("6:00-9:00");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.moon,0,0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }


    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter =new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFlag(new MorningSchedule(),"9:00-12:00");
        adapter.addFlag(new AfterNoonSchedule(),"12:00-3:00");
        adapter.addFlag(new EveningSchedule(),"3:00-6:00");
        adapter.addFlag(new NightSchedule(),"6:00-9:00");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onFragmentInteraction(String time1,String type) {


            String orginalDate=calenderDate.get(Calendar.DATE)
                    +"/"+(calenderDate.get(Calendar.MONTH)+1)
                    +"/"+calenderDate.get(Calendar.YEAR);
        if (!time1.isEmpty()&& !type.isEmpty()&& !orginalDate.isEmpty()){
            Intent intent=new Intent(ScheduleTime.this,PaymentOptions.class);
            intent.putExtra("Date",calenderDate.get(Calendar.DATE));
            intent.putExtra("Month",calenderDate.get(Calendar.MONTH));
            intent.putExtra("YEAR",calenderDate.get(Calendar.YEAR));
            intent.putExtra("DateFormat",orginalDate);
            intent.putExtra("time",time1);
            intent.putExtra("DayTime",type);
            intent.putExtra("totalPrice",totalPrice);
            intent.putExtra("saloonName",saloonName);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Please Select The Time Slot !", Toast.LENGTH_SHORT).show();
        }
    }



    class ViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFlag(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }
}