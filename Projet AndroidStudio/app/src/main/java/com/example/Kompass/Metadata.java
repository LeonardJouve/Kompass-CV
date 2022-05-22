package com.example.Kompass;

public class Metadata {
    Nourriture nourriture;
    Equipement equipement;
    Arme arme;
    Outil outil;
    Marteau marteau;

    public Metadata(Nourriture nourriture, Equipement equipement, Arme arme, Outil outil, Marteau marteau) {
        this.nourriture = nourriture;
        this.equipement = equipement;
        this.arme = arme;
        this.outil = outil;
        this.marteau = marteau;
    }

    public Nourriture getNourriture() {
        return nourriture;
    }

    public void setNourriture(Nourriture nourriture) {
        this.nourriture = nourriture;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public Arme getArme() {
        return arme;
    }

    public void setArme(Arme arme) {
        this.arme = arme;
    }

    public Outil getOutil() {
        return outil;
    }

    public void setOutil(Outil outil) {
        this.outil = outil;
    }

    public Marteau getMarteau() {
        return marteau;
    }

    public void setMarteau(Marteau marteau) {
        this.marteau = marteau;
    }

    public String toJsonMetadata() {
        String stringMetadata = "{"
        + "\n\"nourritureFaim\": " + nourriture.getFaim() + ","
        + "\n\"nourritureEndurance\": " + nourriture.getEndurance() + ","
        + "\n\"nourritureForce\": " + nourriture.getForce() + ","
        + "\n\"equipementPoids\": " + equipement.getPoids() + ","
        + "\n\"equipementDefense\": " + equipement.getDefense() + ","
        + "\n\"equipementSante\": " + equipement.getSante() + ","
        + "\n\"equipementRenvoie\": " + equipement.getRenvoie() + ","
        + "\n\"equipementChance\": " + equipement.getChance() + ","
        + "\n\"equipementQuantite\": " + equipement.getQuantite() + ","
        + "\n\"equipementEndurance\": " + equipement.getEndurance() + ","
        + "\n\"equipementForce\": " + equipement.getForce() + ","
        + "\n\"armeForce\": " + arme.getForce() + ","
        + "\n\"armeVitesse\": " + arme.getVitesse() + ","
        + "\n\"armeReputation\": " + arme.getReputation() + ","
        + "\n\"armeQuantite\": " + arme.getQuantite() + ","
        + "\n\"outilEfficacite\": " + outil.getEfficacite() + ","
        + "\n\"outilChance\": " + outil.getChance() + ","
        + "\n\"outilQuantite\": " + outil.getQuantite() + ","
        + "\n\"marteauRecyclage\": " + marteau.getRecyclage() + ","
        + "\n\"marteauVitesse\": " + marteau.getVitesse() + "\n}";
        return stringMetadata;
    }
}