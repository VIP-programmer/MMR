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
import com.example.mmr.medic.Medcin;
import com.example.mmr.patient.Meetings;
import com.example.mmr.patient.Notes;
import com.example.mmr.patient.OnlineMeds;
import com.example.mmr.patient.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static com.android.volley.VolleyLog.TAG;

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
    public interface LoadHomeInfoCallBack{
        void onSuccess(Vector<Object> vector);
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
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        Patient patient=new Patient();
                        patient.setCin(json.getString("cin"));
                        patient.setNom(json.getString("nom"));
                        patient.setPrenom(json.getString("prenom"));
                        patient.setPhoto(json.getString("photo"));
                        callBack.onSuccess(patient);
                    }else{
                        Log.i("tagconvertstr", "["+response+"]");
                        callBack.onErr(json.getString("messages"));
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
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("vous étes bien enregestrer !");
                    }else{
                        Log.i("tagconvertstr", "["+response+"]");
                        String messages = json.getString("messages");
                        callBack.onErr(messages);
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
    public void update(Map<String,String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/patient/update.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("vous étes mise à jour !");
                    }else{
                        Log.i("tagconvertstr", "["+response+"]");
                        String messages = json.getString("messages");
                        callBack.onErr(messages);
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
    public void getOnlineMedAndNote(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/home_data.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    OnlineMeds medcins = new OnlineMeds();
                    //Vector<Notes.Note> notes = new Vector<>();
                    Notes notes = new Notes();
                    JSONObject json = new JSONObject(response);
                    JSONArray medsArray= json.getJSONArray("medics");
                    JSONArray notesArray= json.getJSONArray("notes");
                    for (int i = 0; i < medsArray.length(); i++) {
                        medcins.addOnlineMed(new OnlineMeds.OnlineMed(medsArray.getJSONObject(i).getString("photo"),
                                medsArray.getJSONObject(i).getString("cin"),
                                true
                        ));
                    }
                    vector.add(medcins);
                    for (int i = 0; i < notesArray.length(); i++) {
                        notes.addNote(new Notes.Note(notesArray.getJSONObject(i).getString("titre"),
                                notesArray.getJSONObject(i).getString("nom")+" "+notesArray.getJSONObject(i).getString("prenom"),
                                notesArray.getJSONObject(i).getString("courps_note"),
                                notesArray.getJSONObject(i).getInt("priorite"),
                                notesArray.getJSONObject(i).getString("date_note")
                                ));
                        //notes.add(note);
                    }
                    vector.add(notes);
                    callBack.onSuccess(vector);
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
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }
    public void getMedcins(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/my_meds.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    OnlineMeds medcins = new OnlineMeds();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONArray medsArray = new JSONArray(response);
                    for (int i = 0; i < medsArray.length(); i++) {
                        medcins.addOnlineMed(new OnlineMeds.OnlineMed(medsArray.getJSONObject(i).getString("photo"),
                                medsArray.getJSONObject(i).getString("cin"),
                                medsArray.getJSONObject(i).getString("nom")+" "+medsArray.getJSONObject(i).getString("prenom"),
                                true
                        ));
                    }
                    vector.add(medcins);
                    callBack.onSuccess(vector);
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
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }
    public void getMedcinInfos(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/med_infos.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONObject json = new JSONObject(response);
                    Medcin medcin = new Medcin();
                    medcin.setCin(json.getString("cin"));
                    medcin.setNom(json.getString("nom"));
                    medcin.setPrenom(json.getString("prenom"));
                    medcin.setAdresse(json.getString("adresse"));
                    medcin.setEmail(json.getString("email"));
                    medcin.setPhoto(json.getString("photo"));
                    medcin.setTele(json.getString("tele"));
                    medcin.setSpeciality(json.getString("intitule_spec"));
                    medcin.setAbout(json.getString("about"));
                    medcin.setDateJoin(json.getString("date_sing_in"));
                    medcin.setNbPatients(json.getString("nb"));
                    medcin.setOnline(json.getInt("is_active") == 1);
                    vector.add(medcin);
                    callBack.onSuccess(vector);
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
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }
    public void getMyInfos(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/my_infos.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONObject json = new JSONObject(response);
                    Patient patient = new Patient();
                    patient.setAdresse(json.getString("adresse"));
                    patient.setVille(json.getString("ville"));
                    patient.setAge(json.getInt("age"));
                    patient.setEmail(json.getString("email"));
                    patient.setTele(json.getString("tele"));
                    patient.setSang(json.getInt("id_sang"));
                    patient.setAssurance(json.getInt("id_assur"));
                    vector.add(patient);
                    callBack.onSuccess(vector);
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
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }
    public void getMeetings(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/my_meets.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    Meetings meetings = new Meetings();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONArray meetArray = new JSONArray(response);
                    for (int i = 0; i < meetArray.length(); i++) {
                        meetings.addmeeting(new Meetings.Meeting(meetArray.getJSONObject(i).getString("date_rend"),
                                meetArray.getJSONObject(i).getString("heur"),
                                meetArray.getJSONObject(i).getString("nom")+" "+meetArray.getJSONObject(i).getString("prenom")
                        ));
                    }
                    vector.add(meetings);
                    callBack.onSuccess(vector);
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
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }

    public void UploadProfileImg(Map<String,String> infos, final SignUpCallBack callBack){

        String url = Config.URL+"/Model/upload_image.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        callBack.onSuccess("Bien Ajouter");
                    }else{
                        callBack.onErr("erreur");
                    }
                } catch (JSONException e) {
                    callBack.onErr("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                    callBack.onErr("Impoussible de se connecter");
                else if (error instanceof VolleyError){}
                    //callBack.onErr("Une erreur s'est produite" + error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return infos;
            }
        };

        request.setTag(TAG);
        queue.add(request);

    }

    public void kill(){
        if (queue!=null){
            queue.cancelAll("TAG");
        }
    }
}
