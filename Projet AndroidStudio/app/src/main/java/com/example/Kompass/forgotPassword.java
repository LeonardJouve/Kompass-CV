package com.example.Kompass;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
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
import java.util.Map;

public class forgotPassword extends AppCompatActivity {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    TextInputLayout textInputForgotpasswordEmail;
    Button buttonForgotPassword;
    TextView textViewLog;
    TextView textViewConnect;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textInputForgotpasswordEmail = findViewById(R.id.textInputForgotpasswordEmail);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);
        textViewLog = findViewById(R.id.textViewLog);
        textViewConnect = findViewById(R.id.textViewConnect);

        queue = Volley.newRequestQueue(this);

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLog.setText(null);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                forgotPassword();
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

    private boolean validateEmail() {
        String inputForgotEmail = textInputForgotpasswordEmail.getEditText().getText().toString().trim();
        if (inputForgotEmail.isEmpty()) {
            textInputForgotpasswordEmail.setError("l'adresse email ne peut pas être vide");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(inputForgotEmail).matches()) {
            textInputForgotpasswordEmail.setError("veuillez entrer une adresse email valide");
            return false;
        }
        else {
            textInputForgotpasswordEmail.setError(null);
            return true;
        }
    }

    private void forgotPassword() {
        if (isConnected()) {
            if (validateEmail()) {
                String urlConnect = getString(R.string.DATABASE_DOMAINE) + "forgotPassword.php";

                String inputEmail = textInputForgotpasswordEmail.getEditText().getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlConnect, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {
                            textViewLog.setText(response);
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
                        params.put("email", inputEmail);
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