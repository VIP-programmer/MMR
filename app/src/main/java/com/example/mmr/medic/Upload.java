package com.example.mmr.medic;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class Upload {
    /*
    public static void uploadDocument(String encodedPDF, Activity activity) {

        Call<ResponsePOJO> call = RetrofitClient.getInstance().getAPI().uploadDocument(encodedPDF);
        call.enqueue(new Callback<ResponsePOJO>() {

            @Override
            public void onResponse(Call<ResponsePOJO> call, retrofit2.Response<ResponsePOJO> response) {
                Toast.makeText(activity, response.body().getRemarks(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePOJO> call, Throwable t) {
                Toast.makeText(activity, "Problem de connexion", Toast.LENGTH_SHORT).show();

            }
        });
    }

     */
}
