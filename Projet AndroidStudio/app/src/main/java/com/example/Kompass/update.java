package com.example.Kompass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class update extends AppCompatActivity {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    TextView text;
    String content;
    String url = "https://leonardjouve8.wixsite.com/jeumobile";
    Document doc;

    View navBar;
    int uiOptions;
    Button B_closeUpdate;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    final static String sharedPreferencesName = "Kompass";
    String MasterKey;

    String token;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        try {
            MasterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(sharedPreferencesName, MasterKey, getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        if (getIntent().hasExtra("token")) {
            token = getIntent().getStringExtra("token");
        }
        else {
            disconnect();
        }

        navBar = this.getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        navBar.setSystemUiVisibility(uiOptions);

        B_closeUpdate = (Button) findViewById(R.id.b_closeUpdate);
        text = (TextView) findViewById(R.id.text);

        new getText().execute();

        B_closeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), profil.class);
                intent.putExtra("token", token);
                view.getContext().startActivity(intent);
            }
        });
    }

    private class getText extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                doc = Jsoup.connect(url).get();
                content = doc.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            text.setText(content);
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

    private void disconnect() {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove("token");
        sharedPreferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
        update.this.startActivity(intent);
        finish();
        return;
    }
}