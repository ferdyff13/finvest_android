package com.invisee.finvest.data.pojo;

import java.io.Serializable;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class DummyCatalogue implements Serializable{

    private String name;
    private String desc;
    private double rate;

    public DummyCatalogue(String name, String desc, double rate) {
        this.name = name;
        this.desc = desc;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
