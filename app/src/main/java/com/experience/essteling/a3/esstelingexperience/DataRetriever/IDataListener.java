package com.experience.essteling.a3.esstelingexperience.DataRetriever;

import com.experience.essteling.a3.esstelingexperience.Entities.SensorData;

/**
 * Created by Bobsk on 8-6-2017.
 */

public interface IDataListener {
    void onFinish(SensorData meting);
    void onError();
}
