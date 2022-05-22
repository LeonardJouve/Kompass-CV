package com.example.Kompass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {

    String url;
    RequestQueue queue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    final static String sharedPreferencesName = "Kompass";
    String MasterKey;

    public void sendJSONInventaire (String json, String token, Context context) {
        try {
            MasterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(sharedPreferencesName, MasterKey, context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        queue = Volley.newRequestQueue(context);
        url = context.getString(R.string.DATABASE_DOMAINE) + "sendJSONInventaire.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!Character.isLetter(response.charAt(0))) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.remove("token");
                    sharedPreferencesEditor.apply();
                    Intent intent = new Intent(context, ConnectAccount.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("json", json);
                params.put("token", token);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void setState (int progressSante, int progressFaim, int progressEndurance, String token, Context context) {
        queue = Volley.newRequestQueue(context);
        url = context.getString(R.string.DATABASE_DOMAINE) + "setState.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!Character.isLetter(response.charAt(0))) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.remove("token");
                    sharedPreferencesEditor.apply();
                    Intent intent = new Intent(context, ConnectAccount.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("vie", String.valueOf(progressSante));
                params.put("faim", String.valueOf(progressFaim));
                params.put("endurance", String.valueOf(progressEndurance));
                params.put("token", token);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
