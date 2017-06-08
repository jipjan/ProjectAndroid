package com.experience.essteling.a3.esstelingexperience;

import java.util.Date;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class SensorData {
    final long Time;
    final int Speed;
    final int Height;

    public SensorData(int speed, int height, long ms) {
        Time = ms;
        Speed = speed;
        Height = height;
    }
}
