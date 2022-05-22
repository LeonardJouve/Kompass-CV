package com.example.Kompass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Tablayout extends AppCompatActivity implements interfaceTablayout {

    /*backPressed*/
    long backPressedTime;
    Toast backPressedToast;

    View navBar;
    int uiOptions;

    ImageView imageViewBackMainActivity;
    ListItem listItem = new ListItem();
    TabLayout tabLayout;
    ViewPager viewPager;
    MainAdapter adapter;
    Bundle bundle;
    String JSONArrayInventaire;
    ArrayList<Item> ListInventaire = new ArrayList<>();
    int progressEndurance;
    int progressFaim;
    int progressSante;
    String jsonInventaire;
    String token;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    final static String sharedPreferencesName = "Kompass";
    String MasterKey;

    VolleyRequest volleyRequest = new VolleyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        try {
            MasterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(sharedPreferencesName, MasterKey, getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        navBar = this.getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        navBar.setSystemUiVisibility(uiOptions);

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

        JSONArrayInventaire = listItem.getItemToJsonInventaire(ListInventaire, getApplicationContext());

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        imageViewBackMainActivity = findViewById(R.id.imageViewBackMainActivity);
        adapter = new MainAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), Tablayout.this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        /*getVariable() pour la premiere fois*/
        Fragment fragmentTab1 = adapter.getItem(0);
        Tab1 tab1 = (Tab1) fragmentTab1;
        bundle = new Bundle();
        bundle.putString("JSONArrayInventaire" , JSONArrayInventaire);
        bundle.putInt("progressEndurance", progressEndurance);
        bundle.putInt("progressFaim", progressFaim);
        bundle.putInt("progressSante", progressSante);
        tab1.setArguments(bundle);

        imageViewBackMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("jsonInventaire", JSONArrayInventaire);
                intent.putExtra("token", token);
                volleyRequest.setState(progressSante, progressFaim, progressEndurance, token, getApplicationContext());
                v.getContext().startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                /*appelé à chaque changement d'onglet*/
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        if (imageViewBackMainActivity.getVisibility() == View.GONE) {
                            imageViewBackMainActivity.setVisibility(View.VISIBLE);
                        }
                        Fragment fragmentTab1 = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 0);
                        if (fragmentTab1 != null) {
                            Tab1 tab1 = (Tab1) fragmentTab1;
                            bundle = new Bundle();
                            bundle.putString("JSONArrayInventaire" , JSONArrayInventaire);
                            bundle.putInt("progressEndurance", progressEndurance);
                            bundle.putInt("progressFaim", progressFaim);
                            bundle.putInt("progressSante", progressSante);
                            tab1.setArguments(bundle);
                            tab1.getVariable();
                        }
                    case 1:
                        if (imageViewBackMainActivity.getVisibility() == View.GONE) {
                            imageViewBackMainActivity.setVisibility(View.VISIBLE);
                        }
                        Fragment fragmentTab2 = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 1);
                        if (fragmentTab2 != null) {
                            Tab2 tab2 = (Tab2) fragmentTab2;
                            bundle = new Bundle();
                            bundle.putString("JSONArrayInventaire" , JSONArrayInventaire);
                            bundle.putInt("progressEndurance", progressEndurance);
                            bundle.putInt("progressFaim", progressFaim);
                            bundle.putInt("progressSante", progressSante);
                            bundle.putBoolean("isImageViewBackMainActivityVisible", imageViewBackMainActivity.getVisibility() == View.VISIBLE);
                            tab2.setArguments(bundle);
                            tab2.getVariable();
                        }
                    case 2:
                        if (imageViewBackMainActivity.getVisibility() == View.GONE) {
                            imageViewBackMainActivity.setVisibility(View.VISIBLE);
                        }
                        Fragment fragmentTab3 = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + 2);
                        if (fragmentTab3 != null) {
                            Tab3 tab3 = (Tab3) fragmentTab3;
                            bundle = new Bundle();
                            bundle.putString("JSONArrayInventaire", JSONArrayInventaire);
                            bundle.putInt("progressEndurance", progressEndurance);
                            bundle.putString("token", token);
                            bundle.putInt("progressFaim", progressFaim);
                            bundle.putInt("progressSante", progressSante);
                            tab3.setArguments(bundle);
                            tab3.getVariable();
                        }
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void disconnect() {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove("token");
        sharedPreferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
        Tablayout.this.startActivity(intent);
        finish();
        return;
    }

    @Override
    public void onJsonReceived(String json) {
        JSONArrayInventaire = json;
        volleyRequest.sendJSONInventaire(json, token, getApplicationContext());
    }

    @Override
    public void onCraft(Boolean isCraft) {
        if (isCraft) {
            imageViewBackMainActivity.setVisibility(View.GONE);
        }
        else {
            imageViewBackMainActivity.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProgressUpdate(int progressEndurance, int progressFaim, int progressSante) {
        this.progressEndurance = progressEndurance;
        this.progressFaim = progressFaim;
        this.progressSante = progressSante;
        volleyRequest.setState(progressSante, progressFaim, progressEndurance, token, getApplicationContext());
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