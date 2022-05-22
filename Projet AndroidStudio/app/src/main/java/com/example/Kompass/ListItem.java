package com.example.Kompass;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ListItem {

    /*RessourceID
     * 0 / cadre vide
     * 1 -> 9 / bois
     * 10 -> 19 / poudre
     *      -> 10 / poudre cuivre
     *      -> 11 / poudre Zinc
     * 20 -> 29 / nourriture
     * -> 20 / champignons
     * -> 21 / baies
     * 30 -> 39 / salade
     * -> 30 / laitue
     *
     * 100 -> 109 / soupe
     * 110 -> 119 / salade composée
     *
     * 200 -> 209 / lingots minerais
     *      -> 200 / lingot cuivre
     *      -> 201 / lingot Zinc
     *      -> 202 / lingot Laiton
     * 210 -> 219 / batonnets
     * 220 -> 229 / plaques
     *      ->
     *
     * 300 -> 309 / bottes
     * 310 -> 319 / jambières
     * 320 -> 329 / plastron
     * 330 -> 339 / casque
     *
     * 400 -> 409 / épée
     * 410 -> 419 / lance
     * 420 -> 429 / massue
     *
     * 500 -> 509 / pioche
     * 510 -> 519 / pelle
     * 520 -> 529 / hache
     * 530 -> 539 / houe
     * 540 -> 549 / marteau
     *
     * */

    ArrayList<Item> listItem = new ArrayList<Item>(Arrays.asList(
            new Item(0, R.drawable.bois, R.drawable.cadre, 1, "Bois", "Bois \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.poudre_cuivre, R.drawable.cadre, 10, "Poudre de cuivre", "Poudre de cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.poudre_zinc, R.drawable.cadre, 11, "Poudre de zinc", "Poudre de zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.champignons, R.drawable.cadre, 20, "Champignons", "Champignons \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.baies, R.drawable.cadre, 21, "Baies", "Baies \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.laitue, R.drawable.cadre, 30, "Laitue", "Laitue \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.soupe_champignons, R.drawable.cadre,100, "Soupe de champignons", "Soupe de champignons \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.soupe_baies, R.drawable.cadre, 101, "Soupe de baies", "Soupe de baies \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.salade, R.drawable.cadre, 110, "Salade", "Salade de laitue \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.lingot_cuivre, R.drawable.cadre, 200, "Lingot de cuivre", "Lingot de cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.lingot_zinc, R.drawable.cadre, 201, "Lingot de zinc", "Lingot de zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.lingot_laiton, R.drawable.cadre, 202, "Lingot de laiton", "Lingot de laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.baton, R.drawable.cadre, 210, "Baton", "Baton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.plaque_cuivre, R.drawable.cadre, 220, "Plaque de cuivre", "Plaque de cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.plaque_zinc, R.drawable.cadre, 221, "Plaque de zinc", "Plaque de zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.plaque_laiton, R.drawable.cadre, 222, "Plaque de laiton", "Plaque de laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.bottes_cuivre, R.drawable.cadre, 300, "Bottes de cuivre", "Bottes de cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.bottes_zinc, R.drawable.cadre, 301, "Bottes de zinc", "Bottes de zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.bottes_laiton, R.drawable.cadre, 302, "Bottes de laiton", "Bottes de laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.jambieres_cuivre, R.drawable.cadre, 310, "Jambières en cuivre", "Jambières en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.jambieres_zinc, R.drawable.cadre, 311, "Jambières en zinc", "Jambières en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.jambieres_laiton, R.drawable.cadre, 312, "Jambières en laiton", "Jambières de zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.plastron_cuivre, R.drawable.cadre, 320, "Plastron en cuivre", "Plastron en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.plastron_zinc, R.drawable.cadre, 321, "Plastron en zinc", "Plastron en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.plastron_laiton, R.drawable.cadre, 322, "Plastron en laiton", "Plastron en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.casque_cuivre, R.drawable.cadre, 330, "Casque en cuivre", "Casque en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.casque_zinc, R.drawable.cadre, 331, "Casque en zinc", "Casque en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.casque_laiton, R.drawable.cadre, 332, "Casque en laiton", "Casque en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.epee_cuivre, R.drawable.cadre, 400, "Épée en cuivre", "Épée en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.epee_zinc, R.drawable.cadre, 401, "Épée en zinc", "Épée en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.epee_laiton, R.drawable.cadre, 402, "Épée en laiton", "Épée en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.lance_cuivre, R.drawable.cadre, 410, "Lance en cuivre", "Lance en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.lance_zinc, R.drawable.cadre, 411, "Lance en zinc", "Lance en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.lance_laiton, R.drawable.cadre, 412, "Lance en laiton", "Lance en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.massue_cuivre, R.drawable.cadre, 420, "Massue en cuivre", "Massue en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.massue_zinc, R.drawable.cadre, 421, "Massue en zinc", "Massue en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.massue_laiton, R.drawable.cadre, 422, "Massue en laiton", "Massue en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.pioche_cuivre, R.drawable.cadre, 500, "Pioche en cuivre", "Pioche en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.pioche_zinc, R.drawable.cadre, 501, "Pioche en zinc", "Pioche en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.pioche_laiton, R.drawable.cadre, 502, "Pioche en laiton", "Pioche en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.pelle_cuivre, R.drawable.cadre, 510, "Pelle en cuivre", "Pelle en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.pelle_zinc, R.drawable.cadre, 511, "Pelle en zinc", "Pelle en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.pelle_laiton, R.drawable.cadre, 512, "Pelle en laiton", "Pelle en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.hache_cuivre, R.drawable.cadre, 520, "Hache en cuivre", "Hache en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.hache_zinc, R.drawable.cadre, 521, "Hache en zinc", "Hache en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.hache_laiton, R.drawable.cadre, 522, "Hache en laiton", "Hache en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.houe_laiton, R.drawable.cadre, 530, "Houe en cuivre", "Houe en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.houe_zinc, R.drawable.cadre, 531, "Houe en zinc", "Houe en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.houe_laiton, R.drawable.cadre, 532, "Houe en laiton", "Houe en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.marteau_cuivre, R.drawable.cadre, 600, "Marteau en cuivre", "Marteau en cuivre \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.marteau_zinc, R.drawable.cadre, 601, "Marteau en zinc", "Marteau en zinc \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0))),
            new Item(0, R.drawable.marteau_laiton, R.drawable.cadre, 602, "Marteau en laiton", "Marteau en laiton \n ", new Metadata(new Nourriture(0,0,0), new Equipement(0,0,0,0,0,0,0,0), new Arme(0,0,0,0), new Outil(0,0,0), new Marteau(0,0)))
    ));

    public Item getItem(int ressourceId) {
        int i = 0;
        while (listItem.get(i).getRessourceId() != ressourceId) {
            i += 1;
        }
        return listItem.get(i);
    }

    public ArrayList<Item> getMetadataDescription(ArrayList<Item> ListInventaire, int progressEndurance, int progressFaim) {
        ArrayList<Item> MetadataDescription = new ArrayList<>();
        for (int i = 0; i < ListInventaire.size(); i++) {
            MetadataDescription.add(noReferenceCopy(ListInventaire.get(i)));
            if ((MetadataDescription.get(i).getRessourceId() >= 100 & MetadataDescription.get(i).getRessourceId() <= 199) | MetadataDescription.get(i).getRessourceId() >= 300) {
                Metadata currentItemMetadata = MetadataDescription.get(i).getMetadata();
                if (currentItemMetadata.getNourriture() != null) {
                    if (currentItemMetadata.getNourriture().getFaim() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nFaim: " + MetadataDescription.get(i).getMetadata().getNourriture().getFaim());
                        if (progressFaim != 0) {
                            MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "    (actuelle: " + progressFaim + " / 100)");
                        }
                    }
                    if (currentItemMetadata.getNourriture().getEndurance() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nEndurance: " + MetadataDescription.get(i).getMetadata().getNourriture().getEndurance());
                        if (progressEndurance != 0) {
                            MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "    (actuelle: " + progressEndurance + " / 100)");
                        }
                    }
                    if (currentItemMetadata.getNourriture().getForce() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nForce: " + MetadataDescription.get(i).getMetadata().getNourriture().getForce());
                    }
                }
                if (currentItemMetadata.getEquipement() != null) {
                    if (currentItemMetadata.getEquipement().getPoids() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nPoids: " + MetadataDescription.get(i).getMetadata().getEquipement().getPoids());
                    }
                    if (currentItemMetadata.getEquipement().getDefense() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nDéfense: " + MetadataDescription.get(i).getMetadata().getEquipement().getDefense());
                    }
                    if (currentItemMetadata.getEquipement().getSante() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nSanté: " + MetadataDescription.get(i).getMetadata().getEquipement().getSante());
                    }
                    if (currentItemMetadata.getEquipement().getRenvoie() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nRenvoie: " + MetadataDescription.get(i).getMetadata().getEquipement().getRenvoie());
                    }
                    if (currentItemMetadata.getEquipement().getChance() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nChance: " + MetadataDescription.get(i).getMetadata().getEquipement().getChance());
                    }
                    if (currentItemMetadata.getEquipement().getQuantite() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nQuantité: " + MetadataDescription.get(i).getMetadata().getEquipement().getQuantite());
                    }
                    if (currentItemMetadata.getEquipement().getEndurance() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nEndurance: " + MetadataDescription.get(i).getMetadata().getEquipement().getEndurance());
                    }
                    if (currentItemMetadata.getEquipement().getForce() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nForce: " + MetadataDescription.get(i).getMetadata().getEquipement().getForce());
                    }
                }
                if (currentItemMetadata.getArme() != null) {
                    if (currentItemMetadata.getArme().getForce() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nForce: " + MetadataDescription.get(i).getMetadata().getArme().getForce());
                    }
                    if (currentItemMetadata.getArme().getVitesse() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nVitesse: " + MetadataDescription.get(i).getMetadata().getArme().getVitesse());
                    }
                    if (currentItemMetadata.getArme().getReputation() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nRéputation: " + MetadataDescription.get(i).getMetadata().getArme().getReputation());
                    }
                    if (currentItemMetadata.getArme().getQuantite() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nQuantité: " + MetadataDescription.get(i).getMetadata().getArme().getQuantite());
                    }
                }
                if (currentItemMetadata.getOutil() != null) {
                    if (currentItemMetadata.getOutil().getEfficacite() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nEfficacité: " + MetadataDescription.get(i).getMetadata().getOutil().getEfficacite());
                    }
                    if (currentItemMetadata.getOutil().getChance() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nChance: " + MetadataDescription.get(i).getMetadata().getOutil().getChance());
                    }
                    if (currentItemMetadata.getOutil().getQuantite() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nQuantité: " + MetadataDescription.get(i).getMetadata().getOutil().getQuantite());
                    }
                }
                if (currentItemMetadata.getMarteau() != null) {
                    if (currentItemMetadata.getMarteau().getRecyclage() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nRecyclage: " + MetadataDescription.get(i).getMetadata().getMarteau().getRecyclage());
                    }
                    if (currentItemMetadata.getMarteau().getVitesse() != 0) {
                        MetadataDescription.get(i).setDescriptionRessource(MetadataDescription.get(i).getDescriptionRessource() + "\nVitesse: " + MetadataDescription.get(i).getMetadata().getMarteau().getVitesse());
                    }
                }
            }
        }
        return MetadataDescription;
    }

    public ArrayList<Integer> getRessourceIdList() {
        ArrayList<Integer> ressourceIdList = new ArrayList<>();
        for (int i = 0; i < listItem.size(); i++) {
            if (listItem.get(i).getRessourceId() <= 99 | (listItem.get(i).getRessourceId() >= 200 & listItem.get(i).getRessourceId() <= 299)) {
                ressourceIdList.add(listItem.get(i).getRessourceId());
            }
        }
        return ressourceIdList;
    }

    public ArrayList<Item> getJsonToItemInventaire(String jsonInventaire, Context context) {
        ArrayList<Item> ListInventaire = new ArrayList<>();
        try {
            JSONArray JSONArrayInventaire = new JSONArray(jsonInventaire);
            for (int i = 0; i < JSONArrayInventaire.length(); i++) {
                JSONObject currentObject = JSONArrayInventaire.getJSONObject(i);
                int currentNbrItem = currentObject.getInt("nbrItem");
                String currentNomImage = currentObject.getString("nomImage");
                int currentImageItem = context.getResources().getIdentifier(currentNomImage, "drawable", context.getPackageName());
                int currentImageCadre = R.drawable.cadre;
                int currentRessourceId = currentObject.getInt("ressourceId");
                String currentNomRessource = currentObject.getString("nomRessource");
                String currentDescriptionRessource = currentObject.getString("descriptionRessource");
                JSONObject currentObjectMetadata = currentObject.getJSONObject("metadata");
                Metadata currentMetadata = new Metadata(new Nourriture(currentObjectMetadata.getInt("nourritureFaim"), currentObjectMetadata.getInt("nourritureEndurance"), currentObjectMetadata.getInt("nourritureForce")), new Equipement(currentObjectMetadata.getInt("equipementPoids"), currentObjectMetadata.getInt("equipementDefense"), currentObjectMetadata.getInt("equipementSante"), currentObjectMetadata.getInt("equipementRenvoie"), currentObjectMetadata.getInt("equipementChance"), currentObjectMetadata.getInt("equipementQuantite"), currentObjectMetadata.getInt("equipementEndurance"), currentObjectMetadata.getInt("equipementForce")), new Arme(currentObjectMetadata.getInt("armeForce"), currentObjectMetadata.getInt("armeVitesse"), currentObjectMetadata.getInt("armeReputation"), currentObjectMetadata.getInt("armeQuantite")), new Outil(currentObjectMetadata.getInt("outilEfficacite"), currentObjectMetadata.getInt("outilChance"), currentObjectMetadata.getInt("outilQuantite")), new Marteau(currentObjectMetadata.getInt("marteauRecyclage"), currentObjectMetadata.getInt("marteauVitesse")));
                ListInventaire.add(new Item (currentNbrItem, currentImageItem, currentImageCadre, currentRessourceId, currentNomRessource, currentDescriptionRessource, currentMetadata));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ListInventaire;
    }

    public String getItemToJsonInventaire(ArrayList<Item> ListInventaire, Context context){
        StringBuilder jsonInventaire = new StringBuilder();
        jsonInventaire.append("[ \n");
        for (int i = 0; i < ListInventaire.size(); i++) {
            jsonInventaire.append(ListInventaire.get(i).toJsonString(context));
            if (i < ListInventaire.size() - 1) {
                jsonInventaire.append(", \n");
            }
        }
        jsonInventaire.append("\n]");
        return jsonInventaire.toString();
    }

    private Boolean checkMetadata(Item item1, Item item2) {
        return item1.getRessourceId() == item2.getRessourceId() & item1.getMetadata().getNourriture().getFaim() == item2.getMetadata().getNourriture().getFaim() & item1.getMetadata().getNourriture().getEndurance() == item2.getMetadata().getNourriture().getEndurance() & item1.getMetadata().getNourriture().getForce() == item2.getMetadata().getNourriture().getForce() & item1.getMetadata().getEquipement().getPoids() == item2.getMetadata().getEquipement().getPoids() & item1.getMetadata().getEquipement().getDefense() == item2.getMetadata().getEquipement().getDefense() & item1.getMetadata().getEquipement().getSante() == item2.getMetadata().getEquipement().getSante() & item1.getMetadata().getEquipement().getRenvoie() == item2.getMetadata().getEquipement().getRenvoie() & item1.getMetadata().getEquipement().getChance() == item2.getMetadata().getEquipement().getChance() & item1.getMetadata().getEquipement().getQuantite() == item2.getMetadata().getEquipement().getQuantite() & item1.getMetadata().getEquipement().getEndurance() == item2.getMetadata().getEquipement().getEndurance() & item1.getMetadata().getEquipement().getForce() == item2.getMetadata().getEquipement().getForce() & item1.getMetadata().getArme().getForce() == item2.getMetadata().getArme().getForce() & item1.getMetadata().getArme().getVitesse() == item2.getMetadata().getArme().getVitesse() & item1.getMetadata().getArme().getReputation() == item2.getMetadata().getArme().getReputation() & item1.getMetadata().getArme().getQuantite() == item2.getMetadata().getArme().getQuantite() & item1.getMetadata().getOutil().getEfficacite() == item2.getMetadata().getOutil().getEfficacite() & item1.getMetadata().getOutil().getChance() == item2.getMetadata().getOutil().getChance() & item1.getMetadata().getOutil().getQuantite() == item2.getMetadata().getOutil().getQuantite() & item1.getMetadata().getMarteau().getRecyclage() == item2.getMetadata().getMarteau().getRecyclage() & item1.getMetadata().getMarteau().getVitesse() == item2.getMetadata().getMarteau().getVitesse();
    }

    public ArrayList<Item> sortInventaire(ArrayList<Item> ListInventaire) {
        ArrayList<Item> sortedList = new ArrayList<>();
        for (int i = 0; i < ListInventaire.size(); i++) {
            if (sortedList.isEmpty()) {
                sortedList.add(noReferenceCopy(ListInventaire.get(i)));
            }
            else {
                boolean sameMetadata = false;
                for (int i2 = 0; i2 < sortedList.size(); i2++) {
                    if (checkMetadata(sortedList.get(i2), ListInventaire.get(i))) {
                        sortedList.get(i2).setNbrItem(sortedList.get(i2).getNbrItem() + ListInventaire.get(i).getNbrItem());
                        sameMetadata = true;
                        break;
                    }
                }
                if (!sameMetadata) {
                        sortedList.add(noReferenceCopy(ListInventaire.get(i)));
                }
            }
        }
        return sortedList;
    }

    public ArrayList<Item> groupInventaire(ArrayList<Item> list1) {
        ArrayList<Item> list2 = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            boolean grouped = false;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if (list2.get(i2).getRessourceId() == list1.get(i).getRessourceId() & (list1.get(i).getRessourceId() <= 99 | (list1.get(i).getRessourceId() >=200 & list1.get(i).getRessourceId() <= 299))) {
                    list2.get(i2).setNbrItem(list2.get(i2).getNbrItem() + list1.get(i).getNbrItem());
                    grouped = true;
                    break;
                }
            }
            if (!grouped) {
                list2.add(noReferenceCopy(list1.get(i)));
            }
        }
        return list2;
    }

    public Item noReferenceCopy(Item item) {
        Item copy = new Item(
                item.getNbrItem(),
                item.getImageItem(),
                item.getImageCadre(),
                item.getRessourceId(),
                item.getNomRessource(),
                item.getDescriptionRessource(),
                item.getMetadata()
        );
        return copy;
    }

    public String getPreviewMetadata(int type) {
        String text = "";
        switch (type) {
            case 1:
                /*nourriture*/
                text = "faim: 0-10\n" +
                        "endurance: 0-10\n" +
                        "force: 0-10";
                break;
            case 3:
                /*equipement*/
                text = "poids: 0-10\n" +
                        "défense: 0-10\n" +
                        "santé: 0-10\n" +
                        "renvoie: 0-10\n" +
                        "chance: 0-10\n" +
                        "quantité: 0-10\n" +
                        "endurance: 0-10\n" +
                        "force: 0-10\n";
                break;
            case 4:
                /*arme*/
                text = "force: 0-10\n" +
                        "vitesse: 0-10\n" +
                        "réputation: 0-10\n" +
                        "quantité: 0-10";
                break;
            case 5:
                /*outil*/
                text= "efficacité: 0-10\n" +
                        "chance: 0-10\n" +
                        "quantité: 0-10\n";
                break;
            case 6:
                /*marteau*/
                text= "recyclage: 0-10\n" +
                        "vitesse: 0-10";
                break;
        }
        return text;
    }

    public int getItemCraftType(int itemCraftId) {
        int number = itemCraftId;
        int itemCraftType = 0;
        while(number!=0) {
            itemCraftType = number%10;
            number /= 10;
        }
        return itemCraftType;
    }

    public Metadata getAverageMetadata(Metadata metadataItem1, Metadata metadataItem2, Metadata metadataItem3, int itemCraftId, float itemCraftMultiplier) {
        Metadata averageMetadata = new Metadata(
                new Nourriture(
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getNourriture().getFaim() + metadataItem2.getNourriture().getFaim() + metadataItem3.getNourriture().getFaim())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getNourriture().getEndurance() + metadataItem2.getNourriture().getEndurance() + metadataItem3.getNourriture().getEndurance())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getNourriture().getForce() + metadataItem2.getNourriture().getForce() + metadataItem3.getNourriture().getForce())/3*itemCraftMultiplier))),
                new Equipement(
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getPoids() + metadataItem2.getEquipement().getPoids() + metadataItem3.getEquipement().getPoids())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getDefense() + metadataItem2.getEquipement().getDefense() + metadataItem3.getEquipement().getDefense())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getSante() + metadataItem2.getEquipement().getSante() + metadataItem3.getEquipement().getSante())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getRenvoie() + metadataItem2.getEquipement().getRenvoie() + metadataItem3.getEquipement().getRenvoie())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getChance() + metadataItem2.getEquipement().getChance() + metadataItem3.getEquipement().getChance())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getQuantite() + metadataItem2.getEquipement().getQuantite() + metadataItem3.getEquipement().getQuantite())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getEndurance() + metadataItem2.getEquipement().getEndurance() + metadataItem3.getEquipement().getEndurance())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getEquipement().getForce() + metadataItem2.getEquipement().getForce() + metadataItem3.getEquipement().getForce())/3*itemCraftMultiplier))),
                new Arme(
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getArme().getForce() + metadataItem2.getArme().getForce() + metadataItem3.getArme().getForce())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getArme().getVitesse() + metadataItem2.getArme().getVitesse() + metadataItem3.getArme().getVitesse())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getArme().getReputation() + metadataItem2.getArme().getReputation() + metadataItem3.getArme().getReputation())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getArme().getQuantite() + metadataItem2.getArme().getQuantite() + metadataItem3.getArme().getQuantite())/3*itemCraftMultiplier))),
                new Outil(
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getOutil().getEfficacite() + metadataItem2.getOutil().getEfficacite() + metadataItem3.getOutil().getEfficacite())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getOutil().getChance() + metadataItem2.getOutil().getChance() + metadataItem3.getOutil().getChance())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getOutil().getQuantite() + metadataItem2.getOutil().getQuantite() + metadataItem3.getOutil().getQuantite())/3*itemCraftMultiplier))),
                new Marteau(
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getMarteau().getRecyclage() + metadataItem2.getMarteau().getRecyclage() + metadataItem3.getMarteau().getRecyclage())/3*itemCraftMultiplier)),
                        Math.round(random(0.8f, 1.2f)*((metadataItem1.getMarteau().getVitesse() + metadataItem2.getMarteau().getVitesse() + metadataItem3.getMarteau().getVitesse())/3*itemCraftMultiplier)))
        );

        int itemCraftType = getItemCraftType(itemCraftId);
        switch (itemCraftType) {
            case 1:
                /*nourriture*/
                averageMetadata = new Metadata(averageMetadata.getNourriture(), new Equipement(0, 0, 0, 0, 0, 0, 0, 0), new Arme(0, 0, 0, 0), new Outil(0, 0, 0), new Marteau(0, 0));
                break;
            case 3:
                /*equipement*/
                averageMetadata = new Metadata(new Nourriture(0, 0, 0), averageMetadata.getEquipement(), new Arme(0, 0, 0, 0), new Outil(0, 0, 0), new Marteau(0, 0));
                break;
            case 4:
                /*arme*/
                averageMetadata = new Metadata(new Nourriture(0, 0, 0), new Equipement(0, 0, 0, 0, 0, 0, 0, 0), averageMetadata.getArme(), new Outil(0, 0, 0), new Marteau(0, 0));
                break;
            case 5:
                /*outil*/
                averageMetadata = new Metadata(new Nourriture(0, 0, 0), new Equipement(0, 0, 0, 0, 0, 0, 0, 0), new Arme(0, 0, 0, 0), averageMetadata.getOutil(), new Marteau(0, 0));
                break;
            case 6:
                /*marteau*/
                averageMetadata = new Metadata(new Nourriture(0, 0, 0), new Equipement(0, 0, 0, 0, 0, 0, 0, 0), new Arme(0, 0, 0, 0), new Outil(0, 0, 0), averageMetadata.getMarteau());
                break;
        }
        return averageMetadata;
    }

    public ArrayList<Item> removeNullItem(ArrayList<Item> listInventaire) {
        for (int i = 0; i < listInventaire.size(); i++) {
            if (listInventaire.get(i).getNbrItem() < 1) {
                listInventaire.remove(i);
                i --;
            }
        }
        return  listInventaire;
    }

    public float random(float min, float max) {
        return (float) (Math.random() * (max-min) + min);
    }

    public Boolean checkSameItem(Item item1, Item item2) {
        if (item1.getRessourceId() == item2.getRessourceId() & item1.getNbrItem() == item2.getNbrItem() & item1.getMetadata().getArme().getForce() == item2.getMetadata().getArme().getForce() & item1.getMetadata().getArme().getQuantite() == item2.getMetadata().getArme().getQuantite() &item1.getMetadata().getArme().getReputation() == item2.getMetadata().getArme().getReputation() & item1.getMetadata().getArme().getVitesse() == item2.getMetadata().getArme().getVitesse() & item1.getMetadata().getEquipement().getForce() == item2.getMetadata().getEquipement().getForce() & item1.getMetadata().getEquipement().getQuantite() == item2.getMetadata().getEquipement().getQuantite() & item1.getMetadata().getEquipement().getDefense() == item2.getMetadata().getEquipement().getDefense() & item1.getMetadata().getEquipement().getEndurance() == item2.getMetadata().getEquipement().getEndurance() & item1.getMetadata().getEquipement().getPoids() == item2.getMetadata().getEquipement().getPoids() & item1.getMetadata().getEquipement().getChance() == item2.getMetadata().getEquipement().getChance() & item1.getMetadata().getEquipement().getRenvoie() == item2.getMetadata().getEquipement().getRenvoie() & item1.getMetadata().getEquipement().getSante() == item2.getMetadata().getEquipement().getSante() & item1.getMetadata().getNourriture().getEndurance() == item2.getMetadata().getNourriture().getEndurance() & item1.getMetadata().getNourriture().getForce() == item2.getMetadata().getNourriture().getForce() & item1.getMetadata().getNourriture().getFaim() == item2.getMetadata().getNourriture().getFaim() & item1.getMetadata().getMarteau().getVitesse() == item2.getMetadata().getMarteau().getVitesse() & item1.getMetadata().getMarteau().getRecyclage() == item2.getMetadata().getMarteau().getRecyclage() & item1.getMetadata().getOutil().getChance() == item2.getMetadata().getOutil().getChance() & item1.getMetadata().getOutil().getQuantite() == item2.getMetadata().getOutil().getQuantite() & item1.getMetadata().getOutil().getEfficacite() == item2.getMetadata().getOutil().getEfficacite()) {
            return true;
        }
        else {
            return false;
        }
    }
}