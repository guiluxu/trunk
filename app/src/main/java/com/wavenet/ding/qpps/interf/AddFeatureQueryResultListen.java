package com.wavenet.ding.qpps.interf;

import com.wavenet.ding.qpps.bean.AdapterBean;

import java.util.ArrayList;

/**
 * Created by zoubeiwen on 2018/9/5.
 */

public interface AddFeatureQueryResultListen {
    void getQueryResultMap(ArrayList<AdapterBean> abList, int mTable);

    void getQueryResultMapSeach(ArrayList<AdapterBean> abList, int mTable);
}
