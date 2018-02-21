package com.example.genji.am012_fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Dado extends Fragment {

    private int mParam1=0;

    public static Dado newInstance(int num) {
        Dado fragment = new Dado();
        Bundle args = new Bundle();
        args.putInt("NUMERO", num);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt("NUMERO");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dado, container, false);
        switch(mParam1){
            case 1:
                view.setBackgroundResource(R.drawable.one);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.two);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.three);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.four);
                break;
            case 5:
                view.setBackgroundResource(R.drawable.five);
                break;
            case 6:
                view.setBackgroundResource(R.drawable.six);
                break;
            default:
                break;
        }
        return view;
    }
}
