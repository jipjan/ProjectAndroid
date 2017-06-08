package com.experience.essteling.a3.esstelingexperience.Entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class SensorDataAttractie extends HashMap<Integer, SensorDataList> implements Serializable {
    public static SensorDataAttractie ITEMS = new SensorDataAttractie();

    private SensorDataAttractie() {}

    public SensorDataList getListOrNew(int id) {
        if (containsKey(id)) {
            return get(id);
        } else {
            SensorDataList data = new SensorDataList();
            put(id, data);
            return data;
        }
    }
}
