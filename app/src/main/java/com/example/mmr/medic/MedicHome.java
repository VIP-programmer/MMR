package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.mmr.R;
import com.example.mmr.patient.CalendarFragment;
import com.example.mmr.patient.Home;
import com.example.mmr.patient.HomeFragment;
import com.example.mmr.patient.PatientSessionManager;
import com.example.mmr.patient.ProfileFragment;
import com.example.mmr.patient.RecordFragment;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

public class MedicHome extends AppCompatActivity {
    BubbleTabBar bubbleTabBar;
    FragmentPagerAdapter adapterViewPager;
    MedicSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medic_home);
        sessionManager=new MedicSessionManager(this);
        //loadFragment(new HomeFragment());
        ViewPager vpPager = (ViewPager) findViewById(R.id.doc_vpPager);
        adapterViewPager = new MedicHome.MyPagerAdapter(getSupportFragmentManager(),sessionManager);
        vpPager.setAdapter(adapterViewPager);
        bubbleTabBar = (BubbleTabBar) findViewById(R.id.medic_doc_bubbleTabBar);
        bubbleTabBar.getChildAt(0).setActivated(true);

        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                //Fragment fragment;
                switch (i) {
                    case R.id.medic_record: /*fragment=RecordFragment.newInstance(1,"page2")*/
                        vpPager.setCurrentItem(1);
                        break;
                    case R.id.medic_calendar: /*fragment=CalendarFragment.newInstance(2,"page2")*/
                        vpPager.setCurrentItem(2);
                        break;
                    case R.id.medic_profile: /*fragment=ProfileFragment.newInstance(3,"page2")*/
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
        private MedicSessionManager sessionManager;

        public MyPagerAdapter(FragmentManager fragmentManager, MedicSessionManager sessionManager) {
            super(fragmentManager);
            this.sessionManager=sessionManager;
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
                    return MedicHomeFragment.newInstance(sessionManager.getCinMedcin(),
                            sessionManager.getNomMedcin(),
                            sessionManager.getPrenomMedcin(),
                            sessionManager.getImgMedcin());
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return MedicRecordFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return MedicRendezVousFragment.newInstance(2, sessionManager.getCinMedcin());
                default:
                    return MedicProfileFragment.newInstance(sessionManager.getCinMedcin(),
                            sessionManager.getNomMedcin(),
                            sessionManager.getPrenomMedcin(),
                            sessionManager.getImgMedcin());
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}