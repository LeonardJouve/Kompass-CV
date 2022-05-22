package com.example.Kompass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class ConnectAccount extends AppCompatActivity {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    TextInputLayout textInputEmailUsername;
    TextInputLayout textInputPassword;
    Button buttonConnect;
    TextView textViewLog;
    TextView textViewCreateAccount;
    TextView textViewForgotPassword;
    CheckBox checkBoxSaveLogins;

    RequestQueue queue;
    SharedPreferences sharedPreferences;
    final static String sharedPreferencesName = "Kompass";

    String MasterKey;
    String inputUsername;
    String inputEmail;
    String inputPassword;
    String token = "";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_account);

        textInputEmailUsername = findViewById(R.id.textInputEmailUsername);
        textInputPassword = findViewById(R.id.textInputPassword);
        buttonConnect = findViewById(R.id.buttonConnect);
        textViewLog = findViewById(R.id.textViewLog);
        textViewCreateAccount = findViewById(R.id.textViewCreateAccount);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        checkBoxSaveLogins = findViewById(R.id.checkBoxSaveLogins);

        queue = Volley.newRequestQueue(this);

        try {
            MasterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(sharedPreferencesName, MasterKey, getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            if (sharedPreferences.contains("token")) {
                connectToken();
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        if (getIntent().hasExtra("username")) {
            inputUsername = getIntent().getStringExtra("username");
            textInputEmailUsername.getEditText().setText(inputUsername);
        }
        if (getIntent().hasExtra("password")) {
            inputPassword = getIntent().getStringExtra("password");
            textInputPassword.getEditText().setText(inputPassword);
        }
        if (getIntent().hasExtra("log")) {
            textViewLog.setText(getIntent().getStringExtra("log"));
        }

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLog.setText(null);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                connect();
            }
        });

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAccount.class);
                v.getContext().startActivity(intent);
                finish();
                return;
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), forgotPassword.class);
                v.getContext().startActivity(intent);
                finish();
                return;
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && ((networkInfo.getType() == ConnectivityManager.TYPE_WIFI) || (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE));
    }

    private boolean validateEmailUsername() {
        String inputEmailUsername = textInputEmailUsername.getEditText().getText().toString().trim();
        if (inputEmailUsername.isEmpty()) {
            textInputEmailUsername.setError("le nom d'utilisateur / email ne peut pas être vide");
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(textInputEmailUsername.getEditText().getText().toString()).matches()) {
            textInputEmailUsername.setError(null);
            inputEmail = textInputEmailUsername.getEditText().getText().toString();
            inputUsername = "";
            return true;
        }
        else {
            textInputEmailUsername.setError(null);
            inputUsername = textInputEmailUsername.getEditText().getText().toString();
            inputEmail = "";
            return true;
        }
    }

    private boolean validatePassword() {
        inputPassword = textInputPassword.getEditText().getText().toString().trim();
        if (inputPassword.isEmpty()) {
            textInputPassword.setError("le mot de passe ne peut pas être vide");
            return false;
        }
        else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private void connectToken() {
        if (isConnected()) {
            url = getString(R.string.DATABASE_DOMAINE) + "validateToken.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    else {
                        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                        sharedPreferencesEditor.remove("token");
                        sharedPreferencesEditor.apply();
                        textViewLog.setText(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.remove("token");
                    sharedPreferencesEditor.apply();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    token = sharedPreferences.getString("token", null);
                    params.put("token", token);
                    return params;
                }
            };
            queue.add(stringRequest);
        }
        else {
            textViewLog.setText("la connection à internet a échouée");
        }
    }

    private void connect() {
        if (isConnected()) {
            if ((validateEmailUsername() & validatePassword())) {
                url = getString(R.string.DATABASE_DOMAINE) + "connectAccount.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!Character.isLetter(response.charAt(0))) {
                            /*erreur*/
                            textViewLog.setText(response);
                        }
                        else {
                            token = response;
                            if (checkBoxSaveLogins.isChecked()) {
                                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                                sharedPreferencesEditor.putString("token", token);
                                sharedPreferencesEditor.apply();
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        if (!inputUsername.isEmpty()) {
                            params.put("username", inputUsername);
                        } else if (!inputEmail.isEmpty()) {
                            params.put("email", inputEmail);
                        }
                        params.put("password", inputPassword);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        }
        else {
            textViewLog.setText("la connection à internet a échouée");
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backPressedToast.cancel();
            finishAffinity();
            finish();
        }
        else {
            backPressedToast = Toast.makeText(getApplicationContext(), "Appuyez à nouveau sur retour pour quitter l'application", Toast.LENGTH_SHORT);
            backPressedToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}