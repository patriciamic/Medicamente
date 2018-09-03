package com.example.medicamente.data;

import com.example.medicamente.entities.Medicament;

import java.util.ArrayList;
import java.util.List;

public class MedicamentsRepository {
    private List<Medicament> medicaments;


    public MedicamentsRepository() {
        medicaments = new ArrayList<>();
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public void delete(Medicament medicament){
        medicaments.remove(medicament);
    }

    public void add(Medicament medicament){
        medicaments.add(medicament);
    }
}
