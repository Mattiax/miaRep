package com.example.matti.svegliamultifunzione.setUp;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.matti.svegliamultifunzione.R;

/**
 * Created by MATTI on 28/12/2017.
 */

public class StartUp extends Fragment {

    private ImageView bluetooth, phone, tablet;
    private Animation phoneAn, bltAn, tabletAn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_up, container, false);
        bluetooth = v.findViewById(R.id.bluetooth_start_up);
        phone = v.findViewById(R.id.phone_start_up);
        tablet = v.findViewById(R.id.tablet_start_up);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        bluetooth.setBackgroundResource(R.drawable.bluetoot_anim);
        phoneAn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fall_down);
        bltAn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_left);
        tabletAn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_left);
        setAnimation();


    }

    private void setAnimation() {
        phoneAn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bluetooth.startAnimation(bltAn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        bltAn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                bluetooth.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tablet.startAnimation(tabletAn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tabletAn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tablet.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((AnimationDrawable) bluetooth.getBackground()).start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        phone.startAnimation(phoneAn);
    }
}
