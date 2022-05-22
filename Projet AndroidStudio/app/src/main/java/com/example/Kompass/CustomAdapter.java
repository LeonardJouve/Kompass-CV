package com.example.Kompass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Item> {

    ArrayList<Item> List = new ArrayList<>();
    ListItem listItem = new ListItem();

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        for (Item item: objects) {
            List.add(listItem.noReferenceCopy(item));
        }
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_items, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        ImageView border = (ImageView) v.findViewById(R.id.border);
        if (List.get(position).getNbrItem() != -1) {
            textView.setText(String.valueOf(List.get(position).getNbrItem()));
        }
        imageView.setImageResource(List.get(position).getImageItem());
        border.setImageResource(List.get(position).getImageCadre());
        return v;
    }
}