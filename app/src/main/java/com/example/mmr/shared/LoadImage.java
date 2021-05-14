package com.example.mmr.shared;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mmr.R;
import com.example.mmr.patient.PatientSessionManager;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoadImage {
    CircleImageView imageView;
    Context context;
    public LoadImage(CircleImageView profile,Context context) {
        imageView=profile;
        this.context=context;
    }

   public void execute(String url){
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.profileholder).skipMemoryCache(true).into(imageView);
    }
}
