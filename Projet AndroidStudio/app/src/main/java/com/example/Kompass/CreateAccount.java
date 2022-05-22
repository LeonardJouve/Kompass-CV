
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

public class CreateAccount extends AppCompatActivity {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    TextInputLayout textInputEmail;
    TextInputLayout textInputUsername;
    TextInputLayout textInputPassword;
    TextInputLayout textInputPasswordConfirm;
    Button buttonCreateAccount;
    TextView textViewLog;
    TextView textViewConnect;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputUsername = findViewById(R.id.textInputUsername);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputPasswordConfirm = findViewById(R.id.textInputPasswordConfirm);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        textViewLog = findViewById(R.id.textViewLog);
        textViewConnect = findViewById(R.id.textViewConnect);

        queue = Volley.newRequestQueue(this);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLog.setText(null);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                createAccount();
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
        String inputEmail = textInputEmail.getEditText().getText().toString().trim();
        if (inputEmail.isEmpty()) {
            textInputEmail.setError("l'adresse email ne peut pas être vide");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            textInputEmail.setError("veuillez entrer une adresse email valide");
            return false;
        }
        else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String inputUsername = textInputUsername.getEditText().getText().toString().trim();
        if (inputUsername.isEmpty()) {
            textInputUsername.setError("le nom d'utilisateur ne peut pas être vide");
            return false;
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(inputUsername).matches()) {
            textInputEmail.setError("le nom d'utilisateur n'est pas valide");
            return false;
        }
        else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String inputPassword = textInputPassword.getEditText().getText().toString().trim();
        if (inputPassword.isEmpty()) {
            textInputPassword.setError("le mot de passe ne peut pas être vide");
            return false;
        }
        else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean validatePasswordConfirm() {
        String inputPasswordConfirm = textInputPasswordConfirm.getEditText().getText().toString().trim();

        if (inputPasswordConfirm.isEmpty()) {
            textInputPasswordConfirm.setError("le mot de passe ne peut pas être vide");
            return false;
        }
        else if (!(textInputPasswordConfirm.getEditText().getText().toString().equals(textInputPassword.getEditText().getText().toString()))) {
            textInputPassword.setError("les 2 mots de passe ne correspondent pas");
            textInputPasswordConfirm.setError("les 2 mots de passe ne correspondent pas");
            return false;
        }
        else {
            textInputPasswordConfirm.setError(null);
            return true;
        }
    }

    private void createAccount() {
        if (isConnected()) {
            if (validateEmail() & validateUsername() & validatePassword() & validatePasswordConfirm()) {
                String inputEmail = textInputEmail.getEditText().getText().toString();
                String inputUsername = textInputUsername.getEditText().getText().toString();
                String inputPassword = textInputPassword.getEditText().getText().toString();

                String urlCreateAccount = getString(R.string.DATABASE_DOMAINE) + "createAccount.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCreateAccount, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {
                            if (!Character.isLetter(response.charAt(0))) {
                                /*erreur*/
                                textViewLog.setText(response);
                            }
                            else {
                                /*reponse*/
                                Intent intent = new Intent(CreateAccount.this, ConnectAccount.class);
                                intent.putExtra("username", inputUsername);
                                intent.putExtra("password", inputPassword);
                                intent.putExtra("log", response);
                                CreateAccount.this.startActivity(intent);
                                finish();
                                return;
                            }
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
                        params.put("username", inputUsername);
                        params.put("email", inputEmail);
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