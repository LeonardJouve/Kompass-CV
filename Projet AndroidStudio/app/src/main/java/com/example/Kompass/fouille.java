package com.example.Kompass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class fouille extends AppCompatActivity {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    View navBar;
    int uiOptions;

    ListItem listItem = new ListItem();
    ArrayList<Integer> ressourceIdList = listItem.getRessourceIdList();

    Button buttonFouille;
    ImageView imageViewInventaire;
    ImageView imageViewAnim;
    TextView textViewAnim;

    int progressEndurance, progressFaim, progressSante;

    String jsonInventaire, token;
    ArrayList<Item> ListInventaire = new ArrayList<>();
    ArrayList<Item> droppedItem = new ArrayList<>();
    int nbrAnim = 0;
    Animation animDrop;
    Boolean animEnd = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    final static String sharedPreferencesName = "Kompass";
    String MasterKey;
    VolleyRequest volleyRequest = new VolleyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fouille);

        navBar = this.getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        navBar.setSystemUiVisibility(uiOptions);

        try {
            MasterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(sharedPreferencesName, MasterKey, getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        if (getIntent().hasExtra("jsonInventaire")) {
            ListInventaire = new ArrayList<>();
            jsonInventaire = getIntent().getStringExtra("jsonInventaire");
            ListInventaire = new ArrayList<>(listItem.getJsonToItemInventaire(jsonInventaire, getApplicationContext()));
        }
        if (getIntent().hasExtra("progressEndurance")) {
            progressEndurance = getIntent().getIntExtra("progressEndurance", 0);
        }
        if (getIntent().hasExtra("progressFaim")) {
            progressFaim = getIntent().getIntExtra("progressFaim", 0);
        }
        if (getIntent().hasExtra("progressSante")) {
            progressSante = getIntent().getIntExtra("progressSante", 0);
        }
        if (getIntent().hasExtra("token")) {
            token = getIntent().getStringExtra("token");
        }
        else {
            disconnect();
        }

        buttonFouille = findViewById(R.id.buttonFouille);
        imageViewInventaire = findViewById(R.id.imageViewInventaire);
        imageViewAnim = findViewById(R.id.imageViewAnim);
        textViewAnim = findViewById(R.id.textViewAnim);
        animDrop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_drop);

        animDrop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageViewAnim.setVisibility(View.VISIBLE);
                textViewAnim.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /*animEnd pour annuler 1 passage sur 2 -> anim textView + imageView*/
                if (animEnd) {
                    imageViewAnim.setVisibility(View.GONE);
                    textViewAnim.setVisibility(View.GONE);
                    if (nbrAnim < droppedItem.size()) {
                        imageViewAnim.setImageResource(droppedItem.get(nbrAnim).getImageItem());
                        textViewAnim.setText(""+droppedItem.get(nbrAnim).getNbrItem());
                        imageViewAnim.startAnimation(animDrop);
                        textViewAnim.startAnimation(animDrop);
                        nbrAnim++;
                        animEnd = false;
                    }
                }
                else {
                    animEnd = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        buttonFouille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                droppedItem = new ArrayList<>();
                nbrAnim = 0;
                /*récupérer chance de equipement*/
                int chance = random(4);
                for (int i = 0; i < chance; i++) {
                    int quantite = random(6);
                    int ressourceId = ressourceIdList.get(random(ressourceIdList.size())-1);
                    Metadata metadata = new Metadata(new Nourriture(random(10), random(10), random(10)), new Equipement(random(10), random(10), random(10), random(10), random(10), random(10), random(10), random(10)), new Arme(random(10), random(10), random(10), random(10)), new Outil(random(10), random(10), random(10)), new Marteau(random(10), random(10)));
                    ListInventaire.add(new Item(quantite, listItem.getItem(ressourceId).getImageItem(), listItem.getItem(ressourceId).getImageCadre(), ressourceId, listItem.getItem(ressourceId).getNomRessource(), listItem.getItem(ressourceId).getDescriptionRessource(), metadata));
                    droppedItem.add(listItem.noReferenceCopy(ListInventaire.get(ListInventaire.size() - 1)));
                    droppedItem = new ArrayList<>(listItem.groupInventaire(droppedItem));
                    ListInventaire = new ArrayList<>(listItem.sortInventaire(ListInventaire));
                }
                imageViewAnim.setImageResource(droppedItem.get(nbrAnim).getImageItem());
                textViewAnim.setText(""+droppedItem.get(nbrAnim).getNbrItem());
                imageViewAnim.startAnimation(animDrop);
                textViewAnim.startAnimation(animDrop);
                nbrAnim++;
                animEnd = true;
                volleyRequest.sendJSONInventaire(jsonInventaire, token, getApplicationContext());
            }
        });

        imageViewInventaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListInventaire = new ArrayList<>(listItem.sortInventaire(ListInventaire));
                jsonInventaire = listItem.getItemToJsonInventaire(ListInventaire, getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), Tablayout.class);
                intent.putExtra("jsonInventaire", jsonInventaire);
                intent.putExtra("token", token);
                intent.putExtra("progressEndurance", progressEndurance);
                intent.putExtra("progressFaim", progressFaim);
                intent.putExtra("progressSante", progressSante);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private int random(int max) {
        int bonus = (int)(Math.random() * max) + 1;
        return bonus;
    }

    private void disconnect() {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove("token");
        sharedPreferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
        fouille.this.startActivity(intent);
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