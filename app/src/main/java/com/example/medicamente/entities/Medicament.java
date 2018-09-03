package com.example.medicamente.entities;

public class Medicament {
    private String name;
    private String time;

    public Medicament(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
