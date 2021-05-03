package com.example.mmr.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mmr.R;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

public class Home extends AppCompatActivity {

    BubbleTabBar bubbleTabBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFragment(new HomeFragment());
        bubbleTabBar=(BubbleTabBar)findViewById(R.id.bubbleTabBar);
        bubbleTabBar.getChildAt(0).setActivated(true);

        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Fragment fragment;
                switch (i){
                    case R.id.record: fragment=RecordFragment.newInstance(1,"page2");break;
                    case R.id.calendar: fragment=CalendarFragment.newInstance(2,"page2");break;
                    case R.id.profile: fragment=ProfileFragment.newInstance(3,"page2");break;
                    default: fragment=HomeFragment.newInstance(0,"page2");
                }
                loadFragment(fragment);
            }
        });

    }
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
}