package com.example.Kompass;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Tab1 extends Fragment {

    ListItem listItem = new ListItem();
    CustomAdapter customAdapter;

    GridView Grille;
    ImageView fondItemClick;
    ImageView imageItemClick;
    ImageView closeItemClick;
    ImageView openFiltre;
    TextView textItemClick;
    TextView nbrItemClick;
    Button buttonUse;

    int progressEndurance, progressFaim, progressSante;
    Item currentItem;

    Context thisContext;

    ArrayList<Item> ListInventaire = new ArrayList<>();
    ArrayList<Item> groupedList = new ArrayList<>();
    ArrayList<Item> ListInventaireFiltre = new ArrayList<>();
    ArrayList<Integer> ListCategorieRessources = new ArrayList<Integer>(Arrays.asList(1, 210));
    ArrayList<Integer> ListCategorieMinerais = new ArrayList<Integer>(Arrays.asList(10, 11, 200, 201, 202));
    ArrayList<Integer> ListCategorieNourriture = new ArrayList<Integer>(Arrays.asList(20, 21, 100, 110));
    ArrayList<Integer> ListCategorieEquipement = new ArrayList<Integer>(Arrays.asList(300, 301, 302, 310, 311, 312, 320, 321, 322, 330, 331, 332, 400, 401, 402, 410, 411, 412, 420, 421, 422, 500, 501, 502, 510, 511, 512, 520, 521, 522, 530, 531, 532, 540, 541, 542));

    int nbr_item = 40; /* = nombre de case minimum dans l'inventaire*/
    int nbrItemSansCadre = 0;

    interfaceTablayout interfaceTablayout;
    public Tab1(interfaceTablayout interfaceTablayout) {
        this.interfaceTablayout = interfaceTablayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        Grille = view.findViewById(R.id.Grid);
        fondItemClick = view.findViewById(R.id.fondItemClick);
        imageItemClick = view.findViewById(R.id.imageItemClick);
        closeItemClick = view.findViewById(R.id.closeItemClick);
        openFiltre = view.findViewById(R.id.openFiltre);
        textItemClick = view.findViewById(R.id.textItemClick);
        nbrItemClick = view.findViewById(R.id.nbrItemClick);
        buttonUse = view.findViewById(R.id.buttonUse);

        thisContext= container.getContext();
        getVariable();

        Grille.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nbrItemSansCadre = 0;
                if (ListInventaireFiltre.size() > 0) {
                    for (int i2 = 0; i2 < ListInventaireFiltre.size(); i2 ++) {
                        if (ListInventaireFiltre.get(i2).getRessourceId() > 0 ) {
                            nbrItemSansCadre += 1;
                        }
                    }
                    if (i < nbrItemSansCadre) {
                        if (ListInventaireFiltre.get(i).getRessourceId() >= 100 & ListInventaireFiltre.get(i).getRessourceId() < 200) {
                            buttonUse.setVisibility(View.VISIBLE);
                        }
                        else {
                            buttonUse.setVisibility(View.GONE);
                        }
                        imageItemClick.setImageResource(ListInventaireFiltre.get(i).getImageItem());
                        textItemClick.setText(ListInventaireFiltre.get(i).getDescriptionRessource());
                        nbrItemClick.setText(String.valueOf(ListInventaireFiltre.get(i).getNbrItem()));
                        currentItem = ListInventaireFiltre.get(i);

                        fondItemClick.setVisibility(View.VISIBLE);
                        imageItemClick.setVisibility(View.VISIBLE);
                        closeItemClick.setVisibility(View.VISIBLE);
                        textItemClick.setVisibility(View.VISIBLE);
                        nbrItemClick.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        closeItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fondItemClick.setVisibility(View.GONE);
                imageItemClick.setVisibility(View.GONE);
                closeItemClick.setVisibility(View.GONE);
                textItemClick.setVisibility(View.GONE);
                nbrItemClick.setVisibility(View.GONE);
            }
        });

        openFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ListInventaireFiltre.size() > 0) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.item1:
                                    /*A-Z*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (Item item: groupedList) {
                                        ListInventaireFiltre.add(listItem.noReferenceCopy(item));
                                    }
                                    Collections.sort(ListInventaireFiltre, new Comparator<Item>() {
                                        @Override
                                        public int compare(Item i1, Item i2) {
                                            return i1.getNomRessource().compareTo(i2.getNomRessource());
                                        }
                                    });
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "", null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.item2:
                                    /*Z-A*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (Item item: groupedList) {
                                        ListInventaireFiltre.add(listItem.noReferenceCopy(item));
                                    }
                                    Collections.sort(ListInventaireFiltre, new Comparator<Item>() {
                                        @Override
                                        public int compare(Item i1, Item i2) {
                                            return i1.getNomRessource().compareTo(i2.getNomRessource());
                                        }
                                    });
                                    Collections.reverse(ListInventaireFiltre);
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.item3:
                                    /*<*/
                                    /*getNbrItem() sur les cadres donnes "" -> pas int*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (Item item: groupedList) {
                                        ListInventaireFiltre.add(listItem.noReferenceCopy(item));
                                    }
                                    Collections.sort(ListInventaireFiltre, new Comparator<Item>() {
                                        @Override
                                        public int compare(Item i1, Item i2) {
                                            return i1.getNbrItem() - i2.getNbrItem();
                                        }
                                    });
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.item4:
                                    /*>*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (Item item: groupedList) {
                                        ListInventaireFiltre.add(listItem.noReferenceCopy(item));
                                    }
                                    Collections.sort(ListInventaireFiltre, new Comparator<Item>() {
                                        @Override
                                        public int compare(Item i1, Item i2) {
                                            return i1.getNbrItem() - i2.getNbrItem();
                                        }
                                    });
                                    Collections.reverse(ListInventaireFiltre);
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.subitem1:
                                    /*ressources*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (int i = 0; i < groupedList.size(); i++) {
                                        if (ListCategorieRessources.contains(groupedList.get(i).getRessourceId())) {
                                            ListInventaireFiltre.add(listItem.noReferenceCopy(groupedList.get(i)));
                                        }
                                    }
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.subitem2:
                                    /*minerais*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (int i = 0; i < groupedList.size(); i++) {
                                        if (ListCategorieMinerais.contains(groupedList.get(i).getRessourceId())) {
                                            ListInventaireFiltre.add(listItem.noReferenceCopy(groupedList.get(i)));
                                        }
                                    }
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.subitem3:
                                    /*nourriture*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (int i = 0; i < groupedList.size(); i++) {
                                        if (ListCategorieNourriture.contains(groupedList.get(i).getRessourceId())) {
                                            ListInventaireFiltre.add(listItem.noReferenceCopy(groupedList.get(i)));
                                        }
                                    }
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.subitem4:
                                    /*equipement*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (int i = 0; i < groupedList.size(); i++) {
                                        if (ListCategorieEquipement.contains(groupedList.get(i).getRessourceId())) {
                                            ListInventaireFiltre.add(listItem.noReferenceCopy(groupedList.get(i)));
                                        }
                                    }
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                case R.id.subitem5:
                                    /*toutes*/
                                    ListInventaireFiltre = new ArrayList<>();
                                    for (Item item: groupedList) {
                                        ListInventaireFiltre.add(listItem.noReferenceCopy(item));
                                    }
                                    Collections.sort(ListInventaireFiltre, new Comparator<Item>() {
                                        @Override
                                        public int compare(Item i1, Item i2) {
                                            return i1.getRessourceId() - i2.getRessourceId();
                                        }
                                    });
                                    for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
                                        ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "",null));
                                    }
                                    customAdapter = new CustomAdapter(getContext(), R.layout.grid_items, ListInventaireFiltre);
                                    Grille.setAdapter(customAdapter);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.inflate(R.menu.filtre_menu);
                    popupMenu.show();
                }
            }
        });

        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressEndurance += currentItem.getMetadata().getNourriture().getEndurance();
                progressFaim += currentItem.getMetadata().getNourriture().getFaim();
                if (progressEndurance > 100) {
                    progressEndurance = 100;
                }
                if (progressFaim > 100) {
                    progressFaim = 100;
                }
                for (int i = 0; i < ListInventaire.size(); i++) {
                    if (listItem.checkSameItem(ListInventaire.get(i), currentItem)) {
                        ListInventaire.get(i).setNbrItem(ListInventaire.get(i).getNbrItem()-1);
                        if (ListInventaire.get(i).getNbrItem()<=0) {
                            ListInventaire.remove(i);
                        }
                        initializeList();
                        interfaceTablayout.onJsonReceived(listItem.getItemToJsonInventaire(ListInventaire, getContext()));
                        break;
                    }
                }
                if (currentItem.getNbrItem() == 1) {
                    fondItemClick.setVisibility(View.GONE);
                    imageItemClick.setVisibility(View.GONE);
                    closeItemClick.setVisibility(View.GONE);
                    textItemClick.setVisibility(View.GONE);
                    nbrItemClick.setVisibility(View.GONE);
                }
                else {
                    nbrItemClick.setText(Integer.parseInt((String) nbrItemClick.getText()) - 1);
                }
                interfaceTablayout.onProgressUpdate(progressEndurance, progressFaim, progressSante);
            }
        });

        return view;
    }

    public void getVariable() {
        ListInventaire = new ArrayList<>();
        String jsonInventaire = getArguments().getString("JSONArrayInventaire");
        progressEndurance = getArguments().getInt("progressEndurance");
        progressFaim = getArguments().getInt("progressFaim");
        progressSante = getArguments().getInt("progressSante");
        ListInventaire = new ArrayList<>(listItem.getJsonToItemInventaire(jsonInventaire, getContext()));
        initializeList();
    }

    private void initializeList() {
        groupedList = new ArrayList<>();
        for (Item item: ListInventaire) {
            groupedList.add(listItem.noReferenceCopy(item));
        }
        groupedList = new ArrayList<>(listItem.groupInventaire(groupedList));
        groupedList = new ArrayList<>(listItem.getMetadataDescription(groupedList, progressEndurance, progressFaim));
        ListInventaireFiltre = new ArrayList<>();
        for (Item item: groupedList) {
            ListInventaireFiltre.add(listItem.noReferenceCopy(item));
        }

        for (int i = ListInventaireFiltre.size(); i < nbr_item; i ++) {
            ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "", null));
        }
        while (ListInventaireFiltre.size() % 4 != 0) {
            ListInventaireFiltre.add(new Item(-1, R.drawable.cadre, R.drawable.cadre, 0, "", "", null));
        }

        customAdapter = new CustomAdapter(thisContext, R.layout.grid_items, ListInventaireFiltre);
        Grille.setAdapter(customAdapter);
    }
}