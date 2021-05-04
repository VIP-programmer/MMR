package com.example.mmr.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mmr.R;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity {

    BubbleTabBar bubbleTabBar;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //loadFragment(new HomeFragment());
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        bubbleTabBar = (BubbleTabBar) findViewById(R.id.bubbleTabBar);
        bubbleTabBar.getChildAt(0).setActivated(true);

        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                //Fragment fragment;
                switch (i) {
                    case R.id.record: /*fragment=RecordFragment.newInstance(1,"page2")*/
                        vpPager.setCurrentItem(1);
                        break;
                    case R.id.calendar: /*fragment=CalendarFragment.newInstance(2,"page2")*/
                        vpPager.setCurrentItem(2);
                        break;
                    case R.id.profile: /*fragment=ProfileFragment.newInstance(3,"page2")*/
                        vpPager.setCurrentItem(3);
                        break;
                    default: /*fragment=HomeFragment.newInstance(0,"page2")*/
                        vpPager.setCurrentItem(0);
                }
                //loadFragment(fragment);
            }
        });

    }

    /*
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
     */
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return HomeFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return RecordFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return CalendarFragment.newInstance(2, "Page # 3");
                default:
                    return ProfileFragment.newInstance(3, "Page # 4");
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}