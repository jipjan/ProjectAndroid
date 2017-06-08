package com.experience.essteling.a3.esstelingexperience.Entities;

import java.util.ArrayList;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class SensorDataList extends ArrayList<SensorData> {
    public long startMs() { if (size() > 0) return get(0).Time; return -1; }
}
