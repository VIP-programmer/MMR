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
import com.example.mmr.patient.Allergie;
import com.example.mmr.patient.Analyse;
import com.example.mmr.patient.Medicament;
import com.example.mmr.patient.Meetings;
import com.example.mmr.patient.Notes;
import com.example.mmr.patient.OnlineMeds;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.Positions;
import com.example.mmr.patient.Visite;

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
    public interface LoginCallBackMedic{
        void onSuccess(Medcin medcin);
        void onErr(String message);
    }
    public interface LoadHomeInfoCallBack{
        void onSuccess(Vector<Object> vector);
        void onErr(String message);
    }
    public interface LoadAllergieCallBack{
        void onSuccess(Vector<Allergie> vector);
        void onErr(String message);
    }
    public interface LoadAnalyseCallBack{
        void onSuccess(Vector<Analyse> vector);
        void onErr(String message);
    }
    public interface LoadVisitCallBack{
        void onSuccess(Vector<Visite> vector);
        void onErr(String message);
    }
    public SharedModel(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }
    public void getPatientInfos(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/medcin/patient_infos.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONObject json = new JSONObject(response);
                    Patient patient = new Patient();
                    patient.setCin(json.getString("cin"));
                    patient.setNom(json.getString("nom"));
                    patient.setPrenom(json.getString("prenom"));
                    patient.setEmail(json.getString("email"));
                    patient.setPhoto(json.getString("photo"));
                    patient.setTele(json.getString("tele"));
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
    public void updateMedic(Map<String,String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/medcin/update.php";

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

    public void addNote(Map<String,String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/medcin/add_note.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("une note a été envoyée !");
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
    public void saveMeetingMed(Map<String,String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/medcin/save_meet.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("Enregeistré");
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
    public void saveMeetingPat(Map<String,String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/patient/save_meet.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("Enregeistré");
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
    public void addMeet(Map<String,String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/medcin/add_meet.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("un rendez-vous a été planifié !");
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
    public void getMyPositions(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/medcin/get_my_locations.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Positions positions = new Positions();
                    JSONObject json = new JSONObject(response);
                    JSONArray jsonArray= json.getJSONArray("tab");
                    Positions positions = new Positions(json.getString("type"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        positions.addPosition(new Positions.Position(Float.parseFloat(jsonArray.getJSONObject(i).getString("x")),
                                Float.parseFloat(jsonArray.getJSONObject(i).getString("y")),
                                jsonArray.getJSONObject(i).getString("name")
                        ));
                    }
                    vector.add(positions);

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
    public void getMyInfosAsMedic(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/medcin/my_infos.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONObject json = new JSONObject(response);
                    Medcin medcin = new Medcin();
                    medcin.setAdresse(json.getString("adresse"));
                    medcin.setEmail(json.getString("email"));
                    medcin.setTele(json.getString("tele"));
                    medcin.setPrenom(json.getString("prenom"));
                    medcin.setAbout(json.getString("about"));
                    medcin.setNom(json.getString("nom"));
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
    public void getPatients(String cinMedcin, LoadHomeInfoCallBack loadHomeInfoCallBack) {
        String url = Config.URL+"/Model/medcin/get_patients.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    Notes notes = new Notes();
                    JSONObject json = new JSONObject(response);
                    JSONArray patsArray= json.getJSONArray("patients");
                    for (int i = 0; i < patsArray.length(); i++) {
                        Patient patient = new Patient(patsArray.getJSONObject(i).getString("cin"),
                                patsArray.getJSONObject(i).getString("nom"),
                                patsArray.getJSONObject(i).getString("prenom")
                                );

                        vector.add(patient);
                    }
                    loadHomeInfoCallBack.onSuccess(vector);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("TAG", "onErrorResponse: " + error.getMessage());
                    loadHomeInfoCallBack.onErr("Impoussible de se connecter");
                }else if (error instanceof VolleyError)
                    loadHomeInfoCallBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cinMedcin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }
    public void getMyPatients(String cinMedcin, LoadHomeInfoCallBack loadHomeInfoCallBack) {
        String url = Config.URL+"/Model/medcin/get_my_patients.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    Notes notes = new Notes();
                    JSONObject json = new JSONObject(response);
                    JSONArray patsArray= json.getJSONArray("patients");
                    for (int i = 0; i < patsArray.length(); i++) {
                        Patient patient = new Patient(patsArray.getJSONObject(i).getString("cin"),
                                patsArray.getJSONObject(i).getString("nom"),
                                patsArray.getJSONObject(i).getString("prenom")
                                );
                        patient.setPhoto(patsArray.getJSONObject(i).getString("photo"));
                        vector.add(patient);
                    }
                    loadHomeInfoCallBack.onSuccess(vector);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("TAG", "onErrorResponse: " + error.getMessage());
                    loadHomeInfoCallBack.onErr("Impoussible de se connecter");
                }else if (error instanceof VolleyError)
                    loadHomeInfoCallBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cinMedcin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }

    public void getAllMedicaments(String cinMedcin, LoadHomeInfoCallBack loadHomeInfoCallBack) {
        String url = Config.URL+"/Model/medcin/get_medicaments.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    Notes notes = new Notes();
                    JSONObject json = new JSONObject(response);
                    JSONArray medicaments= json.getJSONArray("medicaments");
                    for (int i = 0; i < medicaments.length(); i++) {
                        com.example.mmr.medic.Medicament medicament = new com.example.mmr.medic.Medicament(medicaments.getJSONObject(i).getInt("id_medicament"),
                                medicaments.getJSONObject(i).getString("intitule_medicament"),
                                (float) medicaments.getJSONObject(i).getDouble("prix")
                        );

                        vector.add(medicament);
                    }
                    loadHomeInfoCallBack.onSuccess(vector);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("TAG", "onErrorResponse: " + error.getMessage());
                    loadHomeInfoCallBack.onErr("Impoussible de se connecter");
                }else if (error instanceof VolleyError)
                    loadHomeInfoCallBack.onErr("Une erreur s'est produite");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<String, String>();
                map.put("cin",cinMedcin);
                return map;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }

    public void addVisit(Map<String, String> infos, SignUpCallBack callBack) {
        String url = Config.URL+"/Model/medcin/add_new_visite.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "anxof had tkharbi9a: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("");
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

    public void registerMedic(Map<String, String> infos, SignUpCallBack callBack) {
        String url = Config.URL+"/Model/medcin/sign_up.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("vous étes bien enregistré !");
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
    public void MedicConnexion(Map<String, String> infos, LoginCallBackMedic callBack) {
        String url = Config.URL+"/Model/medcin/login.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        Medcin medcin=new Medcin();
                        medcin.setCin(json.getString("cin"));
                        medcin.setNom(json.getString("nom"));
                        medcin.setPrenom(json.getString("prenom"));
                        medcin.setPhoto(json.getString("photo"));
                        callBack.onSuccess(medcin);
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
                                meetArray.getJSONObject(i).getString("nom")+" "+meetArray.getJSONObject(i).getString("prenom"),
                                meetArray.getJSONObject(i).getString("id_rend")
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
    public void getMedicMeetings(Map<String, String> infos, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/medcin/my_meets.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAGM", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    Meetings meetings = new Meetings();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONArray meetArray = new JSONArray(response);
                    for (int i = 0; i < meetArray.length(); i++) {
                        meetings.addmeeting(new Meetings.Meeting(meetArray.getJSONObject(i).getString("date_rend"),
                                meetArray.getJSONObject(i).getString("heur"),
                                meetArray.getJSONObject(i).getString("nom")+" "+meetArray.getJSONObject(i).getString("prenom"),
                                meetArray.getJSONObject(i).getString("id_rend")
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
                return infos;
            }
        };
        request.setTag("TAG");
        queue.add(request);
    }
    public void updateStatut(Map<String, String> infos, SignUpCallBack callBack){

        String url = Config.URL+"/Model/medcin/update_statut.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error){
                        callBack.onSuccess("vous étes "+((infos.get("isOnline")=="1")?"online":"offline"));
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
    public void getVisites(String cin, LoadVisitCallBack callBack){

        String url = Config.URL+"/Model/patient/my_visites.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Visite> obj = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("TAG", "onResponse tani : "+jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.i("TAG", "onResponse tani tani: "+i);
                        Visite visite= new Visite(jsonArray.getJSONObject(i).getString("nom")+" "+jsonArray.getJSONObject(i).getString("prenom"),
                                jsonArray.getJSONObject(i).getString("date_visit")
                        );
                        if (jsonArray.getJSONObject(i).getBoolean("hasAnalyse"))
                            visite.setAnalyse(jsonArray.getJSONObject(i).getString("intitule_analyse"));
                        if (jsonArray.getJSONObject(i).getBoolean("hasAllergie"))
                            visite.setAllergie(jsonArray.getJSONObject(i).getString("intitule_allergie"));
                        if (jsonArray.getJSONObject(i).getBoolean("hasOrd")){
                            JSONArray medicamentsArray = jsonArray.getJSONObject(i).getJSONArray("medicaments");
                            for (int j = 0; j < medicamentsArray.length(); j++) {
                                visite.addMedicament(medicamentsArray.getJSONObject(j).getString("intitule_medicament"));
                            }
                        }

                        obj.add(visite);
                    }
                    callBack.onSuccess(obj);
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
    public void getAllergies(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/my_allergies.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    Vector<Allergie> obj = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //obj.add();
                        vector.add(new Allergie(jsonArray.getJSONObject(i).getString("intitule_allergie")));
                    }
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

    public void getAnalyses(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/my_analyses.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    Vector<Analyse> obj = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        /*
                        obj.add(new Analyse(jsonArray.getJSONObject(i).getString("intitule_analyse"),
                                jsonArray.getJSONObject(i).getString("date_analyse"),
                                jsonArray.getJSONObject(i).getString("fichier")
                                ));

                         */
                        vector.add(new Analyse(jsonArray.getJSONObject(i).getString("intitule_analyse"),
                                jsonArray.getJSONObject(i).getString("date_analyse"),
                                jsonArray.getJSONObject(i).getString("fichier")
                        ));
                    }
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
    public void getMedicaments(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/my_medicaments.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    Vector<Medicament> obj = new Vector<>();
                    //Vector<Notes.Note> notes = new Vector<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        /*
                        obj.add(new Medicament(jsonArray.getJSONObject(i).getString("intitule_medicament"),
                                jsonArray.getJSONObject(i).getString("nom")+" "+jsonArray.getJSONObject(i).getString("prenom"),
                                jsonArray.getJSONObject(i).getString("qte"),
                                jsonArray.getJSONObject(i).getString("date_ord"),
                                jsonArray.getJSONObject(i).getString("date_fin"),
                                jsonArray.getJSONObject(i).getString("prix")
                                ));

                         */
                        vector.add(new Medicament(jsonArray.getJSONObject(i).getString("intitule_medicament"),
                                jsonArray.getJSONObject(i).getString("nom")+" "+jsonArray.getJSONObject(i).getString("prenom"),
                                jsonArray.getJSONObject(i).getString("qte"),
                                jsonArray.getJSONObject(i).getString("date_ord"),
                                jsonArray.getJSONObject(i).getString("date_fin"),
                                jsonArray.getJSONObject(i).getString("prix")
                        ));
                    }
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

    public void getPositions(String cin, LoadHomeInfoCallBack callBack){

        String url = Config.URL+"/Model/patient/get_map_locations.php";

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG", "onResponse: "+response);
                    Vector<Object> vector = new Vector<>();
                    //Positions positions = new Positions();
                    JSONObject json = new JSONObject(response);
                    JSONArray jsonArray= json.getJSONArray("tab");
                    Positions positions = new Positions(json.getString("type"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        positions.addPosition(new Positions.Position(Float.parseFloat(jsonArray.getJSONObject(i).getString("x")),
                                Float.parseFloat(jsonArray.getJSONObject(i).getString("y")),
                                jsonArray.getJSONObject(i).getString("name")
                        ));
                    }
                    vector.add(positions);

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
    public void kill(){
        if (queue!=null){
            queue.cancelAll("TAG");
        }
    }
}
