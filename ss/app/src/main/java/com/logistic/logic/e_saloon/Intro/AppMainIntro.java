package com.logistic.logic.e_saloon.Intro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.logistic.logic.e_saloon.Login.Login_Activity;
import com.github.paolorotolo.appintro.AppIntro2;

public class AppMainIntro extends AppIntro2 implements Intro1.OnFragmentInteractionListener,
                                     Intro2.OnFragmentInteractionListener
                    ,Intro3.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        addSlide(Intro1.newInstance("",""));
        addSlide(Intro2.newInstance("",""));
        addSlide(Intro3.newInstance("",""));

        showSkipButton(false);
        setDepthAnimation();

    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        this.startActivity(new Intent(AppMainIntro.this, Login_Activity.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
