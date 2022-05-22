package com.example.Kompass;

import java.util.ArrayList;

public class Recipie {
    Integer drawableMaterial1;
    Integer drawableMaterial2;
    Integer drawableMaterial3;
    ArrayList<Integer> listMaterial1;
    ArrayList<Integer> listMaterial2;
    ArrayList<Integer> listMaterial3;

    public Recipie(Integer drawableMaterial1, Integer drawableMaterial2, Integer drawableMaterial3, ArrayList<Integer> listMaterial1, ArrayList<Integer> listMaterial2, ArrayList<Integer> listMaterial3) {
        this.drawableMaterial1 = drawableMaterial1;
        this.drawableMaterial2 = drawableMaterial2;
        this.drawableMaterial3 = drawableMaterial3;
        this.listMaterial1 = listMaterial1;
        this.listMaterial2 = listMaterial2;
        this.listMaterial3 = listMaterial3;
    }

    public Integer getDrawableMaterial1() {
        return drawableMaterial1;
    }

    public void setDrawableMaterial1(Integer drawableMaterial1) {
        this.drawableMaterial1 = drawableMaterial1;
    }

    public Integer getDrawableMaterial2() {
        return drawableMaterial2;
    }

    public void setDrawableMaterial2(Integer drawableMaterial2) {
        this.drawableMaterial2 = drawableMaterial2;
    }

    public Integer getDrawableMaterial3() {
        return drawableMaterial3;
    }

    public void setDrawableMaterial3(Integer drawableMaterial3) {
        this.drawableMaterial3 = drawableMaterial3;
    }

    public ArrayList<Integer> getListMaterial1() {
        return listMaterial1;
    }

    public void setListMaterial1(ArrayList<Integer> listMaterial1) {
        this.listMaterial1 = listMaterial1;
    }

    public ArrayList<Integer> getListMaterial2() {
        return listMaterial2;
    }

    public void setListMaterial2(ArrayList<Integer> listMaterial2) {
        this.listMaterial2 = listMaterial2;
    }

    public ArrayList<Integer> getListMaterial3() {
        return listMaterial3;
    }

    public void setListMaterial3(ArrayList<Integer> listMaterial3) {
        this.listMaterial3 = listMaterial3;
    }
}
