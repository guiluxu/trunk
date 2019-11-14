package com.wavenet.ding.qpps.interf;

import android.view.MotionEvent;

import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.view.viewutils.scrollLayout.ScrollLayoutView;

import java.util.ArrayList;

/**
 * Created by zoubeiwen on 2019/8/1.
 */

public class InterfListen {
    public static String onScrollFinished="onScrollFinished";
    public  interface A {
        void aA();
    }
    public  interface B {
        void  bA(Graphic g, ArrayList<AdapterBean> abList, int url, String urlname, int  Symbol);
    }
    public  interface C {
        void cA(String s,ScrollLayoutView.Status currentStatus,boolean isHide);
    }  public  interface D {
        void dA(String s);
    } public  interface E {
        void eA(String time,String id);
    }public  interface F {
        void fA(final MotionEvent e);
    }public  interface G {
        MapView gA();
    }public  interface H {
        void hA(int  isshow);
    }
    public  interface setTimeSelete {
        void okTime();
    }
}
