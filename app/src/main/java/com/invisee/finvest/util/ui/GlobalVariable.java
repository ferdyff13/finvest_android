package com.invisee.finvest.util.ui;

/**
 * Created by pandu.abbiyuarsyah on 26/04/2017.
 */

public class GlobalVariable {

    private static GlobalVariable mInstance= null;

    public boolean status_next;
    public Double totalCart;

    protected GlobalVariable(){}

    public static synchronized GlobalVariable getInstance(){
        if(null == mInstance){
            mInstance = new GlobalVariable();
        }
        return mInstance;
    }

}
