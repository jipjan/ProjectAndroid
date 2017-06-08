package com.experience.essteling.a3.esstelingexperience.Entities;

import java.io.Serializable;

/**
 * Created by Lois Gussenhoven on 18-5-2017.
 */

public class Attractie implements Serializable {
    private String naam;
    private String soort;
    private int id;
    private int image;

    public String getSoort() {
        return soort;
    }

    public int getId() {
        return id;
    }

    public Attractie(String naam, int image, String soort, int id) {
        this.naam = naam;
        this.image = image;
        this.soort = soort;
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }
    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Attractie{" +
                "naam='" + naam + '\'' +
                ", soort='" + soort + '\'' +
                ", id=" + id +
                ", image=" + image +
                '}';
    }
}




