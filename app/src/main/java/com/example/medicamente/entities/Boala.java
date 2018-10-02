package com.example.medicamente.entities;

import java.util.ArrayList;
import java.util.List;

public class Boala {
    private String id;
    private String numeBoala;
    private List<Medicament> medicamentList;

    public Boala() {
        medicamentList = new ArrayList<>();
    }

    public Boala(String numeBoala) {
        this.numeBoala = numeBoala;
        medicamentList = new ArrayList<>();
    }

    public Boala(String numeBoala, List<Medicament> medicamentList) {
        this.numeBoala = numeBoala;
        this.medicamentList = medicamentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeBoala() {
        return numeBoala;
    }

    public void setNumeBoala(String numeBoala) {
        this.numeBoala = numeBoala;
    }

    public List<Medicament> getMedicamentList() {
        return medicamentList;
    }

    public void setMedicamentList(List<Medicament> medicamentList) {
        this.medicamentList = medicamentList;
    }

    public void addMedicament(Medicament medicament) {
        medicament.setIdMed("id" + medicamentList.size() + "");
        medicamentList.add(medicament);
    }

    public Medicament getMedicamentById(String idMed) {
        for (Medicament item : medicamentList) {
            if (item.getIdMed().equals(idMed)) {
                return item;
            }
        }
        return null;
    }

    public void removeMedicament(Medicament medicament) {
        medicamentList.remove(medicament);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Boala)) {
            return false;
        }

        Boala m = (Boala) obj;

        return this.numeBoala.equalsIgnoreCase(m.numeBoala);
    }
}
