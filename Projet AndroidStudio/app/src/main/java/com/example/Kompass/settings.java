package com.example.Kompass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

public class settings extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    private final static String FILE_NAME = "settings.txt";

    View navBar;
    int uiOptions;
    Button B_closeSettings;
    ImageView viewScreen;
    Button map_type;

    float zoomCheck;
    int mapType = 1;
    Button appliquer;
    Switch switch1;
    EditText editText;
    PopupMenu popupMenu;
    Boolean batterySaver = false;
    float zoomLevel = (float) 0.2;
    String settingsString;
    String textEditText = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    final static String sharedPreferencesName = "Kompass";
    String MasterKey;

    String token;

    private View.OnTouchListener Touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            navBar.setSystemUiVisibility(uiOptions);
            return true;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

        map_type = findViewById(R.id.map_type);
        appliquer = findViewById(R.id.appliquer);
        switch1 = findViewById(R.id.batterySaver);
        editText = findViewById(R.id.zoomLevel);

        B_closeSettings = findViewById(R.id.b_closeSettings);
        viewScreen = findViewById(R.id.Screen);

        viewScreen.setOnTouchListener(Touch);

        loadSettingsFile();


        appliquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomCheck = Float.parseFloat(editText.getText().toString());
                if (zoomCheck > 0 && zoomCheck < 1) {
                    zoomLevel = zoomCheck;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer une valeur entre 0 et 1.", Toast.LENGTH_SHORT).show();
                }
                batterySaver = switch1.isChecked();

                settingsString = (batterySaver + "\n" + zoomLevel + "\n" +  mapType);

                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write(settingsString.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch1.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Activer l'économiseur de batterie pourrait diminuer la précision du GPS.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        B_closeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), profil.class);
                intent.putExtra("token", token);
                view.getContext().startActivity(intent);
            }
        });
    }

    public void showPopup(View v) {
        popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick (MenuItem menuItem) {
        navBar.setSystemUiVisibility(uiOptions);
        switch(menuItem.getItemId()) {
            case R.id.item1:
                mapType = 1;
                map_type.setBackgroundResource(R.drawable.type_normal);
                return true;
            case R.id.item2:
                mapType = 3;
                map_type.setBackgroundResource(R.drawable.type_terrain);
                return true;
            case R.id.item3:
                mapType = 4;
                map_type.setBackgroundResource(R.drawable.type_hybrid);
                return true;
            default:
                return false;
        }
    }

    public void loadSettingsFile() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            for(int x = 0; x < 3; x++){
                if (x == 0) {batterySaver = Boolean.parseBoolean(br.readLine());}
                if (x == 1) {zoomLevel = Float.parseFloat(br.readLine());}
                if (x == 2) {mapType = Integer.parseInt(br.readLine());}
                textEditText = "" + zoomLevel;
                switch1.setChecked(batterySaver);
                editText.setText(textEditText);
                if (mapType == 1) {
                    map_type.setBackgroundResource(R.drawable.type_normal);
                }
                if (mapType == 3) {
                    map_type.setBackgroundResource(R.drawable.type_terrain);
                }
                if (mapType == 4) {
                    map_type.setBackgroundResource(R.drawable.type_hybrid);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void disconnect() {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove("token");
        sharedPreferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
        settings.this.startActivity(intent);
        finish();
        return;
    }
}