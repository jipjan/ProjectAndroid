package com.experience.essteling.a3.esstelingexperience;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bob on 18-5-2017.
 */


public class Meeting implements Serializable {
    private Date time;
    private int speed;

    public Meeting(Date time, int speed) {
        this.time = time;
        this.speed = speed;
    }

     public Date getTime() {
        return time;
    }

    public int getSpeed() {
        return speed;
    }
}
