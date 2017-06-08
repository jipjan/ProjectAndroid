package com.experience.essteling.a3.esstelingexperience.DataRetriever;

import com.experience.essteling.a3.esstelingexperience.Entities.Data;

/**
 * Created by Bobsk on 8-6-2017.
 */

public interface IDataListener {
    void onFinish(Data meting);
    void onError();
}
