package com.example.medicamente.entities;

import java.util.ArrayList;
import java.util.List;

public class Medicament {
    private String name;
    private String idMed;
    private List<Hour> hours;
    private int intervalZi;
    private int nrZile;
    private String date;

    public Medicament(String name) {
        this.name = name;
        this.hours = new ArrayList<>();
    }

    public String getIdMed() {
        return idMed;
    }

    public void setIdMed(String idMed) {
        this.idMed = idMed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hour> getHours() {
        return hours;
    }

    public int getNrZile() {
        return nrZile;
    }

    public void setNrZile(int nrZile) {
        this.nrZile = nrZile;
    }

    public int getIntervalZi() {
        return intervalZi;
    }

    public void setIntervalZi(int intervalZi) {
        this.intervalZi = intervalZi;
    }

    public void setHours(List<Hour> hours) {
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Medicament)) {
            return false;
        }

        Medicament m = (Medicament) obj;

        return this.name.equalsIgnoreCase(m.name);
    }
}
