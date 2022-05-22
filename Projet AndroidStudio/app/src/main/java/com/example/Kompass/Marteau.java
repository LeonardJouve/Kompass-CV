package com.example.Kompass;

public class Marteau {
    Integer recyclage;
    Integer vitesse;

    public Marteau(Integer recyclage, Integer vitesse) {
        this.recyclage = recyclage;
        this.vitesse = vitesse;
    }

    public Integer getRecyclage() {
        return recyclage;
    }

    public void setRecyclage(Integer recyclage) {
        this.recyclage = recyclage;
    }

    public Integer getVitesse() {
        return vitesse;
    }

    public void setVitesse(Integer vitesse) {
        this.vitesse = vitesse;
    }
}
