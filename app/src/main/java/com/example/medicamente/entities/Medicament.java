package com.example.medicamente.entities;

public class Medicament {
    private String name;
    private String idMed;

    public Medicament(String name) {
        this.name = name;
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
