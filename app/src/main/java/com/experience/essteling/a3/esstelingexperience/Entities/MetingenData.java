package com.experience.essteling.a3.esstelingexperience.Entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class MetingenData extends HashMap<Integer, AttractieData> implements Serializable {
    public static MetingenData ITEMS = new MetingenData();

    private MetingenData() {}

    public AttractieData getListOrNew(int id) {
        if (containsKey(id)) {
            return get(id);
        } else {
            AttractieData data = new AttractieData();
            put(id, data);
            return data;
        }
    }
}
