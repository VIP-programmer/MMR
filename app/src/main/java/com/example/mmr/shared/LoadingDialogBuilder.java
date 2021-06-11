package com.example.mmr.shared;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.example.mmr.medic.MedicAddNewVisit;

public class LoadingDialogBuilder {

    public static ProgressDialog mProgressDialog;

    public static void startDialog(Context activity){
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage("Veuillez patienter ...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }
    public static void closeDialog(){
        mProgressDialog.dismiss();
    }
}
