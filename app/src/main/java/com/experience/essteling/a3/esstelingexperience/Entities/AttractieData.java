package com.experience.essteling.a3.esstelingexperience.Entities;

import java.util.ArrayList;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class AttractieData extends ArrayList<SensorData> {
    public SensorData newSensorData() {
        SensorData data = new SensorData();
        add(data);
        return data;
    }
}
