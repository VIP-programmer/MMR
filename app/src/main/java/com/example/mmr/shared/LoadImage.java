package com.example.mmr.shared;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoadImage extends AsyncTask<String,Void, Bitmap> {
    CircleImageView imageView;
    Context context;
    public LoadImage(CircleImageView profile,Context context) {
        imageView=profile;
        this.context=context;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlLink=strings[0];
        Bitmap bitmap=null;
        try {
            InputStream inputStream=new java.net.URL(urlLink).openStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        Bitmap resizedBitmap=bitmap;
        Log.i("TAG", "Image size before: "+ resizedBitmap.getWidth()+"X"+resizedBitmap.getHeight());
        switch(densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                resizedBitmap=Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.25), (int)(bitmap.getHeight()*0.25), true);
                break;

            case DisplayMetrics.DENSITY_MEDIUM:
                resizedBitmap=Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.5), (int)(bitmap.getHeight()*0.5), true);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                resizedBitmap=Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.75), (int)(bitmap.getHeight()*0.75), true);
                break;

        }

        Log.i("TAG", "Image size after: "+ resizedBitmap.getWidth()+"X"+resizedBitmap.getHeight());
        imageView.setImageBitmap(resizedBitmap);
    }
}
