package com.logistic.logic.e_saloon.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class BottomNavigationBehaviour extends CoordinatorLayout.Behavior<BottomNavigationView> {

    public BottomNavigationBehaviour(){

    }
    public BottomNavigationBehaviour(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent,
                                   @NonNull BottomNavigationView child, @NonNull View dependency) {
        boolean dependOn=dependency instanceof FrameLayout;
        return dependOn;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child
            , @NonNull View directTargetChild, @NonNull View target, int axes) {
        return axes==ViewCompat.SCROLL_AXIS_VERTICAL;
    }
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child
            , View target, int dx, int dy, int[] consumed) {
        if (dy < 0) {
            showBottomNavigationView(child);
        } else if (dy > 0) {
            hideBottomNavigationView(child);
        }
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(view.getHeight());
    }

    private void showBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(0);
    }
}
