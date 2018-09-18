package com.example.medicamente.data;

import com.example.medicamente.entities.Boala;
import com.example.medicamente.entities.Medicament;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private int id;
    private static Storage instance;
    private final List<Boala> boalaList;

    private Storage() {
        this.boalaList = new ArrayList<>();
    }

    public static Storage getInstance() {
        if (instance == null) {
            synchronized (Storage.class) {
                if (instance == null) {
                    instance = new Storage();
                }
            }
        }
        return instance;
    }

    public List<Boala> getBoli() {
        return new ArrayList<>(boalaList);
    }

    public void addBoala(Boala boala) {
        boala.setId("boala" + id++);
        boalaList.add(boala);

    }

    public Boala getBoala(String id){
        for(Boala item: boalaList){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    public Boala removeBoala(Boala boala) {
        boalaList.remove(boala);
        return boala;
    }


    public Boala updateBoala(Boala boala) {
        for(Boala item: boalaList){
            if(item.getId().equals(boala.getId())){
                item.setNumeBoala(boala.getNumeBoala());
                item.setMedicamentList(boala.getMedicamentList());
            }
        }
        return boala;
    }


}
