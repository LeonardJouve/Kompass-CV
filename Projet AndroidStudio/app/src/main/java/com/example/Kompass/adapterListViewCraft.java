package com.example.Kompass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterListViewCraft extends ArrayAdapter<Item> {

    ArrayList<Item> List = new ArrayList<>();

    public adapterListViewCraft(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        List = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.listview_craft, null);
        ImageView imageViewRessource = v.findViewById(R.id.imageViewRessource);
        TextView textViewNom = v.findViewById(R.id.textViewNom);
        TextView textViewNombre = v.findViewById(R.id.textViewNombre);
        if (List.get(position).getRessourceId() < 600 || List.get(position).getRessourceId() > 610) {
            textViewNombre.setText(String.valueOf(List.get(position).getNbrItem()));
        }
        imageViewRessource.setImageResource(List.get(position).getImageItem());
        textViewNom.setText(List.get(position).getNomRessource());
        return v;
    }
}
