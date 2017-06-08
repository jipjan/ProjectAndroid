package com.experience.essteling.a3.esstelingexperience.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lois Gussenhoven on 8-6-2017.
 */

public class SensorData extends ArrayList<Data> implements Serializable {

    public SensorData() { }

    public SensorData(ArrayList<Data> data) {
        addAll(data);
    }

    public double getAverageSpeed() {
        double speed = 0;
        for (Data d : this)
            speed += d.Speed;
        return speed / size();
    }

    public double getAverageHeight() {
        double height = 0;
        for (Data d : this)
            height += d.Height;
        return height / size();
    }

    public double getHighestSpeed() {
        double highest = 0;
        for (Data d : this) {
            if (d.Speed > highest)
                highest = d.Speed;
        }
        return highest;
    }

    public double getHighestPoint() {
        double highest = 0;
        for (Data d : this) {
            if (d.Height > highest)
                highest = d.Height;
        }
        return highest;
    }
}
