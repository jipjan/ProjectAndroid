package com.experience.essteling.a3.esstelingexperience;

import java.io.Serializable;

/**
 * Created by Lois Gussenhoven on 18-5-2017.
 */

public class Attractie implements Serializable{
    private String naam;
    private int image;

    public Attractie(String naam, int image) {
        this.naam = naam;
        this.image = image;
    }

    public String getNaam() {
        return naam;
    }
    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Attractie {" +
                "naam ='" + naam + '\'' +
                ", imageNaam ='" + image + '\'' +
                '}';
    }
}




