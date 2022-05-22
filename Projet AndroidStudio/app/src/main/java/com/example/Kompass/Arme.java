package com.example.Kompass;

public class Arme {
    Integer force;
    Integer vitesse;
    Integer reputation;
    Integer quantite;

    public Arme(Integer force, Integer vitesse, Integer reputation, Integer quantite) {
        this.force = force;
        this.vitesse = vitesse;
        this.reputation = reputation;
        this.quantite = quantite;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }

    public Integer getVitesse() {
        return vitesse;
    }

    public void setVitesse(Integer vitesse) {
        this.vitesse = vitesse;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
