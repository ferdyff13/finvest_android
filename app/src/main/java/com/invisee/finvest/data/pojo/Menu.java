package com.invisee.finvest.data.pojo;

import android.graphics.drawable.Drawable;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class Menu {

    private int id;
    private Drawable icon;
    private String name;

    public Menu(int id, Drawable icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
