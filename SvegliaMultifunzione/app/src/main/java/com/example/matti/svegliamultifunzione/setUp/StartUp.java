package com.example.matti.svegliamultifunzione.setUp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matti.svegliamultifunzione.R;

/**
 * Created by MATTI on 28/12/2017.
 */

public class StartUp extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_up, container, false);
    }
}
