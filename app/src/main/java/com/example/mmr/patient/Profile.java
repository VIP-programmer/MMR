package com.example.mmr.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mmr.R;
import com.fxn.BubbleTabBar;

public class Profile extends AppCompatActivity {
    BubbleTabBar bubbleTabBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bubbleTabBar=findViewById(R.id.bubbleTabBar);
        bubbleTabBar.getChildAt(3).setSelected(true);
    }
}