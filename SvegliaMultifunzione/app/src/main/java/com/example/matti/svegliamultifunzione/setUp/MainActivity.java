package com.example.matti.svegliamultifunzione.setUp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentManager;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.matti.svegliamultifunzione.R;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    static ProgressBar bar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        bar = findViewById(R.id.progressBar);
        bar.setMax(4);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new StartUp();
                case 1:
                    return new WifiSetup();
                case 3:
                    return new BluetoothSetup();
                case 2:
                    return new WeatherSetUp();
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
