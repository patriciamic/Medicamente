package com.example.medicamente.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Timp {
    Timestamp inceput;
    Timestamp sfarsit;
    List<String> timpi;

    public Timp(){
        this.timpi = new ArrayList<>();
    }


    public Timestamp getInceput() {
        return inceput;
    }

    public void setInceput(Timestamp inceput) {
        this.inceput = inceput;
    }

    public Timestamp getSfarsit() {
        return sfarsit;
    }

    public void setSfarsit(Timestamp sfarsit) {
        this.sfarsit = sfarsit;
    }

    public List<String> getTimpi() {
        return timpi;
    }

    public void setTimpi(List<String> timpi) {
        this.timpi = timpi;
    }
}
