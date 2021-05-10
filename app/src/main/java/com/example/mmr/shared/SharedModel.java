package com.example.mmr.shared;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mmr.Config;
import com.example.mmr.patient.Patient;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class SharedModel {
    private Context context;
    private RequestQueue queue;
    private StringRequest request;
    public interface SignUpCallBack{
        void onSuccess(String message);
        void onErr(String message);
    }
    public interface LoginCallBack{
        void onSuccess(Patient patient);
        void onErr(String message);
    }
    public SharedModel(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }
    public void connexion(Map<String,String> infos, LoginCallBack callBack){
        String url = Config.URL+"/Model/patient/login.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        Patient patient=new Patient();
                        patient.setCin(json.getString("cin"));
                        patient.setAdresse(json.getString("adresse"));
                        patient.setEmail(json.getString("email"));
                        patient.setNom(json.getString("nom"));
                        patient.setPrenom(json.getString("prenom"));
                        patient.setPhoto(json.getString("photo"));
                        patient.setTele(json.getString("tele"));
                        patient.setVille(json.getString("ville"));
                        patient.setAge(json.getInt("age"));
                        patient.setGender((char)json.get("gender"));
                        callBack.onSuccess(patient);
                    }else{
                        Log.i("tagconvertstr", "["+response+"]");
                        JSONObject messages = (JSONObject) json.getJSONObject("messages");
                        callBack.onErr(messages.getString("messages"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return infos;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }
    public void register(Map<String,String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/patient/sign_up.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("vous étes bien enregestrer !");
                    }else{
                        Log.i("tagconvertstr", "["+response+"]");
                        JSONObject messages = (JSONObject) json.getJSONObject("messages");
                        callBack.onErr(messages.getString("messages"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("TAG", "onErrorResponse: " + error.getMessage());
                    callBack.onErr("Impoussible de se connecter");
                }else if (error instanceof VolleyError)
                    callBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return infos;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }

    public void kill(){
        if (queue!=null){
            queue.cancelAll("TAG");
        }
    }
}
