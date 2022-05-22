package com.example.Kompass;

public class Nourriture {
    Integer faim;
    Integer endurance;
    Integer force;

    public Nourriture(Integer faim, Integer endurance, Integer force) {
        this.faim = faim;
        this.endurance = endurance;
        this.force = force;
    }

    public Integer getFaim() {
        return faim;
    }

    public void setFaim(Integer faim) {
        this.faim = faim;
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
