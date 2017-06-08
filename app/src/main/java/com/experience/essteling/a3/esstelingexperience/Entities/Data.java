package com.experience.essteling.a3.esstelingexperience.Entities;

import java.util.Date;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class Data {
    final long Time;
    final double Speed;
    final double Height;

    public Data(double speed, double height, long ms) {
        Time = ms;
        Speed = speed < 0 ? 0 : speed;
        Height = height;
    }
}
