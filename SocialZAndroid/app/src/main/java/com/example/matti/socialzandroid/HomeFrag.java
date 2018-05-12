package com.example.matti.socialzandroid;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class HomeFrag extends Fragment {

    private Context c;
    private ImageView image;

    public void setContext(Context c){
        this.c=c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        image=view.findViewById(R.id.logo_home);
        Animation an=  AnimationUtils.loadAnimation(c, R.anim.zoom);
        image.setAnimation(an);
        return view;
    }
}
