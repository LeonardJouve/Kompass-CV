package com.example.Kompass;

public class Equipement {
    Integer poids;
    Integer defense;
    Integer sante;
    Integer renvoie;
    Integer chance;
    Integer quantite;
    Integer endurance;
    Integer force;

    public Equipement(Integer poids, Integer defense, Integer sante, Integer renvoie, Integer chance, Integer quantite, Integer endurance, Integer force) {
        this.poids = poids;
        this.defense = defense;
        this.sante = sante;
        this.renvoie = renvoie;
        this.chance = chance;
        this.quantite = quantite;
        this.endurance = endurance;
        this.force = force;
    }

    public Integer getPoids() {
        return poids;
    }

    public void setPoids(Integer poids) {
        this.poids = poids;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getSante() {
        return sante;
    }

    public void setSante(Integer sante) {
        this.sante = sante;
    }

    public Integer getRenvoie() {
        return renvoie;
    }

    public void setRenvoie(Integer renvoie) {
        this.renvoie = renvoie;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }
}
