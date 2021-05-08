package com.example.mmr.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mmr.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileMed extends AppCompatActivity {
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_med);
        imageView = findViewById(R.id.profile_img_doc_pat);

    }
}