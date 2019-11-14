package com.wavenet.ding.qpps.bean;

/**
 * Created by zoubeiwen on 2018/6/20.
 */

public class DropBean {
    boolean Choiced;
    String Name;
    String key;
    int tab;

    public DropBean(String name, String key, int tab) {
        Name = name;
        this.key = key;
        this.tab = tab;
    }

    public DropBean(String name, boolean Choiced) {
        Name = name;
        this.Choiced = Choiced;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean isChoiced() {
        return Choiced;
    }

    public void setChoiced(boolean choiced) {
        Choiced = choiced;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
