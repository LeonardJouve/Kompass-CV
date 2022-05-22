package com.example.Kompass;

import android.content.Context;
import android.util.Log;

public class Item {

    int nbrItem;
    int imageItem;
    int imageCadre;
    int ressourceId;
    String nomRessource;
    String descriptionRessource;
    Metadata metadata;

    public Item(int nbrItem, int imageItem, int imageCadre, int ressourceId, String nomRessource, String descriptionRessource, Metadata metadata) {
        this.nbrItem = nbrItem;
        this.imageItem = imageItem;
        this.imageCadre = imageCadre;
        this.ressourceId = ressourceId;
        this.nomRessource = nomRessource;
        this.descriptionRessource = descriptionRessource;
        this.metadata = metadata;
    }

    public int getNbrItem() {
        return nbrItem;
    }

    public void setNbrItem(int nbrItem) {
        this.nbrItem = nbrItem;
    }

    public int getImageItem() {
        return imageItem;
    }

    public void setImageItem(int imageItem) {
        this.imageItem = imageItem;
    }

    public int getImageCadre() {
        return imageCadre;
    }

    public void setImageCadre(int imageCadre) {
        this.imageCadre = imageCadre;
    }

    public int getRessourceId() {
        return ressourceId;
    }

    public void setRessourceId(int ressourceId) {
        this.ressourceId = ressourceId;
    }

    public String getNomRessource() {
        return nomRessource;
    }

    public void setNomRessource(String nomRessource) {
        this.nomRessource = nomRessource;
    }

    public String getDescriptionRessource() {
        return descriptionRessource;
    }

    public void setDescriptionRessource(String descriptionRessource) {
        this.descriptionRessource = descriptionRessource;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String toJsonString(Context context) {
        String nomImage = context.getResources().getResourceEntryName(imageItem);
        return "{ \"nbrItem\": " + nbrItem + ", \n" +
                " \"imageItem\": " + imageItem + ", \n" +
                " \"imageCadre\": " + imageCadre + ", \n" +
                /*utilise pour la sauvegarde de l'inventaire dans la base de donnees*/
                " \"nomImage\": " + nomImage + ", \n" +
                " \"ressourceId\": " + ressourceId + ", \n" +
                " \"nomRessource\": \"" + nomRessource + "\", \n" +
                " \"descriptionRessource\": \"" + descriptionRessource + "\", \n" +
                " \"metadata\": " + metadata.toJsonMetadata() + "}";
    }
}