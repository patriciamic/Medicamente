package com.example.medicamente.entities;

public class Hour {
    private String nume;
    private String id;

    public Hour(String nume, String id) {
        this.nume = nume;
        this.id = id;
    }
    public Hour(){}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
