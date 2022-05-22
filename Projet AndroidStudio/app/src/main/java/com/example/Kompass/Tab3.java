package com.example.Kompass;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Tab3 extends Fragment {

    Button button;
    ListItem listItem = new ListItem();

    ArrayList<Item> ListInventaire = new ArrayList<>();
    String jsonInventaire, token;

    int progressEndurance, progressFaim, progressSante;

    interfaceTablayout interfaceTablayout;
    public Tab3(interfaceTablayout interfaceTablayout) {
        this.interfaceTablayout = interfaceTablayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), fouille.class);
                jsonInventaire = listItem.getItemToJsonInventaire(ListInventaire, getContext());
                intent.putExtra("jsonInventaire", jsonInventaire);
                intent.putExtra("progressEndurance", progressEndurance);
                intent.putExtra("progressFaim", progressFaim);
                intent.putExtra("token", token);
                intent.putExtra("progressSante", progressSante);
                startActivity(intent);
            }
        });
        return view;
    }

    public void getVariable() {
        ListInventaire = new ArrayList<>();
        jsonInventaire = getArguments().getString("JSONArrayInventaire");
        progressEndurance = getArguments().getInt("progressEndurance");
        progressFaim = getArguments().getInt("progressFaim");
        progressSante = getArguments().getInt("progressSante");
        token = getArguments().getString("token");
        ListInventaire = new ArrayList<>(listItem.getJsonToItemInventaire(jsonInventaire, getContext()));
    }
}