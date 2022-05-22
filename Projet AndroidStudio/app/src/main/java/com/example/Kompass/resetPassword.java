package com.example.Kompass;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class resetPassword extends AppCompatActivity {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    TextInputLayout textInputResetPassword;
    TextInputLayout textInputResetPasswordConfirm;
    Button buttonResetPassword;
    TextView textViewLog;
    TextView textViewConnect;

    RequestQueue queue;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        textInputResetPassword = findViewById(R.id.textInputResetPassword);
        textInputResetPasswordConfirm = findViewById(R.id.textInputResetPasswordConfirm);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        textViewLog = findViewById(R.id.textViewLog);
        textViewConnect = findViewById(R.id.textViewConnect);

        Uri uri = getIntent().getData();
        if (uri != null) {
            List<String> params = uri.getPathSegments();
            if (params.size()==1) {
                key = params.get(0);
            }
            else {
                Toast.makeText(getApplicationContext(), "lien de réinitialisation du mot de passe invalide", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                finish();
                return;

            }
        }

        queue = Volley.newRequestQueue(this);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLog.setText(null);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                reset();
            }
        });

        textViewConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConnectAccount.class);
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

    private boolean validatePassword() {
        String inputResetPassword = textInputResetPassword.getEditText().getText().toString().trim();
        if (inputResetPassword.isEmpty()) {
            textInputResetPassword.setError("le mot de passe ne peut pas être vide");
            return false;
        }
        else {
            textInputResetPassword.setError(null);
            return true;
        }
    }

    private boolean validatePasswordConfirm() {
        String inputResetPasswordConfirm = textInputResetPasswordConfirm.getEditText().getText().toString().trim();
        if (inputResetPasswordConfirm.isEmpty()) {
            textInputResetPasswordConfirm.setError("le mot de passe ne peut pas être vide");
            return false;
        }
        else if (!(textInputResetPasswordConfirm.getEditText().getText().toString().equals(textInputResetPassword.getEditText().getText().toString()))) {
            textInputResetPassword.setError("les 2 mots de passe ne correspondent pas");
            textInputResetPasswordConfirm.setError("les 2 mots de passe ne correspondent pas");
            return false;
        }
        else {
            textInputResetPasswordConfirm.setError(null);
            return true;
        }
    }

    private void reset() {
        if (isConnected()) {
            if (validatePassword() & validatePasswordConfirm()) {
                String urlConnect = getString(R.string.DATABASE_DOMAINE) + "resetPassword.php";

                String inputPassword = textInputResetPassword.getEditText().getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlConnect, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!Character.isLetter(response.charAt(0))) {
                            textViewLog.setText(response);
                        }
                        else {
                            Intent intent = new Intent(resetPassword.this, ConnectAccount.class);
                            intent.putExtra("username", response);
                            intent.putExtra("password", inputPassword);
                            resetPassword.this.startActivity(intent);
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
                        params.put("key", key);
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