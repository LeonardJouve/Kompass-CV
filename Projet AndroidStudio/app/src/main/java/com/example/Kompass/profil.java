package com.example.Kompass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class profil extends AppCompatActivity {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    final static String sharedPreferencesName = "Kompass";
    String MasterKey;

    String token;
    View navBar;
    int uiOptions;
    Button B_closeProfil, B_settings, B_help, B_update, buttonDisconnect;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        try {
            MasterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(sharedPreferencesName, MasterKey, getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        navBar = this.getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        navBar.setSystemUiVisibility(uiOptions);

        B_closeProfil = findViewById(R.id.b_closeProfil);
        B_settings = findViewById(R.id.b_settings);
        B_help = findViewById(R.id.b_help);
        B_update = findViewById(R.id.b_update);
        buttonDisconnect = findViewById(R.id.buttonDisconnect);

        if (getIntent().hasExtra("token")) {
            token = getIntent().getStringExtra("token");
        }
        else {
            disconnect();
        }

        B_closeProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("token", token);
                view.getContext().startActivity(intent);
            }
        });
        B_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), settings.class);
                intent.putExtra("token", token);
                view.getContext().startActivity(intent);
            }
        });
        B_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), help.class);
                intent.putExtra("token", token);
                view.getContext().startActivity(intent);
            }
        });
        B_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), update.class);
                intent.putExtra("token", token);
                view.getContext().startActivity(intent);
            }
        });

        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });
    }

    private void disconnect() {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove("token");
        sharedPreferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
        profil.this.startActivity(intent);
        finish();
        return;
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