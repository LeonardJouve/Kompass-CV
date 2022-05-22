package com.example.Kompass;

public class Outil {
    Integer efficacite;
    Integer chance; /*-> augmente le nombre lancé de drop*/
    Integer quantite; /*-> augmente le nombre de ressources obtenues d'un certain type*/

    public Outil(Integer efficacite, Integer chance, Integer quantite) {
        this.efficacite = efficacite;
        this.chance = chance;
        this.quantite = quantite;
    }

    public Integer getEfficacite() {
        return efficacite;
    }

    public void setEfficacite(Integer efficacite) {
        this.efficacite = efficacite;
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
}
