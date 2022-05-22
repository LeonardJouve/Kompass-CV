package com.example.Kompass;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Tab2 extends Fragment {

    ListItem listItem = new ListItem();

    ExpandableHeightGridView gridViewNourriture;
    ExpandableHeightGridView gridViewMateriaux;
    ExpandableHeightGridView gridViewEquipement;
    ExpandableHeightGridView gridViewArmes;
    ExpandableHeightGridView gridViewOutlis;
    View view;
    ImageView cadreMaterial1;
    ImageView cadreMaterial2;
    ImageView cadreMaterial3;
    ImageView cadreMarteau;
    ImageView cadreResultat;
    ImageView imageViewFleche;
    SeekBar slider;
    ProgressBar progressBar;
    TextView textViewSlider;
    TextView textViewPreviewMetadata;
    TextView textViewMultiplicateur;
    Button buttonCraft;
    ImageView imageViewClose;
    ListView listViewMaterial1;
    ListView listViewMaterial2;
    ListView listViewMaterial3;
    ListView listViewMarteau;
    ScrollView scrollView;
    ConstraintLayout constraintLayoutCraft;
    Recipie currentRecipie;
    adapterListViewCraft adapterMaterial1;
    adapterListViewCraft adapterMaterial2;
    adapterListViewCraft adapterMaterial3;
    adapterListViewCraft adapterMarteau;
    ArrayList<Item> ListMaterial1Filtre = new ArrayList<>();
    ArrayList<Item> ListMaterial2Filtre = new ArrayList<>();
    ArrayList<Item> ListMaterial3Filtre = new ArrayList<>();
    ArrayList<Item> ListMarteauFiltre = new ArrayList<>();
    ArrayList<Item> ListInventaire = new ArrayList<>();
    ArrayList<Item> groupedList = new ArrayList<>();
    ArrayList<Item> listRemovedMaterial1 = new ArrayList<>();
    ArrayList<Item> listRemovedMaterial2 = new ArrayList<>();
    ArrayList<Item> listRemovedMaterial3 = new ArrayList<>();
    ArrayList<Integer> listItemCraftTier = new ArrayList<>();
    ImageView imageViewAnim;
    TextView textViewAnim;
    int nbrAnim = 0;
    Animation animDrop;
    Boolean animEnd = false;
    Boolean imageViewBackMainActivityVisiblity = false;
    ArrayList<Item> listAnimCraft = new ArrayList<>();

    Item itemMaterial1;
    Item itemMaterial2;
    Item itemMaterial3;
    Item itemMarteau;

    int currentItemAmount;
    int currentRemovedMaterial;
    int itemCraftId;
    float itemCraftMultiplier;

    String jsonInventaire;

    /*RessourceID
     * 101 -> soupe
     * 102 -> salade
     * 201 -> minerais
     * 202 -> batonnets
     * 301 -> bottes
     * 302 -> jambières
     * 303 -> chestplate
     * 304 -> casque
     * 401 -> épée
     * 402 -> lance
     * 403 -> massue
     * 501 -> pioche
     * 502 -> pelle
     * 503 -> hache
     * 504 -> houe
     * 505 -> marteau
     * */

    ArrayList<Item> ListCraftNourriture = new ArrayList<>(Arrays.asList(new Item(-1, R.drawable.soupe_modele, R.drawable.cadre, 100, "", "", null), new Item(-1, R.drawable.salade_modele, R.drawable.cadre, 110, "", "", null)));
    ArrayList<Item> ListCraftMateriaux = new ArrayList<>(Arrays.asList(new Item(-1, R.drawable.lingot_modele, R.drawable.cadre, 200, "", "", null), new Item(-1, R.drawable.baton_modele, R.drawable.cadre, 210, "", "", null), new Item(-1, R.drawable.plaques_modele, R.drawable.cadre, 220, "", "", null)));
    ArrayList<Item> ListCraftEquipement = new ArrayList<>(Arrays.asList(new Item(-1, R.drawable.bottes_modele, R.drawable.cadre, 300, "", "", null), new Item(-1, R.drawable.jambiere_modele, R.drawable.cadre, 310, "", "", null), new Item(-1, R.drawable.plastron_modele, R.drawable.cadre, 320, "", "", null), new Item(-1, R.drawable.casque_modele, R.drawable.cadre, 330, "", "", null)));
    ArrayList<Item> ListCraftArmes = new ArrayList<>(Arrays.asList(new Item(-1, R.drawable.epee_modele, R.drawable.cadre, 400, "", "", null), new Item(-1, R.drawable.lance_modele, R.drawable.cadre, 410, "", "", null), new Item(-1, R.drawable.masse_modele, R.drawable.cadre, 420, "", "", null)));
    ArrayList<Item> ListCraftOutils = new ArrayList<>(Arrays.asList(new Item(-1, R.drawable.pioche_modele, R.drawable.cadre, 500, "", "", null), new Item(-1, R.drawable.pelle_modele, R.drawable.cadre, 510, "", "", null), new Item(-1, R.drawable.hache_modele, R.drawable.cadre, 520, "", "", null), new Item(-1, R.drawable.houe_modele, R.drawable.cadre, 530, "", "", null), new Item(-1, R.drawable.marteau_modele, R.drawable.cadre, 540, "", "", null)));

    ArrayList<Integer> ListCombustibles = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    ArrayList<Integer> ListPoudres = new ArrayList<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19));
    ArrayList<Integer> ListVegetaux = new ArrayList<>(Arrays.asList(20, 21, 22, 23, 24, 25, 26, 27, 28, 29));
    ArrayList<Integer> ListSalades = new ArrayList<>(Arrays.asList(30, 31, 32, 33, 34, 35, 36, 37, 38, 39));
    ArrayList<Integer> ListLingots = new ArrayList<>(Arrays.asList(200, 201, 202, 203, 204, 205, 206, 207, 208, 209));
    ArrayList<Integer> ListBatonnets = new ArrayList<>(Arrays.asList(210, 211, 212, 213, 214, 215, 216, 217, 218, 219));
    ArrayList<Integer> ListPlaques = new ArrayList<>(Arrays.asList(220, 221, 222, 223, 224, 225, 226, 227, 228, 229));
    ArrayList<Integer> ListMarteau = new ArrayList<>(Arrays.asList(600, 601, 602, 603, 604, 605, 606, 607, 608, 609));

    interfaceTablayout interfaceTablayout;
    public Tab2(interfaceTablayout interfaceTablayout) {
        this.interfaceTablayout = interfaceTablayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab2, container, false);

        scrollView = view.findViewById(R.id.scrollView);
        constraintLayoutCraft = view.findViewById(R.id.constraintLayoutCraft);

        gridViewNourriture = view.findViewById(R.id.gridViewNourriture);
        gridViewMateriaux = view.findViewById(R.id.gridViewMateriaux);
        gridViewEquipement = view.findViewById(R.id.gridViewEquipement);
        gridViewArmes = view.findViewById(R.id.gridViewArmes);
        gridViewOutlis = view.findViewById(R.id.gridViewOutlis);

        CustomAdapter adapterNourriture = new CustomAdapter(getContext(), R.layout.grid_items, ListCraftNourriture);
        gridViewNourriture.setExpanded(true);
        gridViewNourriture.setAdapter(adapterNourriture);
        CustomAdapter adapterMateriaux = new CustomAdapter(getContext(), R.layout.grid_items, ListCraftMateriaux);
        gridViewMateriaux.setExpanded(true);
        gridViewMateriaux.setAdapter(adapterMateriaux);
        CustomAdapter adapterEquipement = new CustomAdapter(getContext(), R.layout.grid_items, ListCraftEquipement);
        gridViewEquipement.setExpanded(true);
        gridViewEquipement.setAdapter(adapterEquipement);
        CustomAdapter adapterArmes = new CustomAdapter(getContext(), R.layout.grid_items, ListCraftArmes);
        gridViewArmes.setExpanded(true);
        gridViewArmes.setAdapter(adapterArmes);
        CustomAdapter adapterOutils = new CustomAdapter(getContext(), R.layout.grid_items, ListCraftOutils);
        gridViewOutlis.setExpanded(true);
        gridViewOutlis.setAdapter(adapterOutils);
        cadreMaterial1 = view.findViewById(R.id.cadreMaterial1);
        listViewMaterial1 = view.findViewById(R.id.listViewMaterial1);
        cadreMaterial2 = view.findViewById(R.id.cadreMaterial2);
        listViewMaterial2 = view.findViewById(R.id.listViewMaterial2);
        cadreMaterial3 = view.findViewById(R.id.cadreMaterial3);
        listViewMaterial3 = view.findViewById(R.id.listViewMaterial3);
        cadreMarteau = view.findViewById(R.id.cadreMarteau);
        listViewMarteau = view.findViewById(R.id.listViewMarteau);
        cadreResultat = view.findViewById(R.id.cadreResultat);
        imageViewFleche = view.findViewById(R.id.imageViewFleche);
        imageViewClose = view.findViewById(R.id.imageViewClose);
        buttonCraft = view.findViewById(R.id.buttonCraft);
        slider = view.findViewById(R.id.slider);
        textViewSlider = view.findViewById(R.id.textViewSlider);
        textViewPreviewMetadata = view.findViewById(R.id.textViewPreviewMetadata);
        textViewMultiplicateur = view.findViewById(R.id.textViewMultiplicateur);
        progressBar = view.findViewById(R.id.progressBar);

        textViewSlider.setText("" + slider.getProgress());
        imageViewAnim = view.findViewById(R.id.imageViewAnimCraft);
        textViewAnim = view.findViewById(R.id.textViewAnimCraft);
        animDrop = AnimationUtils.loadAnimation(getContext(), R.anim.anim_drop);

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
                    if (nbrAnim < listAnimCraft.size()) {
                        imageViewAnim.setImageResource(listAnimCraft.get(nbrAnim).getImageItem());
                        textViewAnim.setText(""+listAnimCraft.get(nbrAnim).getNbrItem());
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

        gridViewNourriture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCraftVisibility();
                switch (position) {
                    case 0:
                        /*soupe*/
                        itemCraftId = 100;
                        currentRecipie = new Recipie(R.drawable.vegetaux_modele, R.drawable.vegetaux_modele, R.drawable.combustibles_modele, ListVegetaux, ListVegetaux, ListCombustibles);
                        break;
                    case 1:
                        /*salade*/
                        itemCraftId = 110;
                        currentRecipie = new Recipie(R.drawable.salades_modele, R.drawable.vegetaux_modele, R.drawable.combustibles_modele, ListSalades, ListVegetaux, ListCombustibles);
                        break;
                }
                cadreMaterial1.setImageResource(currentRecipie.getDrawableMaterial1());
                cadreMaterial2.setImageResource(currentRecipie.getDrawableMaterial2());
                cadreMaterial3.setImageResource(currentRecipie.getDrawableMaterial3());
                setCurrentRecipie();
            }
        });

        gridViewMateriaux.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCraftVisibility();
                switch (position) {
                    case 0:
                        /*lingot*/
                        itemCraftId = 200;
                        currentRecipie = new Recipie(R.drawable.poudres_modele, R.drawable.poudres_modele, R.drawable.combustibles_modele, ListPoudres, ListPoudres, ListCombustibles);
                        break;
                    case 1:
                        /*batonnet*/
                        itemCraftId = 210;
                        currentRecipie = new Recipie(R.drawable.combustibles_modele, R.drawable.combustibles_modele, R.drawable.combustibles_modele, ListCombustibles, ListCombustibles, ListCombustibles);
                        break;
                    case 2:
                        /*plaques*/
                        itemCraftId = 220;
                        currentRecipie = new Recipie(R.drawable.lingot_modele, R.drawable.lingot_modele, R.drawable.combustibles_modele, ListLingots, ListLingots, ListCombustibles);
                        break;
                }
                cadreMaterial1.setImageResource(currentRecipie.getDrawableMaterial1());
                cadreMaterial2.setImageResource(currentRecipie.getDrawableMaterial2());
                cadreMaterial3.setImageResource(currentRecipie.getDrawableMaterial3());
                setCurrentRecipie();
            }
        });

        gridViewEquipement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCraftVisibility();
                switch (position) {
                    case 0:
                        /*bottes*/
                        itemCraftId = 300;
                        currentRecipie = new Recipie(R.drawable.lingot_modele, R.drawable.lingot_modele, R.drawable.combustibles_modele, ListLingots, ListLingots, ListCombustibles);
                        break;
                    case 1:
                        /*jambières*/
                        itemCraftId = 310;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.lingot_modele, R.drawable.combustibles_modele, ListPlaques, ListLingots, ListCombustibles);
                        break;
                    case 2:
                        /*plastron*/
                        itemCraftId = 320;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.plaques_modele, R.drawable.combustibles_modele, ListPlaques, ListPlaques, ListCombustibles);
                        break;
                    case 3:
                        /*casque*/
                        itemCraftId = 330;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.poudres_modele, R.drawable.combustibles_modele, ListPlaques, ListPoudres, ListCombustibles);
                        break;
                }
                cadreMaterial1.setImageResource(currentRecipie.getDrawableMaterial1());
                cadreMaterial2.setImageResource(currentRecipie.getDrawableMaterial2());
                cadreMaterial3.setImageResource(currentRecipie.getDrawableMaterial3());
                setCurrentRecipie();
            }
        });

        gridViewArmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCraftVisibility();
                switch (position) {
                    case 0:
                        /*epee*/
                        itemCraftId = 400;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.baton_modele, R.drawable.combustibles_modele, ListPlaques, ListBatonnets, ListCombustibles);
                        break;
                    case 1:
                        /*lance*/
                        itemCraftId = 410;
                        currentRecipie = new Recipie(R.drawable.lingot_modele, R.drawable.baton_modele, R.drawable.combustibles_modele, ListLingots, ListBatonnets, ListCombustibles);
                        break;
                    case 2:
                        /*massue*/
                        itemCraftId = 420;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.baton_modele, R.drawable.combustibles_modele, ListPlaques, ListBatonnets, ListCombustibles);
                        break;
                }
                cadreMaterial1.setImageResource(currentRecipie.getDrawableMaterial1());
                cadreMaterial2.setImageResource(currentRecipie.getDrawableMaterial2());
                cadreMaterial3.setImageResource(currentRecipie.getDrawableMaterial3());
                setCurrentRecipie();
            }
        });

        gridViewOutlis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCraftVisibility();
                switch (position) {
                    case 0:
                        /*pioche*/
                        itemCraftId = 500;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.baton_modele, R.drawable.combustibles_modele, ListPlaques, ListBatonnets, ListCombustibles);
                        break;
                    case 1:
                        /*pelle*/
                        itemCraftId = 510;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.baton_modele, R.drawable.combustibles_modele, ListPlaques, ListBatonnets, ListCombustibles);
                        break;
                    case 2:
                        /*hache*/
                        itemCraftId = 520;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.baton_modele, R.drawable.combustibles_modele, ListPlaques, ListBatonnets, ListCombustibles);
                        break;
                    case 3:
                        /*houe*/
                        itemCraftId = 530;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.baton_modele, R.drawable.combustibles_modele, ListPlaques, ListBatonnets, ListCombustibles);
                        break;
                    case 4:
                        /*marteau*/
                        itemCraftId = 600;
                        currentRecipie = new Recipie(R.drawable.plaques_modele, R.drawable.plaques_modele, R.drawable.baton_modele, ListPlaques, ListPlaques, ListBatonnets);
                        break;
                }
                cadreMaterial1.setImageResource(currentRecipie.getDrawableMaterial1());
                cadreMaterial2.setImageResource(currentRecipie.getDrawableMaterial2());
                cadreMaterial3.setImageResource(currentRecipie.getDrawableMaterial3());
                setCurrentRecipie();
            }
        });

        listViewMaterial1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemMaterial1 = ListMaterial1Filtre.get(position);
                if (cadreMaterial1.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial1()).getConstantState()) {
                    for (int i = 0; i < groupedList.size(); i++) {
                        if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMaterial1.getDrawable().getConstantState()) {
                            groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() + 1);
                            break;
                        }
                    }
                }
                cadreMaterial1.setImageResource(ListMaterial1Filtre.get(position).getImageItem());
                for (int i = 0; i < groupedList.size(); i++) {
                    if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMaterial1.getDrawable().getConstantState()) {
                        groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() - 1);
                        break;
                    }
                }
                setCurrentRecipie();
                listViewMaterial1.setVisibility(View.GONE);
                checkCraft();
            }
        });

        listViewMaterial2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemMaterial2 = ListMaterial2Filtre.get(position);
                if (cadreMaterial2.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial2()).getConstantState()) {
                    for (int i = 0; i < groupedList.size(); i++) {
                        if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMaterial2.getDrawable().getConstantState()) {
                            groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() + 1);
                            break;
                        }
                    }
                }
                cadreMaterial2.setImageResource(ListMaterial2Filtre.get(position).getImageItem());
                for (int i = 0; i < groupedList.size(); i++) {
                    if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMaterial2.getDrawable().getConstantState()) {
                        groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() - 1);
                        break;
                    }
                }
                setCurrentRecipie();
                listViewMaterial2.setVisibility(View.GONE);
                checkCraft();
            }
        });

        listViewMaterial3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemMaterial3 = ListMaterial3Filtre.get(position);
                if (cadreMaterial3.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial3()).getConstantState()) {
                    for (int i = 0; i < groupedList.size(); i++) {
                        if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMaterial3.getDrawable().getConstantState()) {
                            groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() + 1);
                            break;
                        }
                    }
                }
                cadreMaterial3.setImageResource(ListMaterial3Filtre.get(position).getImageItem());
                for (int i = 0; i < groupedList.size(); i++) {
                    if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMaterial3.getDrawable().getConstantState()) {
                        groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() - 1);
                        break;
                    }
                }
                setCurrentRecipie();
                listViewMaterial3.setVisibility(View.GONE);
                checkCraft();
            }
        });

        listViewMarteau.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemMarteau = ListMarteauFiltre.get(position);
                if (cadreMarteau.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), R.drawable.marteau_modele).getConstantState()) {
                    for (int i = 0; i < groupedList.size(); i++) {
                        if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMarteau.getDrawable().getConstantState()) {
                            groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() + 1);
                            break;
                        }
                    }
                }
                cadreMarteau.setImageResource(ListMarteauFiltre.get(position).getImageItem());
                for (int i = 0; i < groupedList.size(); i++) {
                    if (AppCompatResources.getDrawable(getContext(), groupedList.get(i).getImageItem()).getConstantState() == cadreMarteau.getDrawable().getConstantState()) {
                        groupedList.get(i).setNbrItem(groupedList.get(i).getNbrItem() - 1);
                        break;
                    }
                }
                setCurrentRecipie();
                listViewMarteau.setVisibility(View.GONE);
                checkCraft();
            }
        });

        buttonCraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cadreMaterial1.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial1()).getConstantState() & cadreMaterial2.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial2()).getConstantState() & cadreMaterial3.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial3()).getConstantState() & cadreMarteau.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), R.drawable.marteau_modele).getConstantState() & slider.getProgress() > 0) {
                    int [] listItemCraftTier = {itemMaterial1.getRessourceId() % 10, itemMaterial2.getRessourceId() % 10};
                    Arrays.sort(listItemCraftTier);
                    if (itemMarteau.getRessourceId()%10 + 1 >= listItemCraftTier[0]) {
                        float a = 0;
                        if (itemMarteau.getMetadata().getMarteau().getVitesse() < 100) {
                            a = (float) (100-itemMarteau.getMetadata().getMarteau().getVitesse())/120;
                        }
                        float b = (float) (10 * slider.getProgress()) * a;
                        int period = (Math.round(b) + 1);
                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.VISIBLE);
                        imageViewClose.setVisibility(View.GONE);
                        buttonCraft.setVisibility(View.GONE);
                        final int[] progress = {0};
                        final Timer t = new Timer();
                        TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {
                                progress[0]++;
                                progressBar.setProgress(progress[0]);

                                if (progress[0] == 100) {
                                    t.cancel();
                                }
                            }
                        };
                        t.schedule(tt, 0, period);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Craft();
                            }
                        }, 100*period);
                    }
                }
            }
        });

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCraftVisibility();
            }
        });

        cadreMaterial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewMaterial1.setVisibility(View.VISIBLE);
                listViewMaterial2.setVisibility(View.GONE);
                listViewMaterial3.setVisibility(View.GONE);
                listViewMarteau.setVisibility(View.GONE);
            }
        });

        cadreMaterial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewMaterial1.setVisibility(View.GONE);
                listViewMaterial2.setVisibility(View.VISIBLE);
                listViewMaterial3.setVisibility(View.GONE);
                listViewMarteau.setVisibility(View.GONE);
            }
        });

        cadreMaterial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewMaterial1.setVisibility(View.GONE);
                listViewMaterial2.setVisibility(View.GONE);
                listViewMaterial3.setVisibility(View.VISIBLE);
                listViewMarteau.setVisibility(View.GONE);
            }
        });

        cadreMarteau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewMaterial1.setVisibility(View.GONE);
                listViewMaterial2.setVisibility(View.GONE);
                listViewMaterial3.setVisibility(View.GONE);
                listViewMarteau.setVisibility(View.VISIBLE);
            }
        });

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSlider.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;}

    public void changeCraftVisibility() {
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
            constraintLayoutCraft.setVisibility(View.VISIBLE);
            listViewMarteau.setVisibility(View.GONE);
            listViewMaterial1.setVisibility(View.GONE);
            listViewMaterial2.setVisibility(View.GONE);
            listViewMaterial3.setVisibility(View.GONE);
            ListMaterial1Filtre.clear();
            ListMaterial2Filtre.clear();
            ListMaterial3Filtre.clear();
            cadreResultat.setImageResource(R.drawable.preview_craft);
            textViewPreviewMetadata.setText("");
            textViewMultiplicateur.setText("");
            slider.setMax(0);
            groupedList = new ArrayList<>();
            for (Item item: ListInventaire) {
                groupedList.add(listItem.noReferenceCopy(item));
            }
            groupedList = new ArrayList<>(listItem.groupInventaire(groupedList));
            interfaceTablayout.onCraft(true);
        }
        else {
            scrollView.setVisibility(View.VISIBLE);
            constraintLayoutCraft.setVisibility(View.GONE);
            cadreMarteau.setImageResource(R.drawable.marteau_modele);
            interfaceTablayout.onCraft(false);
        }
    }

    public void checkCraft() {
        if (cadreMaterial1.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial1()).getConstantState() & cadreMaterial2.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial2()).getConstantState() & cadreMaterial3.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), currentRecipie.getDrawableMaterial3()).getConstantState() & cadreMarteau.getDrawable().getConstantState() != AppCompatResources.getDrawable(getContext(), R.drawable.marteau_modele).getConstantState()) {
            listItemCraftTier = new ArrayList<>();
            /*si material != batonnet -> sinon impossible de craft des outils de tier supérieur à 1*/
            if (itemMaterial1.getRessourceId() < 210 | itemMaterial1.getRessourceId() > 219) {
                // bois
                if (itemMaterial1.getRessourceId() < 10) {
                    listItemCraftTier.add(itemMaterial1.getRessourceId() % 10 - 1);
                } else {
                    listItemCraftTier.add(itemMaterial1.getRessourceId() % 10);
                }
            }
            if (itemMaterial2.getRessourceId() < 210 | itemMaterial2.getRessourceId() > 219) {
                // bois
                if (itemMaterial2.getRessourceId() < 10) {
                    listItemCraftTier.add(itemMaterial2.getRessourceId() % 10 - 1);
                } else {
                    listItemCraftTier.add(itemMaterial2.getRessourceId() % 10);
                }
            }
            Collections.sort(listItemCraftTier);
            if (itemMarteau.getRessourceId()%10 + 1 >= listItemCraftTier.get(0)) {
                int nbrItemMaterial1 = 0;
                int nbrItemMaterial2 = 0;
                int nbrItemMaterial3 = 0;
                int maxCraft = 0;

                ArrayList<Item> ListCheckCraft = new ArrayList<>();
                for (Item item: ListInventaire) {
                    ListCheckCraft.add(listItem.noReferenceCopy(item));
                }
                ListCheckCraft = new ArrayList<>(listItem.groupInventaire(ListCheckCraft));

                for (Item item: ListCheckCraft) {
                    if (item.getRessourceId() == itemMaterial1.getRessourceId()) {
                        nbrItemMaterial1 = item.getNbrItem();
                    }
                    if (item.getRessourceId() == itemMaterial2.getRessourceId()) {
                        nbrItemMaterial2 = item.getNbrItem();
                    }
                    if (item.getRessourceId() == itemMaterial3.getRessourceId()) {
                        nbrItemMaterial3 = item.getNbrItem();
                    }
                }

                while (nbrItemMaterial1 > 0 & nbrItemMaterial2 > 0 & nbrItemMaterial3 > 0) {
                    if (itemMaterial1.getRessourceId() == itemMaterial2.getRessourceId()) {
                        if (itemMaterial1.getRessourceId() == itemMaterial3.getRessourceId()) {
                            /*item1 == item2 == item3*/
                            nbrItemMaterial1 -= 3;
                        }
                        else{
                            /*item1 == item2*/
                            nbrItemMaterial1 -= 2;
                            nbrItemMaterial3 --;
                        }
                    }
                    else if (itemMaterial1.getRessourceId() == itemMaterial3.getRessourceId()) {
                        /*item1 == item3*/
                        nbrItemMaterial1 -= 2;
                        nbrItemMaterial2 --;
                    }
                    else if (itemMaterial2.getRessourceId() == itemMaterial3.getRessourceId()) {
                        /*item2 == item3*/
                        nbrItemMaterial2 -= 2;
                        nbrItemMaterial1 --;
                    }
                    else {
                        /*item1 != item2 != item3*/
                        nbrItemMaterial1 --;
                        nbrItemMaterial2 --;
                        nbrItemMaterial3 --;
                    }
                    if (nbrItemMaterial1 >= 0 & nbrItemMaterial2 >= 0 & nbrItemMaterial3 >= 0) {
                        maxCraft += 1;
                    }
                }
                slider.setMax(maxCraft);

                float a = 2 + itemMaterial1.getRessourceId()%10 + itemMaterial2.getRessourceId()%10 + itemMaterial3.getRessourceId()%10;
                if (itemMaterial1.getRessourceId() < 10) {
                    a -= 1;
                }
                if (itemMaterial2.getRessourceId() < 10) {
                    a -= 1;
                }
                //tier 1 du bois = 1 / tier 1 des autres materiaux = ...0 -> + 1/3
                if (itemMaterial3.getRessourceId() > 9) {
                    a += 1;
                }
                a = (float) a/3;
                float b = (float) (0.5 * (itemMarteau.getRessourceId()%10));
                itemCraftMultiplier = (float) a + b;
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String textMultiplicateur = decimalFormat.format(itemCraftMultiplier);
                textViewMultiplicateur.setText(textMultiplicateur + " x");
                cadreResultat.setImageResource(listItem.getItem(itemCraftId + listItemCraftTier.get(0)).getImageItem());
                int itemCraftType = listItem.getItemCraftType(itemCraftId);
                if (itemCraftType != 2) {
                    textViewPreviewMetadata.setText(listItem.getPreviewMetadata(itemCraftType));
                }
            }
            else {
                Toast.makeText(getContext(), "Qualité du marteau insuffisante !", Toast.LENGTH_SHORT).show();
                cadreResultat.setImageResource(R.drawable.preview_craft);
                textViewPreviewMetadata.setText("");
                textViewMultiplicateur.setText("");
                slider.setMax(0);
                textViewSlider.setText("" + 0);
            }
        }
    }

    public void getVariable() {
        imageViewBackMainActivityVisiblity = getArguments().getBoolean("isImageViewBackMainActivityVisible");
        if (imageViewBackMainActivityVisiblity & imageViewClose.getDrawable().isVisible()) {
            changeCraftVisibility();
        }
        jsonInventaire = getArguments().getString("JSONArrayInventaire");
        ListInventaire = new ArrayList<>(listItem.getJsonToItemInventaire(jsonInventaire, getContext()));
        groupedList = new ArrayList<>();
        for (Item item: ListInventaire) {
            groupedList.add(listItem.noReferenceCopy(item));
        }
        groupedList = new ArrayList<>(listItem.groupInventaire(groupedList));

        ListMarteauFiltre = new ArrayList<>();
        for (int i = 0; i < ListInventaire.size(); i++) {
            if (ListMarteau.contains(ListInventaire.get(i).getRessourceId())) {
                ListMarteauFiltre.add(listItem.noReferenceCopy(ListInventaire.get(i)));
            }
        }
        adapterMarteau = new adapterListViewCraft(getContext(), android.R.layout.simple_list_item_1, ListMarteauFiltre);
        listViewMarteau.setAdapter(adapterMarteau);
    }

    private void setCurrentRecipie() {
        ListMaterial1Filtre = new ArrayList<>();
        ListMaterial2Filtre = new ArrayList<>();
        ListMaterial3Filtre = new ArrayList<>();
        ListMarteauFiltre = new ArrayList<>();

        for (int i = 0; i < groupedList.size(); i++) {
            if (currentRecipie.getListMaterial1().contains(groupedList.get(i).getRessourceId())) {
                if (groupedList.get(i).getNbrItem() > 0) {
                    ListMaterial1Filtre.add(listItem.noReferenceCopy(groupedList.get(i)));
                }
            }
        }
        adapterMaterial1 = new adapterListViewCraft(getContext(), android.R.layout.simple_list_item_1, ListMaterial1Filtre);
        listViewMaterial1.setAdapter(adapterMaterial1);

        for (int i = 0; i < groupedList.size(); i++) {
            if (currentRecipie.getListMaterial2().contains(groupedList.get(i).getRessourceId())) {
                if (groupedList.get(i).getNbrItem() > 0) {
                    ListMaterial2Filtre.add(listItem.noReferenceCopy(groupedList.get(i)));
                }
            }
        }
        adapterMaterial2 = new adapterListViewCraft(getContext(), android.R.layout.simple_list_item_1, ListMaterial2Filtre);
        listViewMaterial2.setAdapter(adapterMaterial2);

        for (int i = 0; i < groupedList.size(); i++) {
            if (currentRecipie.getListMaterial3().contains(groupedList.get(i).getRessourceId())) {
                if (groupedList.get(i).getNbrItem() > 0) {
                    ListMaterial3Filtre.add(listItem.noReferenceCopy(groupedList.get(i)));
                }
            }
        }
        adapterMaterial3 = new adapterListViewCraft(getContext(), android.R.layout.simple_list_item_1, ListMaterial3Filtre);
        listViewMaterial3.setAdapter(adapterMaterial3);

        for (int i = 0; i < groupedList.size(); i++) {
            if (ListMarteau.contains(groupedList.get(i).getRessourceId())) {
                if (groupedList.get(i).getNbrItem() > 0) {
                    ListMarteauFiltre.add(listItem.noReferenceCopy(groupedList.get(i)));
                }
            }
        }
        adapterMarteau = new adapterListViewCraft(getContext(), android.R.layout.simple_list_item_1, ListMarteauFiltre);
        listViewMarteau.setAdapter(adapterMarteau);
    }

    private void Craft() {
        progressBar.setVisibility(View.GONE);
        imageViewClose.setVisibility(View.VISIBLE);
        buttonCraft.setVisibility(View.VISIBLE);
        cadreResultat.setImageResource(R.drawable.preview_craft);
        textViewPreviewMetadata.setText("");
        textViewMultiplicateur.setText("");
        boolean isMaterial1Removed = false;
        boolean isMaterial2Removed = false;
        boolean isMaterial3Removed = false;
        int removedMaterial1 = 0;
        int removedMaterial2 = 0;
        int removedMaterial3 = 0;
        int recycled;
        int recycledMaterial1 = 0;
        int recycledMaterial2 = 0;
        int recycledMaterial3 = 0;
        nbrAnim = 0;
        listAnimCraft = new ArrayList<>();
        listRemovedMaterial1 = new ArrayList<>();
        listRemovedMaterial2 = new ArrayList<>();
        listRemovedMaterial3 = new ArrayList<>();
        for (int i = 0; i < ListInventaire.size(); i++) {
            if (!isMaterial1Removed & cadreMaterial1.getDrawable().getConstantState() == AppCompatResources.getDrawable(getContext(), ListInventaire.get(i).getImageItem()).getConstantState()) {
                currentItemAmount = ListInventaire.get(i).getNbrItem();
                currentRemovedMaterial = 0;
                while (removedMaterial1 < slider.getProgress() & currentRemovedMaterial < currentItemAmount) {
                    removedMaterial1 += 1;
                    currentRemovedMaterial += 1;
                    listRemovedMaterial1.add(listItem.noReferenceCopy(ListInventaire.get(i)));
                    listRemovedMaterial1.get(listRemovedMaterial1.size()-1).setNbrItem(1);
                }
                /*recyclage*/
                recycled = 0;
                for (int i2 = 0; i2 < currentRemovedMaterial; i2++) {
                    int random = (int)(Math.random() * 100) + 1;
                    float a = (float) itemMarteau.getMetadata().getMarteau().getRecyclage()/10;
                    int recycleRate = Math.round((float) 50*a/(a+1));
                    if (random < recycleRate) {
                        recycled ++;
                    }
                }
                recycledMaterial1 += recycled;
                ListInventaire.get(i).setNbrItem(ListInventaire.get(i).getNbrItem() - (currentRemovedMaterial - recycled));
                if (removedMaterial1 == slider.getProgress()) {
                    if (isMaterial2Removed & isMaterial3Removed) {
                        break;
                    }
                    else {
                        isMaterial1Removed = true;
                    }
                }
                if (currentItemAmount == currentRemovedMaterial) {
                    continue;
                }
            }
            if (!isMaterial2Removed & cadreMaterial2.getDrawable().getConstantState() == AppCompatResources.getDrawable(getContext(), ListInventaire.get(i).getImageItem()).getConstantState()) {
                currentItemAmount = ListInventaire.get(i).getNbrItem();
                currentRemovedMaterial = 0;
                while (removedMaterial2 < slider.getProgress() & currentRemovedMaterial < currentItemAmount) {
                    removedMaterial2 += 1;
                    currentRemovedMaterial += 1;
                    listRemovedMaterial2.add(listItem.noReferenceCopy(ListInventaire.get(i)));
                    listRemovedMaterial2.get(listRemovedMaterial2.size()-1).setNbrItem(1);
                }
                /*recyclage*/
                recycled = 0;
                for (int i2 = 0; i2 < currentRemovedMaterial; i2++) {
                    int random = (int)(Math.random() * 100) + 1;
                    float a = (float) itemMarteau.getMetadata().getMarteau().getRecyclage()/10;
                    int recycleRate = Math.round((float) 50*a/(a+1));
                    if (random < recycleRate) {
                        recycled ++;
                    }
                }
                recycledMaterial2 += recycled;
                ListInventaire.get(i).setNbrItem(ListInventaire.get(i).getNbrItem() - (currentRemovedMaterial - recycled));
                if (removedMaterial2 == slider.getProgress()) {
                    if (isMaterial1Removed & isMaterial3Removed) {
                        break;
                    }
                    else {
                        isMaterial2Removed = true;
                    }
                }
                if (currentItemAmount == currentRemovedMaterial) {
                    continue;
                }
            }
            if (!isMaterial3Removed & cadreMaterial3.getDrawable().getConstantState() == AppCompatResources.getDrawable(getContext(), ListInventaire.get(i).getImageItem()).getConstantState()) {
                currentItemAmount = ListInventaire.get(i).getNbrItem();
                currentRemovedMaterial = 0;
                while (removedMaterial3 < slider.getProgress() & currentRemovedMaterial < currentItemAmount) {
                    removedMaterial3 += 1;
                    currentRemovedMaterial += 1;
                    listRemovedMaterial3.add(listItem.noReferenceCopy(ListInventaire.get(i)));
                    listRemovedMaterial3.get(listRemovedMaterial3.size()-1).setNbrItem(1);
                }
                /*recyclage*/
                recycled = 0;
                for (int i2 = 0; i2 < currentRemovedMaterial; i2++) {
                    int random = (int)(Math.random() * 100) + 1;
                    float a = (float) itemMarteau.getMetadata().getMarteau().getRecyclage()/10;
                    int recycleRate = Math.round((float) 50*a/(a+1));
                    if (random < recycleRate) {
                        recycled ++;
                    }
                }
                recycledMaterial3 += recycled;
                ListInventaire.get(i).setNbrItem(ListInventaire.get(i).getNbrItem() - (currentRemovedMaterial - recycled));
                if (removedMaterial3 == slider.getProgress()) {
                    if (isMaterial1Removed & isMaterial2Removed) {
                        break;
                    }
                    else {
                        isMaterial3Removed = true;
                    }
                }
            }
        }
        int craftId = itemCraftId + listItemCraftTier.get(0);
        if (listRemovedMaterial1.size() == slider.getProgress() & listRemovedMaterial2.size() == slider.getProgress() & listRemovedMaterial3.size() == slider.getProgress()) {
            for (int i = 0; i < slider.getProgress(); i++) {
                Item itemCraft = new Item(1, listItem.getItem(craftId).getImageItem(), listItem.getItem(craftId).getImageCadre(), craftId, listItem.getItem(craftId).getNomRessource(), listItem.getItem(craftId).getDescriptionRessource(), listItem.getAverageMetadata(listRemovedMaterial1.get(i).getMetadata(), listRemovedMaterial2.get(i).getMetadata(), listRemovedMaterial3.get(i).getMetadata(), itemCraftId, itemCraftMultiplier));
                ListInventaire.add(listItem.noReferenceCopy(itemCraft));
                if (listAnimCraft.isEmpty()) {
                    itemCraft.setNbrItem(slider.getProgress());
                    listAnimCraft.add(listItem.noReferenceCopy(itemCraft));
                }
            }
        }
        ListInventaire = new ArrayList<>(listItem.removeNullItem(ListInventaire));
        groupedList = new ArrayList<>();
        for (Item item: ListInventaire) {
            groupedList.add(listItem.noReferenceCopy(item));
        }
        groupedList = new ArrayList<>(listItem.groupInventaire(groupedList));

        /*animation*/
        if (recycledMaterial1 > 0) {
            Item item = itemMaterial1;
            /*quantité affichée lors de l'animation*/
            item.setNbrItem(recycledMaterial1);
            listAnimCraft.add(item);
        }
        if (recycledMaterial2 > 0) {
            Item item = itemMaterial2;
            item.setNbrItem(recycledMaterial2);
            listAnimCraft.add(item);
        }
        if (recycledMaterial3 > 0) {
            Item item = itemMaterial3;
            item.setNbrItem(recycledMaterial3);
            listAnimCraft.add(item);
        }
        imageViewAnim.setImageResource(listAnimCraft.get(0).getImageItem());
        textViewAnim.setText(""+listAnimCraft.get(0).getNbrItem());
        imageViewAnim.startAnimation(animDrop);
        textViewAnim.startAnimation(animDrop);
        nbrAnim++;
        animEnd = true;

        cadreMaterial1.setImageResource(currentRecipie.getDrawableMaterial1());
        cadreMaterial2.setImageResource(currentRecipie.getDrawableMaterial2());
        cadreMaterial3.setImageResource(currentRecipie.getDrawableMaterial3());
        cadreMarteau.setImageResource(R.drawable.marteau_modele);
        slider.setMax(0);
        setCurrentRecipie();
        interfaceTablayout.onJsonReceived(listItem.getItemToJsonInventaire(ListInventaire, getContext()));
    }
}