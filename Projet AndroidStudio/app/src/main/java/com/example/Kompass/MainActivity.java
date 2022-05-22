package com.example.Kompass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener, MapWrapperLayout.OnDragListener, GoogleMap.OnMarkerClickListener {

    /*backPressed*/
    long backPressedTime;
    Toast toast;

    /*map*/
    CustomMapFragment customMapFragment;
    private GoogleMap mmap;
    /*camera*/
    int tilt = 50; /*50 start*/
    CameraPosition c_localisation;
    GroundOverlay groundOverlayPlayer;
    GroundOverlay groundOverlayHalo;

    /*localisation*/
    private FusedLocationProviderClient fusedLocationClient;
    LatLng co;
    double latitude = 0;
    double longitude = 0;
    private Handler handler = new Handler(); /*timer localisation*/
    private static final int request_code_loc_perm = 101; /*request code*/
    Boolean locationPermissionsGranted = false;
    private static final String KEY_CAMERA = "camera";
    private static final String KEY_LOCATION = "location";
    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location: locationResult.getLocations()) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            updateMarker();
        }
    };

    /*Ui-settings*/
    int uiOptions; /*-> UiSettings phone*/
    View navBar;
    Display display;
    int ScreenWidth;
    int ScreenHeight;
    UiSettings uiSettings; /*-> UiSettings map*/

    /*settings*/
    boolean sett_autorot = false;
    private final static String FILE_NAME = "settings.txt";

    Boolean batterySaver = false;
    float zoomLevel = (float) 18.2; /*18.2f start*/
    int mapType = 1;

    /*view*/
    ImageView boussole;
    ImageView cadenas;
    Button map_type;
    Button B_profil;
    Button B_sac;
    ImageView imageViewAnimFouille;
    TextView textViewAnimFouille;
    SemiCircleArcProgressBar progressBarEndurance, progressBarFaim, progressBarSante;

    /*rotation*/
    int oldX;
    int oldY;
    int newX;
    int newY;
    int diffX;
    int diffY;
    int a = 2; /*-> vitesse de rotation*/
    int angle = 0;
    int bearing = 0;
    String direcTouch;
    String posTouch;
    String sensRotation;

    /*orientation*/
    SensorManager sensorManager;
    Sensor sensor;
    int degree;
    int startDegree = 0;
    int deg_boussole = 0;
    int deg_halo = 0;

    /*Places*/
    String urlPlaces;
    String startUrlPlaces = "https://api.opentripmap.com/0.1/en/places/radius?radius=100&lat=";
    String lonUrlPlaces = "&lon=";
    String endUrlPlaces = "&limit=100&kinds=interesting_places,sport,tourist_facilities,adult,amusements,accomodations&format=json&apikey=";
    Button fouille;
    RequestQueue queue;

    Double distClosest;
    String nameClosest;
    String kindsClosest;
    Double latClosest, lonClosest;

    Double dist;
    String name;
    String kinds;
    Double lat;
    Double lon;

    Double distMin = 30.0;

    String kFood = "foods";
    String kShop = "shops";
    String kTransport = "transport";
    String kBank = "banks";
    String kNatural = "natural";
    String kAccomodations = "accomodations";
    String kIndustrial = "industrial_facilities";
    String kReligion = "religion";
    String kSport = "sport";
    String kAmusements = "amusements";
    String kAdult = "adult";

    ArrayList<Marker> arrayMarker = new ArrayList<>();
    Circle circle;
    BitmapDescriptor bitmapDescriptor;

    /*fouille*/
    ArrayList<Item> droppedItem = new ArrayList<>();
    int nbrAnim = 0;
    Animation animDrop;
    Boolean animEnd = false;
    ArrayList<Integer> ressourceIdList;

    /*Datebase*/
    String token = "";
    String url;

    /*meteo*/
    String startUrlWeather = "https://api.openweathermap.org/data/2.5/weather?lat=";
    String lonUrlWeather = "&lon=";
    String endUrlWeather = "&units=metric&lang=fr&appid=";
    String urlWeather;
    String country;
    String ville;
    String icon;
    String description;
    String temperature;
    ImageView weather;
    ImageView bg_weather;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    final static String sharedPreferencesName = "Kompass";
    String MasterKey;

    int progressEndurance, progressFaim, progressSante, secondeProgressBar;

    ConstraintLayout ecran1;
    ConstraintLayout ecran2;

    /*Inventaire*/
    ListItem listItem = new ListItem();
    String JSONArrayInventaire;
    ArrayList<Item> ListInventaire = new ArrayList<>(Arrays.asList(listItem.getItem(600)));
    VolleyRequest volleyRequest = new VolleyRequest();

    /*test*/
    boolean aBoolean = true;
    int chiffre = 1;

    //rotation de la carte
    @Override
    public void onDrag(MotionEvent motionEvent) {
        if (locationPermissionsGranted) {
            navBar.setSystemUiVisibility(uiOptions);
            if (!sett_autorot) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        newX = (int) motionEvent.getX();
                        newY = (int) motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        oldX = newX;
                        oldY = newY;
                        newX = (int) motionEvent.getX();
                        newY = (int) motionEvent.getY();

                        if (oldX > (ScreenWidth / 2)) {
                                //*droite*//*
                            if (oldY > (ScreenHeight / 2)) {
                                posTouch = "bas-droite";
                                    //*droite-bas*//*
                            } else {
                                posTouch = "haut-droite";
                                    //*droite-haut*//*
                            }
                        } else {
                                //*gauche*//*
                            if (oldY > (ScreenHeight / 2)) {
                                posTouch = "bas-gauche";
                                    //*gauche-bas*//*
                            } else {
                                posTouch = "haut-gauche";
                                    //*gauche-haut*//*
                            }
                        }

                        diffX = oldX - newX;
                        diffY = oldY - newY;

                        if (diffX != diffY) {
                            if (Math.abs(diffX) > Math.abs(diffY)) {
                                if (oldX > newX) {
                                    direcTouch = "gauche";
                                        //*vers la gauche*//*
                                } else {
                                    direcTouch = "droite";
                                        //*vers la droite*//*
                                }
                            } else {
                                if (oldY > newY) {
                                    direcTouch = "haut";
                                        //*vers le haut*//*
                                } else {
                                    direcTouch = "bas";
                                        //*vers le bas*//
                                }
                            }
                            if ((direcTouch == "gauche" && posTouch == "bas-droite") || (direcTouch == "gauche" && posTouch == "bas-gauche") || (direcTouch == "droite" && posTouch == "haut-gauche") || (direcTouch == "droite" && posTouch == "haut-droite") || (direcTouch == "haut" && posTouch == "bas-gauche") || (direcTouch == "haut" && posTouch == "haut-gauche") || (direcTouch == "bas" && posTouch == "haut-droite") || (direcTouch == "bas" && posTouch == "bas-droite")) {
                                sensRotation = "-";
                                if (angle + a > 360) {
                                    angle -= 360;
                                    angle += a;
                                    bearing = -angle;
                                    if (!sett_autorot) {
                                        deg_boussole -= 360;
                                        deg_boussole += a;
                                        deg_halo -= 360;
                                        deg_halo += a;
                                        boussole.setRotation(deg_boussole);
                                        groundOverlayPlayer.setBearing(bearing);
                                        //halo.setRotation(deg_halo);
                                    }
                                    updateMarker();
                                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(c_localisation));
                                } else {
                                    angle += a;
                                    bearing = -angle;
                                    if (!sett_autorot) {
                                        deg_boussole += a;
                                        deg_halo += a;
                                        boussole.setRotation(deg_boussole);
                                        groundOverlayPlayer.setBearing(bearing);
                                        //halo.setRotation(deg_halo);
                                    }
                                    updateMarker();
                                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(c_localisation));
                                }

                            } else {
                                sensRotation = "+";
                                if (angle - a < 0) {
                                    angle += 360;
                                    angle -= a;
                                    bearing = -angle;
                                    if (!sett_autorot) {
                                        deg_boussole += 360;
                                        deg_boussole -= a;
                                        deg_halo -= a;
                                        boussole.setRotation(deg_boussole);
                                        groundOverlayPlayer.setBearing(bearing);
                                        //halo.setRotation(deg_halo);
                                    }
                                    updateMarker();
                                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(c_localisation));
                                } else {
                                    angle -= a;
                                    bearing = -angle;
                                    if (!sett_autorot) {
                                        deg_boussole -= a;
                                        deg_halo -= 360;
                                        deg_halo -= a;
                                        boussole.setRotation(deg_boussole);
                                        groundOverlayPlayer.setBearing(bearing);
                                        //halo.setRotation(deg_halo);
                                    }
                                    updateMarker();
                                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(c_localisation));
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            }
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            c_localisation = savedInstanceState.getParcelable(KEY_CAMERA);
            co = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        endUrlPlaces += getString(R.string.APIKEY_OpenTripMap);
        endUrlWeather += getString(R.string.APIKEY_OpenWeatherMap);
        animDrop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_drop);

        /*Ui-settings*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        navBar = this.getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        navBar.setSystemUiVisibility(uiOptions);
        /*screen*/
        display = getWindowManager().getDefaultDisplay();
        ScreenWidth = display.getWidth();
        ScreenHeight = display.getHeight();
        /*orientation*/
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        /*ImageView*/
        boussole = findViewById(R.id.boussole);
        cadenas = findViewById(R.id.cadenas);
        /*button*/
        map_type = findViewById(R.id.map_type);
        B_profil = findViewById(R.id.b_profil);
        B_sac = findViewById(R.id.b_sac);
        weather = findViewById(R.id.weather);
        bg_weather = findViewById(R.id.bg_weather);

        /*Places*/
        imageViewAnimFouille = findViewById(R.id.imageViewAnimFouille);
        textViewAnimFouille = findViewById(R.id.textViewAnimFouille);
        fouille = findViewById(R.id.fouille);

        progressBarEndurance = findViewById(R.id.progressBarEndurance);
        progressBarFaim = findViewById(R.id.progressBarFaim);
        progressBarSante = findViewById(R.id.progressBarSante);
        try {
            MasterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(sharedPreferencesName, MasterKey, getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        queue = Volley.newRequestQueue(this);

        /*Tablayout*/
        ressourceIdList = listItem.getRessourceIdList();
        ListInventaire.get(0).setNbrItem(1);
        JSONArrayInventaire = listItem.getItemToJsonInventaire(ListInventaire, getApplicationContext());

        if (getIntent().hasExtra("jsonInventaire")) {
            ListInventaire = new ArrayList<>();
            String jsonInventaire = getIntent().getStringExtra("jsonInventaire");
            ListInventaire = new ArrayList<>(listItem.getJsonToItemInventaire(jsonInventaire, getApplicationContext()));
        }
        if (getIntent().hasExtra("token")) {
            token = getIntent().getStringExtra("token");
        } else {
            disconnect();
        }

        url = getString(R.string.DATABASE_DOMAINE) + "getJSONInventaire.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    if (!Character.isLetter(response.charAt(0))) {
                        ListInventaire = listItem.getJsonToItemInventaire(response, getApplicationContext());
                        JSONArrayInventaire = listItem.getItemToJsonInventaire(ListInventaire, getApplicationContext());
                    } else {
                        disconnect();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                disconnect();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        queue.add(stringRequest);
        url = getString(R.string.DATABASE_DOMAINE) + "getState.php";
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().isEmpty()) {
                    if (!Character.isLetter(response.charAt(0))) {
                        ArrayList<Integer> arrayState = new ArrayList<Integer>();
                        int start = 0;
                        int end = 0;
                        boolean first = true;
                        for (int i = 0; i < response.length(); i++) {
                            if (Character.isDigit(response.charAt(i))) {
                                if (first) {
                                    start = i;
                                    first = false;
                                }
                            }
                            else {
                                end = i;
                                arrayState.add(Integer.parseInt(String.valueOf(response.subSequence(start, end))));
                                first = true;
                            }
                        }
                        arrayState.add(Integer.parseInt(String.valueOf(response.subSequence(start, response.length()))));
                        if (arrayState.size() == 3) {
                            progressSante = arrayState.get(0);
                            progressFaim = arrayState.get(1);
                            progressEndurance = arrayState.get(2);
                            progressBarEndurance.setPercent(progressEndurance);
                            progressBarFaim.setPercent(progressFaim);
                            progressBarSante.setPercent(progressSante);
                        }
                        else {
                            disconnect();
                        }
                    } else {
                        disconnect();
                    }
                }
                else {
                    disconnect();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                disconnect();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        queue.add(stringRequest2);

        /*rotation + touch_screen*/
        ecran1 = findViewById(R.id.ecran1);
        ecran2 = findViewById(R.id.ecran2);

        /*ecran2.setOnTouchListener(Touch);*/

        checkPermissions();

        /*settings*/
        loadSettingsFile();

        /*localisation*/
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        if (batterySaver) {
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
        else {
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(2000);

        /*onButtonClickListener*/
        fouille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kindsClosest != null & progressEndurance >= 5) {
                    url = getString(R.string.DATABASE_DOMAINE) + "getJSONFouille.php";
                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (!Character.isLetter(response.charAt(0))) {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
                                    url = getString(R.string.DATABASE_DOMAINE) + "sendJSONFouille.php";
                                    String currentDate = ZonedDateTime.now(ZoneId.of("Europe/Paris")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                    Date date = dateFormat.parse(currentDate);
                                    ArrayList<Double> listLat = new ArrayList<>();
                                    ArrayList<Double> listLon = new ArrayList<>();
                                    ArrayList<String> listDate = new ArrayList<>();
                                    Boolean isFouille;
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            try {
                                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                                String currentObjectDate = currentObject.getString("date");
                                                Date jsonDate = dateFormat.parse(currentObjectDate);
                                                if (jsonDate.after(date)) {
                                                    /*POI en cooldown*/
                                                    Double currentObjectLatitude = currentObject.getDouble("latitude");
                                                    Double currentObjectLongitude = currentObject.getDouble("longitude");
                                                    listLat.add(currentObjectLatitude);
                                                    listLon.add(currentObjectLongitude);
                                                    listDate.add(currentObjectDate);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (!listLat.contains(latClosest) & !listLon.contains(lonClosest)) {
                                            /*Fouille réalisée*/
                                            listLat.add(latClosest);
                                            listLon.add(lonClosest);
                                            Calendar c = Calendar.getInstance();
                                            c.setTime(date);
                                            c.add(Calendar.MINUTE, 5);
                                            Date expDate = c.getTime();
                                            String strExpDate = dateFormat.format(expDate);
                                            listDate.add(strExpDate);
                                            isFouille = true;
                                        } else {
                                            toaster("Ce point d'intérêt a déjà été fouillé, veuillez réessayez dans un moment.", 1);
                                            isFouille = false;
                                        }
                                        StringBuilder json = new StringBuilder("[");
                                        for (int i = 0; i < listDate.size(); i++) {
                                            json.append("{\"latitude\": " + listLat.get(i) + ", \"longitude\": " + listLon.get(i) + ", \"date\": \"" + listDate.get(i) + "\"}");
                                            if (i != listDate.size() - 1) {
                                                json.append(",");
                                            }
                                        }
                                        json.append("]");
                                        String jsonString = String.valueOf(json);
                                        String TOKEN = token;
                                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if (Character.isLetter(response.charAt(0))) {
                                                    if (isFouille) {
                                                        onFouille();
                                                    }
                                                } else {
                                                    /*erreur*/
                                                    toaster("" + response, 1);
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                toaster("problème rencontré lors de la fouille", 1);
                                                error.printStackTrace();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<>();
                                                params.put("json", jsonString);
                                                params.put("token", TOKEN);
                                                params.put("isFouille", String.valueOf(isFouille));
                                                return params;
                                            }
                                        };
                                        queue.add(stringRequest2);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    /*erreur*/
                                    toaster("" + response, 1);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Log.d("fouille", "" + e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            toaster("problème rencontré lors de la fouille: " + error, 1);
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("latitude", String.valueOf(latClosest));
                            params.put("longitude", String.valueOf(lonClosest));
                            params.put("token", token);
                            return params;
                        }
                    };
                    queue.add(stringRequest1);
                } else {
                    toaster("Vous êtes trop fatigué pour continuer vos recherches", 0);
                }
            }
        });

        animDrop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageViewAnimFouille.setVisibility(View.VISIBLE);
                textViewAnimFouille.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /*animEnd pour annuler 1 passage sur 2 -> anim textView + imageView*/
                if (animEnd) {
                    imageViewAnimFouille.setVisibility(View.GONE);
                    textViewAnimFouille.setVisibility(View.GONE);
                    if (nbrAnim < droppedItem.size()) {
                        imageViewAnimFouille.setImageResource(droppedItem.get(nbrAnim).getImageItem());
                        textViewAnimFouille.setText("" + droppedItem.get(nbrAnim).getNbrItem());
                        imageViewAnimFouille.startAnimation(animDrop);
                        textViewAnimFouille.startAnimation(animDrop);
                        nbrAnim++;
                        animEnd = false;
                    }
                } else {
                    animEnd = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        progressBarSante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toaster("Santé: " + progressSante + "\nFaim: " + progressFaim + "\nEndurance: " + progressEndurance, 0);
            }
        });
        progressBarFaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toaster("Santé: " + progressSante + "\nFaim: " + progressFaim + "\nEndurance: " + progressEndurance, 0);
            }
        });
        progressBarEndurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toaster("Santé: " + progressSante + "\nFaim: " + progressFaim + "\nEndurance: " + progressEndurance, 0);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temperature != null) {
                    toaster(temperature + " " + description + "\n" + country + " " + ville, 0);
                }
            }
        });

        B_sac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Tablayout.class);
                intent.putExtra("jsonInventaire", JSONArrayInventaire);
                intent.putExtra("token", token);
                intent.putExtra("progressEndurance", progressEndurance);
                intent.putExtra("progressFaim", progressFaim);
                intent.putExtra("progressSante", progressSante);
                view.getContext().startActivity(intent);
            }
        });

        B_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), profil.class);
                intent.putExtra("token", token);
                view.getContext().startActivity(intent);
            }
        });
        boussole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sett_autorot) {
                    angle = 0;
                    bearing = 0;
                    deg_boussole = 0;
                    deg_halo = degree - bearing;
                    groundOverlayPlayer.setBearing(bearing);
                    boussole.setRotation(deg_boussole);
                    updateMarker();
                }
            }
        });
        cadenas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sett_autorot) {
                    sett_autorot = false;
                    deg_boussole = 0;
                    bearing = 0;
                    angle = 0;
                    deg_halo = degree - bearing;
                    groundOverlayPlayer.setBearing(bearing);
                    boussole.setRotation(deg_boussole);
                    boussole.setVisibility(View.VISIBLE);
                    updateMarker();
                } else {
                    sett_autorot = true;
                    boussole.setVisibility(View.GONE);
                    groundOverlayPlayer.setBearing(bearing);
                }
            }
        });
        startTimerOther();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mmap != null) {
            outState.putParcelable(KEY_CAMERA, mmap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, co);
        }
    }

    private void disconnect() {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove("token");
        sharedPreferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
        MainActivity.this.startActivity(intent);
        finish();
        return;
    }

    /*map*/
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;

        customMapFragment.setOnDragListener(this);
        mmap.setOnMarkerClickListener(this);

        try {
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        if (mmap.getMapType() != mapType) {
            mmap.setMapType(mapType);
        }
        if (mmap.getMapType() == 1) {
            tilt = 50;
        }
        if (mmap.getMapType() == 3) {
            tilt = 50;
        }
        if (mmap.getMapType() == 4) {
            tilt = 0;
        }
        
        if (locationPermissionsGranted) {
            Task<Location> locationTask = fusedLocationClient.getLastLocation();
            locationTask.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            if (co == null) {
                                co = new LatLng(latitude, longitude);
                            }
                            if (c_localisation == null) {
                                c_localisation = new CameraPosition.Builder()
                                        .target(co)
                                        .zoom(zoomLevel)
                                        .bearing(bearing)
                                        .tilt(tilt)
                                        .build();
                            }
                            groundOverlayHalo = mmap.addGroundOverlay(new GroundOverlayOptions()
                                    .image(BitmapDescriptorFactory.fromResource(R.drawable.halo4))
                                    .anchor(0.5f, 0.5f)
                                    .position(co, 35, 35f));
                            groundOverlayPlayer = mmap.addGroundOverlay(new GroundOverlayOptions()
                                    .image(BitmapDescriptorFactory.fromResource(R.drawable.marker_location2))
                                    .anchor(0.5f, 0.5f)
                                    .position(co, 20f, 20f));

                            mmap.moveCamera(CameraUpdateFactory.newCameraPosition(c_localisation));
                            circle = mmap.addCircle(new CircleOptions()
                                    .center(co)
                                    .radius(100)
                                    .strokeWidth(7)
                                    .zIndex(0));
                        }
                    }
                }
            });
        }
        else {
            Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
            getApplicationContext().startActivity(intent);
        }

        /*uiSettings*/
        uiSettings = mmap.getUiSettings();
        uiSettings.setAllGesturesEnabled(false);

        checkSettingsAndStartLocationUpdates();
    }

    /*localisation*/
    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = settingsClient.checkLocationSettings(locationSettingsRequest);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(MainActivity.this, 1000);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void updateMarker() {
        co = new LatLng(latitude, longitude);
        c_localisation = new CameraPosition.Builder()
                .target(co)
                .zoom(zoomLevel)
                .bearing(bearing)
                .tilt(tilt)
                .build();
        mmap.animateCamera(CameraUpdateFactory.newCameraPosition(c_localisation));
        groundOverlayPlayer.setPosition(co);
        groundOverlayHalo.setPosition(co);
        circle.setCenter(co);
    }
    
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionsGranted = true;
            /*map*/
            customMapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            assert customMapFragment != null;
            customMapFragment.getMapAsync(this);
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permissions requises")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                checkPermissions();
                            }
                        })
                        .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(getApplicationContext(), ConnectAccount.class);
                                getApplicationContext().startActivity(intent);
                                finish();
                                return;
                            }
                        }).create().show();
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, request_code_loc_perm);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionsGranted = false;
        switch (requestCode) {
            case request_code_loc_perm:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionsGranted = true;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert mapFragment != null;
                    mapFragment.getMapAsync(this);
                } else {
                    checkPermissions();
                }
        }
    }

    /*timer autres*/
    private void startTimerOther() { otherUpdates.run(); }
    private Runnable otherUpdates = new Runnable() {
        @Override
        public void run() {
            getPlaces();
            getWeather();

            /*progressBar*/
            secondeProgressBar += 2;
            if (secondeProgressBar == 60) {
                secondeProgressBar = 0;
                if (progressFaim >= 1) {
                    if (progressFaim >= 80) {
                        progressSante += 5;
                        if (progressSante > 100) {
                            progressSante = 100;
                        }
                    }
                    progressFaim -= 1;
                    volleyRequest.setState(progressSante, progressFaim, progressEndurance, token, getApplicationContext());
                    progressBarFaim.setPercent(progressFaim);
                }
                else if (progressSante >= 1) {
                    progressSante -= 1;
                    volleyRequest.setState(progressSante, progressFaim, progressEndurance, token, getApplicationContext());
                    progressBarSante.setPercent(progressSante);
                }
                else {
                    toaster("Vous êtes mort!", 0);
                }
            }
            handler.postDelayed(this, 2000);
        }
    };

    /*orientation*/
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        degree = Math.round(sensorEvent.values[0]);
        if (!sett_autorot) {
            /*rotation manuelle*/
            if (Math.abs(degree - startDegree) > 2) {
                deg_halo = degree - bearing;
                //halo.setRotation(deg_halo);
                startDegree = degree;
            }
        }
        else {
            bearing = degree;
            updateMarker();
            mmap.moveCamera(CameraUpdateFactory.newCameraPosition(c_localisation));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /*settings*/
    public void loadSettingsFile() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            for(int x = 0; x < 3; x++){
                if (x == 0) {batterySaver = Boolean.parseBoolean(br.readLine());}
                if (x == 1) {zoomLevel = (float) ((Float.parseFloat(br.readLine())*1.5)+17.5);}
                if (x == 2) {mapType = Integer.parseInt(br.readLine());}
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

    public void getWeather() {
        urlWeather = startUrlWeather + latitude + lonUrlWeather + longitude + endUrlWeather;
        final JsonObjectRequest jorWeather = new JsonObjectRequest(Request.Method.GET, urlWeather, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray weatherArray = response.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                    icon = weatherObject.getString("icon");
                    description = weatherObject.getString("description");

                    JSONObject main = response.getJSONObject("main");
                    temperature = String.valueOf(Math.round(main.getDouble("temp"))) + "°C";

                    JSONObject sys = response.getJSONObject("sys");
                    country = sys.getString("country");

                    ville = response.getString("name");

                    if (icon.equals("01d")) {weather.setBackgroundResource(R.drawable.owm_01d);}
                    if (icon.equals("01n")) {weather.setBackgroundResource(R.drawable.owm_01n);}
                    if (icon.equals("02d")) {weather.setBackgroundResource(R.drawable.owm_02d);}
                    if (icon.equals("02n")) {weather.setBackgroundResource(R.drawable.owm_02n);}
                    if (icon.equals("03d") || icon.equals("03n")) {weather.setBackgroundResource(R.drawable.owm_03);}
                    if (icon.equals("04d") || icon.equals("04n")) {weather.setBackgroundResource(R.drawable.owm_04);}
                    if (icon.equals("09d") || icon.equals("09n")) {weather.setBackgroundResource(R.drawable.owm_09);}
                    if (icon.equals("10d")) {weather.setBackgroundResource(R.drawable.owm_10d);}
                    if (icon.equals("10n")) {weather.setBackgroundResource(R.drawable.owm_10n);}
                    if (icon.equals("11d") || icon.equals("11n")) {weather.setBackgroundResource(R.drawable.owm_11);}
                    if (icon.equals("13d") || icon.equals("13n")) {weather.setBackgroundResource(R.drawable.owm_13);}
                    if (icon.equals("50d") || icon.equals("50n")) {weather.setBackgroundResource(R.drawable.owm_50);}

                    if (weather.getVisibility() == View.GONE) {
                        weather.setVisibility(View.VISIBLE);
                        bg_weather.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jorWeather);
    }

    /*Places*/
    public void getPlaces() {
        urlPlaces = startUrlPlaces + latitude + lonUrlPlaces + longitude + endUrlPlaces;
        final JsonArrayRequest jarPlaces = new JsonArrayRequest(Request.Method.GET, urlPlaces, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int k = 0; k < arrayMarker.size(); k++){
                        Boolean sameMarker = false;
                        LatLng markerLocation = arrayMarker.get(k).getPosition();
                        for (int i = 0; i < response.length(); i++){
                            JSONObject place = response.getJSONObject(i);
                            JSONObject coordinates = place.getJSONObject("point");
                            lon = coordinates.getDouble("lon");
                            lat = coordinates.getDouble("lat");
                            LatLng currentLocation = new LatLng(lat, lon);
                            if (currentLocation.toString().equals(markerLocation.toString())) {
                                sameMarker = true;
                                break;
                            }
                        }
                        if (!sameMarker) {
                            arrayMarker.get(k).remove();
                            arrayMarker.remove(k);
                            k--;
                        }
                    }

                    for (int j = 0; j < response.length(); j++) {
                        Boolean sameMarker = false;

                        JSONObject place = response.getJSONObject(j);
                        JSONObject coordinates = place.getJSONObject("point");

                        lon = coordinates.getDouble("lon");
                        lat = coordinates.getDouble("lat");
                        dist = place.getDouble("dist");
                        name = place.getString("name");
                        kinds = place.getString("kinds");



                        if (kinds.contains(kFood)) {
                            kinds = "nourriture";
                            if (j == 0 && dist < distMin) {
                                bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_nourriture_closest);
                            }
                            else {
                                bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_nourriture);
                            }
                        }
                        else {
                            if (kinds.contains(kShop)) {
                                kinds = "magasin";
                                if (j == 0 && dist < distMin) {
                                    bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_magasin_closest);
                                }
                                else {
                                    bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_magasin);
                                }
                            }
                            else{
                                if (kinds.contains(kTransport)) {
                                    kinds = "transport";
                                    if (j == 0 && dist < distMin) {
                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_transport_closest);
                                    }
                                    else {
                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_transport);
                                    }
                                }
                                else{
                                    if (kinds.contains(kBank)) {
                                        kinds = "banque";
                                        if (j == 0 && dist < distMin) {
                                            bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_banque_closest);
                                        }
                                        else {
                                            bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_banque);
                                        }
                                    }
                                    else{
                                        if (kinds.contains(kNatural)) {
                                            kinds = "terrain vert";
                                            if (j == 0 && dist < distMin) {
                                                bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_terrain_closest);
                                            }
                                            else {
                                                bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_terrain);
                                            }
                                        }
                                        else{
                                            if (kinds.contains(kAccomodations)) {
                                                kinds = "logement";
                                                if (j == 0 && dist < distMin) {
                                                    bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_logement_closest);
                                                }
                                                else {
                                                    bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_logement);
                                                }
                                            }
                                            else{
                                                if (kinds.contains(kIndustrial)) {
                                                    kinds = "industrie";
                                                    if (j == 0 && dist < distMin) {
                                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_industrie_closest);
                                                    }
                                                    else {
                                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_industrie);
                                                    }
                                                }
                                                else{
                                                    if (kinds.contains(kReligion)) {
                                                        kinds = "religion";
                                                        if (j == 0 && dist < distMin) {
                                                            bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_religion_closest);
                                                        }
                                                        else {
                                                            bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_religion);
                                                        }
                                                    }
                                                    else{
                                                        if (kinds.contains(kSport)) {
                                                            kinds = "sport";
                                                            if (j == 0 && dist < distMin) {
                                                                bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_sport_closest);
                                                            }
                                                            else {
                                                                bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_sport);
                                                            }
                                                        }
                                                        else{
                                                            if (kinds.contains(kAmusements)) {
                                                                kinds = "loisirs";
                                                                if (j == 0 && dist < distMin) {
                                                                    bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_loisir_closest);
                                                                }
                                                                else {
                                                                    bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_loisir);
                                                                }
                                                            }
                                                            else{
                                                                if (kinds.contains(kAdult)) {
                                                                    kinds = "adult";
                                                                    if (j == 0 && dist < distMin) {
                                                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_adulte_closest);
                                                                    }
                                                                    else {
                                                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_adulte);
                                                                    }
                                                                }
                                                                else {
                                                                    /*other*/
                                                                    kinds = "monuments";
                                                                    if (j == 0 && dist < distMin) {
                                                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_monument_closest);
                                                                    }
                                                                    else {
                                                                        bitmapDescriptor = bitmapDescriptorFromVector(getApplicationContext(), R.drawable.marker_monument);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        LatLng currentLocation = new LatLng(lat, lon);

                        for (int i = 0; i < arrayMarker.size(); i++) {
                            LatLng markerLocation = arrayMarker.get(i).getPosition();
                            if (currentLocation.toString().equals(markerLocation.toString())) {
                                sameMarker = true;
                                break;
                            }
                        }
                        if (!sameMarker) {
                            arrayMarker.add(mmap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lon))
                                    .title(name)
                                    .icon(bitmapDescriptor)
                                    .zIndex(1)));
                        }



                        if (j == 0) {
                            distClosest = dist;
                            nameClosest = name;
                            kindsClosest = kinds;
                            latClosest = lat;
                            lonClosest = lon;
                        }
                    }



                    if (!arrayMarker.isEmpty()) {
                        if (distClosest < distMin) {
                            fouille.setVisibility(View.VISIBLE);
                        }
                        else{
                            fouille.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jarPlaces);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorDrawableResourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void onFouille() {
        progressEndurance -= 5;
        volleyRequest.setState(progressSante, progressFaim, progressEndurance, token, getApplicationContext());
        progressBarEndurance.setPercent(progressEndurance);
        toaster("Vous fouillez " + nameClosest + "\n" + kindsClosest, 1);
        droppedItem = new ArrayList<>();
        nbrAnim = 0;

        /*récupérer chance de equipement*/
        int chance = Math.round(listItem.random(1, 4));
        for (int i = 0; i < chance; i++) {
            int quantite = Math.round(listItem.random(1, 6));
            int ressourceId = ressourceIdList.get(Math.round(listItem.random(1, ressourceIdList.size())-1));
            Metadata metadata = new Metadata(new Nourriture(Math.round(listItem.random(0, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10))), new Equipement(Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10))), new Arme(Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10))), new Outil(Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10))), new Marteau(Math.round(listItem.random(1, 10)), Math.round(listItem.random(1, 10))));
            ListInventaire.add(new Item(quantite, listItem.getItem(ressourceId).getImageItem(), listItem.getItem(ressourceId).getImageCadre(), ressourceId, listItem.getItem(ressourceId).getNomRessource(), listItem.getItem(ressourceId).getDescriptionRessource(), metadata));
            droppedItem.add(listItem.noReferenceCopy(ListInventaire.get(ListInventaire.size() - 1)));
            droppedItem = new ArrayList<>(listItem.groupInventaire(droppedItem));
            ListInventaire = new ArrayList<>(listItem.sortInventaire(ListInventaire));
        }
        JSONArrayInventaire = listItem.getItemToJsonInventaire(ListInventaire, getApplicationContext());
        imageViewAnimFouille.setImageResource(droppedItem.get(nbrAnim).getImageItem());
        textViewAnimFouille.setText(""+droppedItem.get(nbrAnim).getNbrItem());
        imageViewAnimFouille.startAnimation(animDrop);
        textViewAnimFouille.startAnimation(animDrop);
        nbrAnim++;
        animEnd = true;
        volleyRequest.sendJSONInventaire(JSONArrayInventaire, token, getApplicationContext());
        getPlaces();
    }

    public void toaster (String text, int length) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), text, length);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toast.cancel();
            finishAffinity();
            finish();
        }
        else {
            toaster("Appuyez à nouveau sur retour pour quitter l'application", 0);
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        toaster(""+marker.getTitle(), 0);
        return true;
    }
}